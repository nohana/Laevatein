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
package com.laevatein.internal.ui;

import com.laevatein.R;
import com.laevatein.internal.entity.Album;
import com.laevatein.internal.entity.AlbumViewResources;
import com.laevatein.internal.misc.ui.FragmentUtils;
import com.laevatein.internal.model.DevicePhotoAlbumCollection;
import com.laevatein.internal.ui.helper.AlbumListViewHelper;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 */
public class AlbumListFragment extends Fragment implements
        AdapterView.OnItemClickListener,
        DevicePhotoAlbumCollection.DevicePhotoAlbumCallbacks {
    private final DevicePhotoAlbumCollection mCollection = new DevicePhotoAlbumCollection();
    private OnDirectorySelectListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDirectorySelectListener) activity;
        } catch (ClassCastException e) {
            throw new IllegalStateException("the host activity should implement OnDirectorySelectListener.", e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_album, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AlbumViewResources resources = FragmentUtils.getIntentParcelableExtra(this, PhotoSelectionActivity.EXTRA_DIR_VIEW_RES);
        AlbumListViewHelper.setUpListView(this, this, resources);
        mCollection.onCreate(getActivity(), this);
        mCollection.loadAlbums();
    }

    @Override
    public void onDestroyView() {
        mCollection.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AlbumListViewHelper.callOnSelect(mListener, (Cursor) parent.getItemAtPosition(position));
    }

    @Override
    public void onLoad(final Cursor cursor) {
        AlbumListViewHelper.setCursor(this, cursor);
        AlbumListViewHelper.callOnDefaultSelect(getActivity(), mListener, cursor);

    }

    @Override
    public void onReset() {
        AlbumListViewHelper.setCursor(this, null);
    }

    public interface OnDirectorySelectListener {
        public void onSelect(Album album);
    }
}