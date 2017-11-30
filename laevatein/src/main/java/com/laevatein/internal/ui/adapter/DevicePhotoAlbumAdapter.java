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
package com.laevatein.internal.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amalgam.content.ContextUtils;
import com.laevatein.R;
import com.laevatein.internal.entity.Album;

/**
 * @author KeithYokoma
 * @version 1.0.0
 * @hide
 * @since 2014/03/24
 */
public class DevicePhotoAlbumAdapter extends CursorAdapter {

    public DevicePhotoAlbumAdapter(Context context, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = ContextUtils.getLayoutInflater(context);
        return inflater.inflate(R.layout.l_list_item_default_album, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Album album = Album.valueOf(cursor);
        TextView textView = view.findViewById(R.id.l_default_directory_label);
        textView.setText(album.getDisplayName(context));
    }
}