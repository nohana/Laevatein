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
package com.laevatein.internal.ui.helper;

import com.laevatein.R;
import com.laevatein.internal.entity.ItemViewResources;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.PhotoSelectionActivity;
import com.laevatein.internal.ui.adapter.SelectedPhotoAdapter;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.widget.GridView;

/**
 * @author keishin.yokomaku
 * @since 2014/03/27
 * @version 1.0.0
 * @hide
 */
public final class SelectedGridViewHelper {
    private SelectedGridViewHelper() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static SelectedUriCollection getSelectedPhotoSet(Fragment fragment) {
        return ((PhotoSelectionActivity) fragment.getActivity()).getCollection();
    }

    public static void setUpGridView(Fragment fragment, ItemViewResources resources, SelectedUriCollection collection) {
        GridView gridView = (GridView) fragment.getView().findViewById(R.id.l_grid_photo);
        SelectedPhotoAdapter adapter = new SelectedPhotoAdapter(fragment.getActivity(), resources, collection);
        adapter.registerCheckStateListener((SelectedPhotoAdapter.CheckStateListener) fragment);
        gridView.setAdapter(adapter);
    }

    public static void tearDownGridView(Fragment fragment) {
        GridView gridView = (GridView) fragment.getView().findViewById(R.id.l_grid_photo);
        SelectedPhotoAdapter adapter = (SelectedPhotoAdapter) gridView.getAdapter();
        adapter.unregisterCheckStateListener();
    }

    public static void syncCollection(SelectedUriCollection collection, Uri uri, boolean checked) {
        if (checked) {
            collection.add(uri);
        } else {
            collection.remove(uri);
        }
    }

    public static void callCheckStateListener(SelectedPhotoAdapter.CheckStateListener listener) {
        if (listener == null) {
            return;
        }
        listener.onUpdate();
    }
}
