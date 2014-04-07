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

import com.amalgam.database.CursorUtils;
import com.amalgam.io.CloseableUtils;
import com.laevatein.MimeType;
import com.laevatein.internal.entity.SelectionSpec;
import com.laevatein.internal.entity.UncapableCause;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author KeithYokoma
 * @since 2014/03/25
 * @version 1.0.0
 * @hide
 */
public final class PhotoMetadataUtils {
    public static final String TAG = PhotoMetadataUtils.class.getSimpleName();
    private static final String SCHEME_CONTENT = "content";

    private PhotoMetadataUtils() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static int getPixelsCount(ContentResolver resolver, Uri uri) {
        InputStream in = null;
        try {
            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inJustDecodeBounds = true;
            in = resolver.openInputStream(uri);
            BitmapFactory.decodeStream(in, null, op);
            return op.outHeight * op.outWidth;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "file not found for the uri: " + uri.toString(), e);
            return -1;
        } finally {
            CloseableUtils.close(in);
        }
    }

    public static String getPath(ContentResolver resolver, Uri uri) {
        if (uri == null) {
            return null;
        }

        if (SCHEME_CONTENT.equals(uri.getScheme())) {
            Cursor cursor = null;
            try {
                cursor = resolver.query(uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null);
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

}
