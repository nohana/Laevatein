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

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

import com.laevatein.internal.entity.Album;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Wrapper for {@link CursorLoader} to merge custom cursors.
 * @author KeithYokoma
 * @since 2014/03/26
 * @version 1.0.0
 * @hide
 */
public class AlbumLoader extends CursorLoader {
    public static final String TAG = AlbumLoader.class.getSimpleName();
    private static final String[] PROJECTION = { MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media._ID };
    private static final String BUCKET_ORDER_BY = "datetaken DESC";
    private static final String MEDIA_ID_DUMMY = String.valueOf(-1);

    public AlbumLoader(Context context) {
        super(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTION, null, null, BUCKET_ORDER_BY);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor albums = super.loadInBackground();

        Map<String, BucketEntry> buckets = new LinkedHashMap<>();
        if (albums != null) {
            try {
                while (albums.moveToNext()) {
                    String bucketId = albums.getString(albums.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                    if (!buckets.containsKey(bucketId)) {
                        buckets.put(bucketId, new BucketEntry(
                                albums.getString(albums.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)),
                                albums.getString(albums.getColumnIndex(MediaStore.Images.Media._ID))
                        ));
                    }
                }
            } finally {
                albums.close();
            }
        }

        MatrixCursor allAlbum = new MatrixCursor(PROJECTION);
        allAlbum.addRow(new String[]{Album.ALBUM_ID_ALL, Album.ALBUM_NAME_ALL, MEDIA_ID_DUMMY});
        for (Map.Entry<String, BucketEntry> entry : buckets.entrySet()) {
            BucketEntry bucket = entry.getValue();
            allAlbum.addRow(new String[]{entry.getKey(), bucket.displayName, bucket.mediaId});
        }

        return allAlbum;
    }

    private static class BucketEntry {
        private final String displayName;
        private final String mediaId;

        public BucketEntry(String displayName, String mediaId) {
            this.displayName = displayName;
            this.mediaId = mediaId;
        }
    }
}