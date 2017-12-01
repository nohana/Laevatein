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

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.laevatein.R;
import com.laevatein.internal.entity.ItemViewResources;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.adapter.SelectedPhotoAdapter;
import com.laevatein.internal.ui.widget.PhotoDecoration;
import com.laevatein.ui.PhotoSelectionActivity;

/**
 * @author keishin.yokomaku
 * @version 1.0.0
 * @hide
 * @since 2014/03/27
 */
public final class SelectedGridViewHelper {
    private SelectedGridViewHelper() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static SelectedUriCollection getSelectedPhotoSet(Fragment fragment) {
        return ((PhotoSelectionActivity) fragment.getActivity()).getCollection();
    }

    public static void setUpGridView(Fragment fragment, ItemViewResources resources, SelectedUriCollection collection) {
        RecyclerView recyclerView = fragment.getView().findViewById(R.id.l_recyclerview);
        int spanCount = resources.getSpanCount();
        recyclerView.setLayoutManager(new GridLayoutManager(fragment.getContext(), resources.getSpanCount()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);

        SelectedPhotoAdapter adapter = new SelectedPhotoAdapter(fragment.getActivity(), resources, collection);
        adapter.registerCheckStateListener((SelectedPhotoAdapter.CheckStateListener) fragment);

        int spacing = fragment.getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerView.addItemDecoration(new PhotoDecoration(spanCount, spacing, false));
        recyclerView.setAdapter(adapter);

        TextView emptyMessage = fragment.getView().findViewById(R.id.l_label_empty);
        if (collection.asList().size() == 0) {
            emptyMessage.setVisibility(View.VISIBLE);
            emptyMessage.setText(R.string.l_empty_selection);
        } else {
            emptyMessage.setVisibility(View.GONE);
        }
    }

    public static void tearDownGridView(Fragment fragment) {
        RecyclerView recyclerView = fragment.getView().findViewById(R.id.l_recyclerview);
        SelectedPhotoAdapter adapter = (SelectedPhotoAdapter) recyclerView.getAdapter();
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
