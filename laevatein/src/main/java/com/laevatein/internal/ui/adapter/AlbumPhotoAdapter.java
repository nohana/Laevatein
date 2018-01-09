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
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.laevatein.R;
import com.laevatein.internal.entity.Item;
import com.laevatein.internal.entity.ItemViewResources;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.helper.PhotoGridViewHelper;

/**
 * @author KeithYokoma
 * @version 1.0.0
 * @hide
 * @since 2014/03/24
 */
public class AlbumPhotoAdapter extends RecyclerViewCursorAdapter<AlbumPhotoAdapter.ViewHolder> {
    private final Context mContext;
    private final ItemViewResources mResources;
    private final SelectedUriCollection mCollection;
    private CheckStateListener mListener;
    private BindViewListener mBindViewListener;

    public AlbumPhotoAdapter(Context context, ItemViewResources resources, SelectedUriCollection collection, BindViewListener bindViewListener) {
        super(null);
        mContext = context;
        mResources = resources;
        mCollection = collection;
        mBindViewListener = bindViewListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(mResources.getLayoutId(), viewGroup, false);
        return new ViewHolder(v, mResources);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        final Item item = Item.valueOf(cursor);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isCapture()) {
                    PhotoGridViewHelper.callCamera(mContext);
                } else {
                    PhotoGridViewHelper.callPreview(mContext, item, mCollection.asList());
                }
            }
        });
        holder.checkBox.setVisibility(item.isCapture() ? View.GONE : View.VISIBLE);
        holder.checkBox.setChecked(mCollection.isSelected(item.buildContentUri()));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoGridViewHelper.syncCheckState(mContext, mCollection, item, (CheckBox) v);
                PhotoGridViewHelper.callCheckStateListener(mListener);
            }
        });
        if (item.isCapture()) {
            holder.thumbnail.setImageResource(R.drawable.l_ic_capture);
        } else {
            Glide.with(mContext).load(item.buildContentUri())
                    .apply(new RequestOptions().fitCenter().centerCrop())
                    .into(holder.thumbnail);
        }
        mBindViewListener.onBindView(mContext, holder.itemView, item.buildContentUri());
    }

    public void registerCheckStateListener(CheckStateListener listener) {
        mListener = listener;
    }

    public void unregisterCheckStateListener() {
        mListener = null;
    }

    public interface CheckStateListener {
        void onUpdate();
    }

    public interface BindViewListener {
        /**
         * Called when view is bound to data.
         *
         * @param context context of photo selection activity
         * @param view    view of grid
         * @param uri     uri of image in grid
         */
        void onBindView(Context context, View view, Uri uri);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView thumbnail;
        private final CheckBox checkBox;

        ViewHolder(View v, ItemViewResources resources) {
            super(v);
            thumbnail = v.findViewById(resources.getImageViewId());
            checkBox = v.findViewById(resources.getCheckBoxId());
        }
    }
}