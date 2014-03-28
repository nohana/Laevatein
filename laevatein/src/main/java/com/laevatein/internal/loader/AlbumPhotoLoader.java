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
package com.laevatein.internal.loader;

import com.laevatein.internal.entity.Album;
import com.laevatein.internal.entity.Item;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

import jp.mixi.compatibility.android.provider.MediaStoreCompat;

/**
 * @author KeithYokoma
 * @since 2014/03/27
 * @version 1.0.0
 * @hide
 */
public class AlbumPhotoLoader extends CursorLoader {
    public static final String TAG = AlbumPhotoLoader.class.getSimpleName();
    private static final String[] PROJECTION = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME };
    private static final String ORDER_BY = MediaStore.Images.Media._ID + " DESC";
    private final boolean mEnableCapture;

    public AlbumPhotoLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, boolean capture) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        mEnableCapture = capture;
    }

    public static CursorLoader newInstance(Context context, Album album) {
        if (album.isAll()) {
            return new AlbumPhotoLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTION, null, null, ORDER_BY, false);
        }
        return newInstance(context, album, false);
    }

    public static CursorLoader newInstance(Context context, Album album, boolean capture) {
        return new AlbumPhotoLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTION,
                MediaStore.Images.Media.BUCKET_ID + " = ?", new String[]{ album.getId() }, ORDER_BY, capture);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor result = super.loadInBackground();
        if (!mEnableCapture || !MediaStoreCompat.hasCameraFeature(getContext())) {
            return result;
        }
        MatrixCursor dummy = new MatrixCursor(PROJECTION);
        dummy.addRow(new Object[] { Item.ITEM_ID_CAPTURE, Item.ITEM_DISPLAY_NAME_CAPTURE });
        return new MergeCursor(new Cursor[] { dummy, result });
    }
}
