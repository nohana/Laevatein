package com.laevatein.internal.utils;

import android.support.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class is based on this source code.
 * https://github.com/mixi-inc/Android-Device-Compatibility/blob/master/AndroidDeviceCompatibility/src/main/java/jp/mixi/compatibility/android/media/ExifInterfaceCompat.java
 * <p>
 * License
 * see: https://github.com/mixi-inc/Android-Device-Compatibility#license
 */
public class ExifInterfaceUtils {
    public static final String TAG = ExifInterfaceUtils.class.getSimpleName();
    private static final int EXIF_DEGREE_FALLBACK_VALUE = -1;

    /**
     * Do not instantiate this class.
     */
    private ExifInterfaceUtils() {
    }

    private static Date getExifDateTime(String filepath) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
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
    public static long getExifDateTimeInMillis(String filepath) {
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
    public static int getExifOrientation(String filepath) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
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
