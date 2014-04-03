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

import com.amalgam.os.BundleUtils;
import com.laevatein.R;
import com.laevatein.internal.entity.Album;
import com.laevatein.internal.entity.ItemViewResources;
import com.laevatein.internal.misc.ui.FragmentUtils;
import com.laevatein.internal.model.AlbumPhotoCollection;
import com.laevatein.internal.ui.adapter.AlbumPhotoAdapter;
import com.laevatein.internal.ui.helper.PhotoGridViewHelper;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 * @hide
 */
public class PhotoGridFragment extends Fragment implements
        AlbumPhotoCollection.AlbumPhotoCallbacks, AlbumPhotoAdapter.CheckStateListener {
    public static final String TAG = PhotoGridFragment.class.getSimpleName();
    private static final String ARGS_ALBUM = BundleUtils.buildKey(PhotoGridFragment.class, "ARGS_ALBUM");
    private final AlbumPhotoCollection mPhotoCollection = new AlbumPhotoCollection();

    public static PhotoGridFragment newInstance(Album album) {
        PhotoGridFragment fragment = new PhotoGridFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_ALBUM, album);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.l_fragment_grid_photo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ItemViewResources resources = FragmentUtils.getIntentParcelableExtra(this, PhotoSelectionActivity.EXTRA_ITEM_VIEW_RES);
        Album album = getArguments().getParcelable(ARGS_ALBUM);
        PhotoGridViewHelper.setUpGridView(this, resources, PhotoGridViewHelper.getSelectedPhotoSet(this));
        mPhotoCollection.onCreate(getActivity(), this);
        mPhotoCollection.load(album, FragmentUtils.getIntentBooleanExtra(this, PhotoSelectionActivity.EXTRA_ENABLE_CAPTURE, false));
        getActivity().setTitle(album.getDisplayName(getActivity()));
    }

    @Override
    public void onDestroyView() {
        PhotoGridViewHelper.tearDownGridView(this);
        mPhotoCollection.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onLoad(Cursor cursor) {
        PhotoGridViewHelper.setCursor(this, cursor);
    }

    @Override
    public void onReset() {
        PhotoGridViewHelper.setCursor(this, null);
    }

    @Override
    public void onUpdate() {
        getActivity().supportInvalidateOptionsMenu();
    }

    public void refreshGrid() {
        PhotoGridViewHelper.refreshView(this);
    }
}