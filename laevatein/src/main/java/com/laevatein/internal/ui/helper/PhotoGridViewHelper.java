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
import com.laevatein.internal.ui.adapter.AlbumPhotoAdapter;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * @author keishin.yokomaku
 * @since 2014/03/24
 * @version 1.0.0
 * @hide
 */
public final class PhotoGridViewHelper {
    private PhotoGridViewHelper() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static SelectedUriCollection getSelectedPhotoSet(Fragment fragment) {
        return ((PhotoSelectionActivity) fragment.getActivity()).getCollection();
    }

    public static void setUpGridView(Fragment fragment, AdapterView.OnItemClickListener listener, ItemViewResources resources) {
        GridView gridView = (GridView) fragment.getView().findViewById(R.id.grid_photo);
        gridView.setAdapter(new AlbumPhotoAdapter(fragment.getActivity(), null, resources));
        gridView.setOnItemClickListener(listener);
    }

    public static void setCursor(Fragment fragment, Cursor cursor) {
        GridView gridView = (GridView) fragment.getView().findViewById(R.id.grid_photo);
        CursorAdapter adapter = (CursorAdapter) gridView.getAdapter();
        adapter.swapCursor(cursor);
    }
}