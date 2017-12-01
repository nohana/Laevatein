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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laevatein.R;
import com.laevatein.internal.entity.ViewResourceSpec;
import com.laevatein.internal.misc.ui.FragmentUtils;
import com.laevatein.internal.ui.adapter.SelectedPhotoAdapter;
import com.laevatein.internal.ui.helper.SelectedGridViewHelper;
import com.laevatein.ui.PhotoSelectionActivity;

/**
 * @author KeithYokoma
 * @since 2014/03/27
 * @version 1.0.0
 * @hide
 */
public class SelectedPhotoGridFragment extends Fragment implements SelectedPhotoAdapter.CheckStateListener {
    public static final String TAG = SelectedPhotoGridFragment.class.getSimpleName();

    public SelectedPhotoGridFragment() {
    }

    public static SelectedPhotoGridFragment newInstance() {
        return new SelectedPhotoGridFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.l_fragment_grid_photo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewResourceSpec resources = FragmentUtils.getIntentParcelableExtra(this, PhotoSelectionActivity.EXTRA_VIEW_SPEC);
        getActivity().setTitle(R.string.l_album_name_selected);
        SelectedGridViewHelper.setUpGridView(this, resources.getItemViewResources(), SelectedGridViewHelper.getSelectedPhotoSet(this));
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void onUpdate() {
        getActivity().invalidateOptionsMenu();
    }
}