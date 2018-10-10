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
}
