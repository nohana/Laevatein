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

import com.laevatein.R;
import com.laevatein.internal.entity.ItemViewResources;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.helper.SelectedGridViewHelper;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import android.support.annotation.Nullable;

/**
 * @author KeithYokoma
 * @since 2014/03/27
 * @version 1.0.0
 * @hide
 */
public class SelectedPhotoAdapter extends ArrayAdapter<Uri> {
    public static final String TAG = SelectedPhotoAdapter.class.getSimpleName();
    private final ItemViewResources mResources;
    private final SelectedUriCollection mCollection;
    private CheckStateListener mListener;

    public SelectedPhotoAdapter(Context context, ItemViewResources resources, SelectedUriCollection collection) {
        super(context, resources.getLayoutId(), resources.getCheckBoxId(), collection.asList());
        mResources = resources;
        mCollection = collection;
    }

    @Override
    @Nullable
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (view == null) {
            return null;
        }

        final Uri uri = getItem(position);

        ImageView thumbnail = (ImageView) view.findViewById(mResources.getImageViewId());
        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PhotoGridViewHelper.callPreview(getContext(), item);
            }
        });
        final CheckBox check = (CheckBox) view.findViewById(mResources.getCheckBoxId());
        check.setChecked(mCollection.isSelected(uri));
        check.setText(null);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SelectedGridViewHelper.syncCollection(mCollection, uri, isChecked);
                SelectedGridViewHelper.callCheckStateListener(mListener);
            }
        });
        Picasso.with(getContext()).load(uri)
                .resizeDimen(R.dimen.l_gridItemImageWidth, R.dimen.l_gridItemImageHeight)
                .centerCrop()
                .into(thumbnail);
        return view;
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
