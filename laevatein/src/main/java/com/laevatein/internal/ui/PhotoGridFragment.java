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

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amalgam.os.BundleUtils;
import com.laevatein.R;
import com.laevatein.internal.entity.Album;
import com.laevatein.internal.entity.ViewResourceSpec;
import com.laevatein.internal.misc.ui.FragmentUtils;
import com.laevatein.internal.model.AlbumPhotoCollection;
import com.laevatein.internal.ui.adapter.AlbumPhotoAdapter;
import com.laevatein.internal.ui.helper.PhotoGridViewHelper;
import com.laevatein.ui.PhotoSelectionActivity;

/**
 * @author KeithYokoma
 * @version 1.0.0
 * @hide
 * @since 2014/03/20
 */
public class PhotoGridFragment extends Fragment implements
        AlbumPhotoCollection.AlbumPhotoCallbacks, AlbumPhotoAdapter.CheckStateListener {
    public static final String TAG = PhotoGridFragment.class.getSimpleName();
    public static final String ARGS_ALBUM = BundleUtils.buildKey(PhotoGridFragment.class, "ARGS_ALBUM");
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
        ViewResourceSpec resources = FragmentUtils.getIntentParcelableExtra(this, PhotoSelectionActivity.EXTRA_VIEW_SPEC);
        Album album = getArguments().getParcelable(ARGS_ALBUM);
        AlbumPhotoAdapter.BindViewListener listener;
        try {
            listener = (AlbumPhotoAdapter.BindViewListener) getActivity();
        } catch (ClassCastException e) {
            throw new IllegalStateException("the host activity should implement BindViewListener.");
        }
        PhotoGridViewHelper.setUpGridView(this, resources, PhotoGridViewHelper.getSelectedPhotoSet(this), listener);
        mPhotoCollection.onCreate(getActivity(), this);
        mPhotoCollection.load(album, resources.getCaptureResource().enabled());
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
        getActivity().invalidateOptionsMenu();
    }

    public void refreshGrid() {
        PhotoGridViewHelper.refreshView(this);
    }
}