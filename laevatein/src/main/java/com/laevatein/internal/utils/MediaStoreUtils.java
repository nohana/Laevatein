package com.laevatein.internal.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.media.ExifInterface;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.laevatein.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Compatibility class for the issue of various implementations of camera feature.
 * <p>
 * This class is based on this source code.
 * https://github.com/mixi-inc/Android-Device-Compatibility/blob/master/AndroidDeviceCompatibility/src/main/java/jp/mixi/compatibility/android/provider/MediaStoreCompat.java
 * <p>
 * License
 * see: https://github.com/mixi-inc/Android-Device-Compatibility#license
 */
public class MediaStoreUtils {
    public static final String TAG = MediaStoreUtils.class.getSimpleName();
    private static final String MEDIA_FILE_NAME_FORMAT = "yyyyMMdd_HHmmss";
    private static final String MEDIA_FILE_EXTENSION = ".jpg";
    private static final String MEDIA_FILE_PREFIX = "IMG_";
    private final String MEDIA_FILE_DIRECTORY;
    private Context mContext;
    private ContentObserver mObserver;
    private ArrayList<PhotoContent> mRecentlyUpdatedPhotos;

    /**
     * Prepares to deal with various implementations of camera feature and {@link MediaStore}.
     * You should call {@link MediaStoreUtils#destroy()} on destroying context.
     *
     * @param context a context
     * @param handler a handler
     */
    public MediaStoreUtils(Context context, Handler handler) {
        mContext = context;
        MEDIA_FILE_DIRECTORY = context.getPackageName();
        mObserver = new ContentObserver(handler) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                updateLatestPhotos();
            }
        };
        mContext.getContentResolver().registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, mObserver);
    }

    /**
     * Checks whether the device has a camera feature or not.
     *
     * @param context a context to check for camera feature.
     * @return true if the device has a camera feature. false otherwise.
     */
    public static final boolean hasCameraFeature(Context context) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * Invokes a camera capture activity.
     *
     * @param activity    the caller of the camera capture activity.
     * @param requestCode activity result handling id.
     * @return a file name to be saved as.
     */
    public String invokeCameraCapture(Activity activity, int requestCode) {
        if (!hasCameraFeature(mContext)) return null;

        File toSave = getOutputFile();
        if (toSave == null) return null;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(activity, activity.getString(R.string.l_file_provider_authorities), toSave));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        activity.startActivityForResult(intent, requestCode);
        return toSave.toString();
    }

    /**
     * Should be called on destroying context to dereference to the {@link ContentObserver}.
     */
    public void destroy() {
        mContext.getContentResolver().unregisterContentObserver(mObserver);
    }

    /**
     * @param data        the result {@link Intent} of a camera activity.
     * @param preparedUri a prepared photo uri to fetch photo data.
     * @return captured photo {@link Uri}
     */
    public Uri getCapturedPhotoUri(Intent data, String preparedUri) {
        Uri captured = null;
        if (data != null) {
            captured = data.getData();
            if (captured == null) data.getParcelableExtra(Intent.EXTRA_STREAM);
        }

        File prepared = new File(preparedUri.toString());
        if (captured == null || captured.equals(Uri.fromFile(prepared))) {
            captured = findPhotoFromRecentlyTaken(prepared);
            if (captured == null) {
                captured = storeImage(prepared);
                prepared.delete();
            } else {
                // Found. Compare the destination path
                // and delete app-private one if there is any copy outside of app-private directory.
                String realPath = getPathFromUri(
                        mContext.getContentResolver(), captured);
                if (realPath != null) {
                    if (!prepared.equals(new File(realPath))) prepared.delete();
                }
            }
        }

        return captured;
    }

    /**
     * Deletes unnecessary files if exist.
     *
     * @param uri to delete.
     */
    public void cleanUp(String uri) {
        File file = new File(uri.toString());
        if (file.exists()) file.delete();
    }

    /**
     * Obtains a captured photo file path from content {@link Uri} of a {@link MediaStore}
     *
     * @param resolver   a content resolver
     * @param contentUri a content uri
     * @return captured photo file path string
     */
    public static String getPathFromUri(ContentResolver resolver, Uri contentUri) {
        final String dataColumn = MediaStore.MediaColumns.DATA;
        Cursor cursor = null;
        try {
            cursor = resolver.query(contentUri, new String[]{dataColumn}, null, null, null);
            if (cursor == null || !cursor.moveToFirst()) return null;

            final int index = cursor.getColumnIndex(dataColumn);
            return cursor.getString(index);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    /**
     * Copies file streams.
     *
     * @param is input stream
     * @param os output stream
     * @return the number of bytes.
     * @throws IOException if something wrong with I/O.
     */
    public static long copyFileStream(FileInputStream is, FileOutputStream os)
            throws IOException {
        FileChannel srcChannel = null;
        FileChannel destChannel = null;
        try {
            srcChannel = is.getChannel();
            destChannel = os.getChannel();
            return srcChannel.transferTo(0, srcChannel.size(), destChannel);
        } finally {
            if (srcChannel != null) srcChannel.close();
            if (destChannel != null) destChannel.close();
        }
    }

    /**
     * Obtains file {@link Uri} that refers to a recently taken photo.
     *
     * @param file to search
     * @return photo file uri.
     */
    private Uri findPhotoFromRecentlyTaken(File file) {
        if (mRecentlyUpdatedPhotos == null)
            updateLatestPhotos();

        long filesize = file.length();
        long taken = ExifInterfaceUtils.getExifDateTimeInMillis(file.getAbsolutePath());

        int maxPoint = 0;
        PhotoContent maxItem = null;
        for (PhotoContent item : mRecentlyUpdatedPhotos) {
            int point = 0;
            if (item.size == filesize)
                point++;
            if (item.taken == taken)
                point++;
            if (point > maxPoint) {
                maxPoint = point;
                maxItem = item;
            }
        }
        if (maxItem != null) {
            generateThumbnails(maxItem.id);
            return ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    maxItem.id);
        }

        return null;
    }

    /**
     * Stores a file to media store with photo orientation and date that taken.
     *
     * @param file to store
     * @return a stored file uri
     */
    private Uri storeImage(File file) {
        // store image to content provider
        try {
            // create parameters for Intent with filename
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, file.getName());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DESCRIPTION, "mixi Photo");
            values.put(MediaStore.Images.Media.ORIENTATION, new ExifInterface(file.getAbsolutePath()).getRotationDegrees());
            long date = ExifInterfaceUtils.getExifDateTimeInMillis(file.getAbsolutePath());
            if (date != -1)
                values.put(MediaStore.Images.Media.DATE_TAKEN, date);

            Uri imageUri = mContext.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            FileOutputStream fos = (FileOutputStream) mContext.getContentResolver().openOutputStream(
                    imageUri);
            FileInputStream fis = new FileInputStream(file);
            copyFileStream(fis, fos);
            fos.close();
            fis.close();

            generateThumbnails(ContentUris.parseId(imageUri));

            return imageUri;
        } catch (Exception e) {
            Log.w(TAG, "cannot insert", e);
            return null;
        }
    }

    /**
     * Request to update latest photos onto media store.
     */
    private void updateLatestPhotos() {
        // retrieve the newest five images
        Cursor c = MediaStore.Images.Media.query(mContext.getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{
                        MediaStore.Images.ImageColumns._ID,
                        MediaStore.Images.ImageColumns.DATE_TAKEN,
                        MediaStore.Images.ImageColumns.SIZE,
                }, null, null, MediaStore.Images.ImageColumns.DATE_ADDED + " DESC");
        if (c != null) {
            try {
                int count = 0;
                mRecentlyUpdatedPhotos = new ArrayList<PhotoContent>();
                while (c.moveToNext()) {
                    PhotoContent item = new PhotoContent();
                    item.id = c.getLong(0);
                    item.taken = c.getLong(1);
                    item.size = c.getInt(2);
                    mRecentlyUpdatedPhotos.add(item);
                    if (++count > 5)
                        break;
                }
            } finally {
                c.close();
            }
        }
    }

    /**
     * Create thumbnail images for a specified photo image.
     *
     * @param imageId referes to a photo image.
     */
    private void generateThumbnails(long imageId) {
        try {
            MediaStore.Images.Thumbnails.getThumbnail(mContext.getContentResolver(), imageId,
                    MediaStore.Images.Thumbnails.MINI_KIND, null);
        } catch (NullPointerException e) {
            // some MediaStores throws NPE, just ignore the result
        }
    }

    /**
     * Make output file uri based on a external storage directory.
     *
     * @return a file
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    private File getOutputFile() {
        File extDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES),
                MEDIA_FILE_DIRECTORY);

        if (!extDir.exists()) {
            if (!extDir.mkdirs()) return null;
        }

        String timeStamp = new SimpleDateFormat(MEDIA_FILE_NAME_FORMAT).format(new Date());
        return new File(extDir.getPath() + File.separator +
                MEDIA_FILE_PREFIX + timeStamp + MEDIA_FILE_EXTENSION);
    }

    /**
     * Entity class for photo content.
     */
    private static class PhotoContent {
        public long id;
        public long taken;
        public int size;
    }
}