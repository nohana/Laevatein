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

import com.amalgam.app.SupportSimpleAlertDialogFragment;
import com.laevatein.R;
import com.laevatein.internal.entity.Item;
import com.laevatein.internal.entity.ItemViewResources;
import com.laevatein.internal.entity.UncapableCause;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.ImagePreviewActivity;
import com.laevatein.internal.ui.PhotoSelectionActivity;
import com.laevatein.internal.ui.adapter.AlbumPhotoAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.CursorAdapter;
import android.widget.CheckBox;
import android.widget.GridView;

/**
 * @author KeithYokoma
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

    public static void setUpGridView(Fragment fragment, ItemViewResources resources, SelectedUriCollection collection) {
        GridView gridView = (GridView) fragment.getView().findViewById(R.id.l_grid_photo);
        AlbumPhotoAdapter adapter = new AlbumPhotoAdapter(fragment.getActivity(), null, resources, collection);
        adapter.registerCheckStateListener((AlbumPhotoAdapter.CheckStateListener) fragment);
        gridView.setAdapter(adapter);
    }

    public static void tearDownGridView(Fragment fragment) {
        GridView gridView = (GridView) fragment.getView().findViewById(R.id.l_grid_photo);
        AlbumPhotoAdapter adapter = (AlbumPhotoAdapter) gridView.getAdapter();
        adapter.unregisterCheckStateListener();
    }

    public static void setCursor(Fragment fragment, Cursor cursor) {
        GridView gridView = (GridView) fragment.getView().findViewById(R.id.l_grid_photo);
        CursorAdapter adapter = (CursorAdapter) gridView.getAdapter();
        adapter.swapCursor(cursor);
    }

    public static void callPreview(Context context, Item item) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.putExtra(ImagePreviewActivity.EXTRA_ITEM, item);
        context.startActivity(intent);
    }

    public static void syncCheckState(Context context, SelectedUriCollection collection, Item item, CheckBox checkBox) {
        Uri uri = item.buildContentUri();
        if (collection.isSelected(uri)) {
            removeSelection(collection, uri, checkBox);
        } else {
            addSelection(context, collection, uri, checkBox);
        }
    }

    public static void removeSelection(SelectedUriCollection collection, Uri uri, CheckBox checkBox) {
        collection.remove(uri);
        checkBox.setChecked(false);
    }

    public static void addSelection(Context context, SelectedUriCollection collection, Uri uri, CheckBox checkBox) {
        UncapableCause cause = collection.isAcceptable(uri);
        if (cause == null) {
            collection.add(uri);
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
            FragmentActivity activity = (FragmentActivity) context;
            SupportSimpleAlertDialogFragment.newInstance(cause.getErrorMessageRes())
                    .show(activity.getSupportFragmentManager(), SupportSimpleAlertDialogFragment.TAG);
        }
    }

    public static void callCheckStateListener(AlbumPhotoAdapter.CheckStateListener listener) {
        if (listener == null) {
            return;
        }
        listener.onUpdate();
    }
}