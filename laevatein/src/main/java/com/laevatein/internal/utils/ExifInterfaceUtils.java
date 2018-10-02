package com.laevatein.internal.utils;

import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Bug fixture for ExifInterface constructor.
 * <p>
 * This class is based on this source code.
 * https://github.com/mixi-inc/Android-Device-Compatibility/blob/master/AndroidDeviceCompatibility/src/main/java/jp/mixi/compatibility/android/media/ExifInterfaceCompat.java
 * <p>
 * License
 * see: https://github.com/mixi-inc/Android-Device-Compatibility#license
 */
public class ExifInterfaceUtils {
    public static final String TAG = ExifInterfaceUtils.class.getSimpleName();
    public static final int EXIF_DEGREE_FALLBACK_VALUE = -1;

    /**
     * Do not instantiate this class.
     */
    private ExifInterfaceUtils() {
    }

    /**
     * Creates new instance of {@link ExifInterface}.
     * Original constructor won't check filename value, so if null value has been passed,
     * the process will be killed because of SIGSEGV.
     * Google Play crash report system cannot perceive this crash, so this method will throw {@link NullPointerException} when the filename is null.
     *
     * @param filename a JPEG filename.
     * @return {@link ExifInterface} instance.
     * @throws IOException something wrong with I/O.
     */
    public static final ExifInterface newInstance(String filename) throws IOException {
        if (filename == null) throw new NullPointerException("filename should not be null");
        return new ExifInterface(filename);
    }

    public static final Date getExifDateTime(String filepath) {
        ExifInterface exif = null;
        try {
            // ExifInterface does not check whether file path is null or not,
            // so passing null file path argument to its constructor causing SIGSEGV.
            // We should avoid such a situation by checking file path string.
            exif = ExifInterfaceUtils.newInstance(filepath);
        } catch (IOException ex) {
            Log.e(TAG, "cannot read exif", ex);
            return null;
        }

        String date = exif.getAttribute(ExifInterface.TAG_DATETIME);
        if (TextUtils.isEmpty(date)) {
            return null;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            return formatter.parse(date);
        } catch (ParseException e) {
            Log.d(TAG, "failed to parse date taken", e);
        }
        return null;
    }

    /**
     * Read exif info and get datetime value of the photo.
     *
     * @param filepath to get datetime
     * @return when a photo taken.
     */
    public static final long getExifDateTimeInMillis(String filepath) {
        Date datetime = getExifDateTime(filepath);
        if (datetime == null) {
            return -1;
        }
        return datetime.getTime();
    }

    /**
     * Read exif info and get orientation value of the photo.
     *
     * @param filepath to get exif.
     * @return exif orientation value
     */
    public static final int getExifOrientation(String filepath) {
        ExifInterface exif = null;
        try {
            // ExifInterface does not check whether file path is null or not,
            // so passing null file path argument to its constructor causing SIGSEGV.
            // We should avoid such a situation by checking file path string.
            exif = ExifInterfaceUtils.newInstance(filepath);
        } catch (IOException ex) {
            Log.e(TAG, "cannot read exif", ex);
            return EXIF_DEGREE_FALLBACK_VALUE;
        }

        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, EXIF_DEGREE_FALLBACK_VALUE);
        if (orientation == EXIF_DEGREE_FALLBACK_VALUE) {
            return 0;
        }
        // We only recognize a subset of orientation tag values.
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
            default:
                return 0;
        }
    }
}
