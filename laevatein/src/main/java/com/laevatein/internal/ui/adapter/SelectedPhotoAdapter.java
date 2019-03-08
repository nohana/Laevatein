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
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.laevatein.internal.entity.ItemViewResources;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.helper.SelectedGridViewHelper;

import java.util.List;

/**
 * @author KeithYokoma
 * @version 1.0.0
 * @hide
 * @since 2014/03/27
 */
public class SelectedPhotoAdapter extends RecyclerView.Adapter<SelectedPhotoAdapter.ViewHolder> {
    public static final String TAG = SelectedPhotoAdapter.class.getSimpleName();
    private final Context mContext;
    private final ItemViewResources mResources;
    private final SelectedUriCollection mCollection;
    private CheckStateListener mListener;
    private List<Uri> mUris;

    public SelectedPhotoAdapter(Context context, ItemViewResources resources, SelectedUriCollection collection) {
        mContext = context;
        mResources = resources;
        mCollection = collection;
        mUris = collection.asList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(mResources.getLayoutId(), viewGroup, false);
        return new ViewHolder(v, mResources);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Uri uri = mUris.get(position);
        holder.checkBox.setChecked(mCollection.isSelected(uri));
        holder.checkBox.setText(null);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SelectedGridViewHelper.syncCollection(mCollection, uri, isChecked);
                SelectedGridViewHelper.callCheckStateListener(mListener);
            }
        });
        Glide.with(mContext).load(uri)
                .apply(new RequestOptions().fitCenter().centerCrop())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return mUris.size();
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
