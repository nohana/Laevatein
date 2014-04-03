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

import com.amalgam.content.ContextUtils;
import com.laevatein.R;
import com.laevatein.internal.entity.Item;
import com.laevatein.internal.entity.ItemViewResources;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.helper.PhotoGridViewHelper;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

/**
 * @author KeithYokoma
 * @since 2014/03/24
 * @version 1.0.0
 * @hide
 */
public class AlbumPhotoAdapter extends CursorAdapter {
    private final ItemViewResources mResources;
    private final SelectedUriCollection mCollection;
    private CheckStateListener mListener;

    public AlbumPhotoAdapter(Context context, Cursor c, ItemViewResources resources, SelectedUriCollection collection) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mResources = resources;
        mCollection = collection;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = ContextUtils.getLayoutInflater(context);
        return inflater.inflate(mResources.getLayoutId(), parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final Item item = Item.valueOf(cursor);

        ImageView thumbnail = (ImageView) view.findViewById(mResources.getImageViewId());
        final CheckBox check = (CheckBox) view.findViewById(mResources.getCheckBoxId());
        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isCapture()) {
                    PhotoGridViewHelper.callCamera(context);
                } else {
                    PhotoGridViewHelper.callPreview(context, item, check.isChecked());
                }
            }
        });
        check.setVisibility(item.isCapture() ? View.GONE : View.VISIBLE);
        check.setChecked(mCollection.isSelected(item.buildContentUri()));
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoGridViewHelper.syncCheckState(context, mCollection, item, check);
                PhotoGridViewHelper.callCheckStateListener(mListener);
            }
        });
        if (item.isCapture()) {
            thumbnail.setImageResource(R.drawable.l_ic_capture);
        } else {
            Picasso.with(context).load(item.buildContentUri())
                    .resizeDimen(R.dimen.l_gridItemImageWidth, R.dimen.l_gridItemImageHeight)
                    .centerCrop()
                    .into(thumbnail);
        }
    }

    public void registerCheckStateListener(CheckStateListener listener) {
        mListener = listener;
    }

    public void unregisterCheckStateListener() {
        mListener = null;
    }

    public static interface CheckStateListener {
        public void onUpdate();
    }
}