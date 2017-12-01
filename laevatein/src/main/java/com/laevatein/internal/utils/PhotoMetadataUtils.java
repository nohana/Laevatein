/*
 * Copyright (C) 2014 nohana, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.laevatein.internal.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.media.ExifInterface;
import android.util.DisplayMetrics;
import android.util.Log;

import com.amalgam.database.CursorUtils;
import com.amalgam.io.CloseableUtils;
import com.laevatein.MimeType;
import com.laevatein.internal.entity.SelectionSpec;
import com.laevatein.internal.entity.UncapableCause;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author KeithYokoma
 * @version 1.0.0
 * @hide
 * @since 2014/03/25
 */
public final class PhotoMetadataUtils {
    public static final String TAG = PhotoMetadataUtils.class.getSimpleName();
    private static final int MAX_WIDTH = 1600;
    private static final String SCHEME_CONTENT = "content";

    private PhotoMetadataUtils() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static int getPixelsCount(ContentResolver resolver, Uri uri) {
        Point size = getBitmapBound(resolver, uri);
        return size.x * size.y;
    }

    public static Point getBitmapSize(ContentResolver resolver, Uri uri, Activity activity) {
        Point imageSize = getBitmapBound(resolver, uri);
        int w = imageSize.x;
        int h = imageSize.y;
        if (PhotoMetadataUtils.shouldRotate(resolver, uri)) {
            w = imageSize.y;
            h = imageSize.x;
        }
        if (h == 0) return new Point(MAX_WIDTH, MAX_WIDTH);
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float screenWidth = (float) metrics.widthPixels;
        float screenHeight = (float) metrics.heightPixels;
        float widthScale = screenWidth / w;
        float heightScale = screenHeight / h;
        if (widthScale > heightScale) {
            return new Point((int) (w * widthScale), (int) (h * heightScale));
        }
        return new Point((int) (w * widthScale), (int) (h * heightScale));
    }

    private static Point getBitmapBound(ContentResolver resolver, Uri uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream is = null;
        try {
            is = ContentResolverWrapper.openInputStream(resolver, uri);
        } catch (FileNotFoundException e) {
            return new Point(0, 0);
        } finally {
            CloseableUtils.close(is);
        }
        BitmapFactory.decodeStream(is, null, options);
        int width = options.outWidth;
        int height = options.outHeight;
        return new Point(width, height);
    }

    public static String getPath(ContentResolver resolver, Uri uri) {
        if (uri == null) {
            return null;
        }

        if (SCHEME_CONTENT.equals(uri.getScheme())) {
            Cursor cursor = null;
            try {
                cursor = resolver.query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (cursor == null || !cursor.moveToFirst()) {
                    return null;
                }
                return cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            } finally {
                CursorUtils.close(cursor);
            }
        }
        return uri.getPath();
    }

    public static UncapableCause isAcceptable(Context context, SelectionSpec spec, Uri uri) {
        if (!isSelectableType(context, spec, uri)) {
            return UncapableCause.FILE_TYPE;
        }
        if (!hasUnderAtMostQuality(context, spec, uri)) {
            return UncapableCause.OVER_COUNT;
        }
        if (!hasOverAtLeastQuality(context, spec, uri)) {
            return UncapableCause.UNDER_QUALITY;
        }
        return null;
    }

    public static boolean hasOverAtLeastQuality(Context context, SelectionSpec spec, Uri uri) {
        if (context == null) {
            return false;
        }

        int pixels = PhotoMetadataUtils.getPixelsCount(context.getContentResolver(), uri);
        return spec.getMinPixels() <= pixels;
    }

    public static boolean hasUnderAtMostQuality(Context context, SelectionSpec spec, Uri uri) {
        if (context == null) {
            return false;
        }

        int pixels = PhotoMetadataUtils.getPixelsCount(context.getContentResolver(), uri);
        return pixels <= spec.getMaxPixels();
    }

    public static boolean isSelectableType(Context context, SelectionSpec spec, Uri uri) {
        if (context == null) {
            return false;
        }

        ContentResolver resolver = context.getContentResolver();
        for (MimeType type : spec.getMimeTypeSet()) {
            if (type.checkType(resolver, uri)) {
                return true;
            }
        }
        return false;
    }

    public static boolean shouldRotate(ContentResolver resolver, Uri uri) {
        ExifInterface exif;
        try {
            exif = new ExifInterface(getPath(resolver, uri));
        } catch (IOException e) {
            Log.e(TAG, "could not read exif info of the image: " + uri);
            return false;
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
        return orientation == ExifInterface.ORIENTATION_ROTATE_90
                || orientation == ExifInterface.ORIENTATION_ROTATE_270;
    }

    static final class ContentResolverWrapper {
        @Nullable
        static InputStream openInputStream(ContentResolver resolver, Uri uri) throws FileNotFoundException {
            try {
                return resolver.openInputStream(uri);
            } catch (NullPointerException e) {
                // NPE may occur when `openInputStream` is called on Kyocera's device.
                // > Attempt to invoke virtual method 'char[] java.lang.String.toCharArray()' on a null object reference
                // That was "kyocera original" implement, so we cannot get bounds in this cause.
                return null;
            }
        }
    }

}
