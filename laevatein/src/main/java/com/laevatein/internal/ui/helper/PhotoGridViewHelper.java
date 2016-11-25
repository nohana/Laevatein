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

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.CursorAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.laevatein.R;
import com.laevatein.internal.entity.Album;
import com.laevatein.internal.entity.ErrorViewResources;
import com.laevatein.internal.entity.ErrorViewSpec;
import com.laevatein.internal.entity.Item;
import com.laevatein.internal.entity.ItemViewResources;
import com.laevatein.internal.entity.UncapableCause;
import com.laevatein.internal.entity.ViewResourceSpec;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.ui.ImagePreviewActivity;
import com.laevatein.internal.ui.PhotoGridFragment;
import com.laevatein.ui.PhotoSelectionActivity;
import com.laevatein.internal.ui.adapter.AlbumPhotoAdapter;
import com.laevatein.internal.utils.ErrorViewUtils;

import java.util.ArrayList;
import java.util.List;

import jp.mixi.compatibility.android.provider.MediaStoreCompat;

/**
 * @author KeithYokoma
 * @version 1.0.0
 * @hide
 * @since 2014/03/24
 */
public final class PhotoGridViewHelper {
    private PhotoGridViewHelper() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static SelectedUriCollection getSelectedPhotoSet(Fragment fragment) {
        return ((PhotoSelectionActivity) fragment.getActivity()).getCollection();
    }

    public static void setUpGridView(Fragment fragment, ItemViewResources resources, SelectedUriCollection collection, AlbumPhotoAdapter.BindViewListener listener) {
        GridView gridView = (GridView) fragment.getView().findViewById(R.id.l_grid_photo);
        AlbumPhotoAdapter adapter = new AlbumPhotoAdapter(fragment.getActivity(), null, resources, collection, listener);
        adapter.registerCheckStateListener((AlbumPhotoAdapter.CheckStateListener) fragment);
        gridView.setAdapter(adapter);

        TextView emptyMessage = (TextView) fragment.getView().findViewById(R.id.l_label_empty);
        emptyMessage.setText(R.string.l_empty_photo);
        gridView.setEmptyView(emptyMessage);
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

    public static void refreshView(Fragment fragment) {
        GridView gridView = (GridView) fragment.getView().findViewById(R.id.l_grid_photo);
        CursorAdapter adapter = (CursorAdapter) gridView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    public static void callPreview(Context context, Item item, List<Uri> checked) {
        PhotoSelectionActivity activity = (PhotoSelectionActivity) context;
        ViewResourceSpec resources = activity.getIntent().getParcelableExtra(PhotoSelectionActivity.EXTRA_VIEW_SPEC);
        Intent intent = new Intent(context, resources.getPreviewActivityClass());
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.l_container_grid_fragment);
        Album album = fragment.getArguments().getParcelable(PhotoGridFragment.ARGS_ALBUM);
        intent.putExtra(ImagePreviewActivity.EXTRA_ALBUM, album);
        intent.putExtra(ImagePreviewActivity.EXTRA_ITEM, item);
        intent.putExtra(ImagePreviewActivity.EXTRA_ERROR_SPEC, activity.getIntent().getParcelableExtra(PhotoSelectionActivity.EXTRA_ERROR_SPEC));
        intent.putExtra(ImagePreviewActivity.EXTRA_SELECTION_SPEC, activity.getIntent().getParcelableExtra(PhotoSelectionActivity.EXTRA_SELECTION_SPEC));
        intent.putExtra(ImagePreviewActivity.EXTRA_VIEW_SPEC, activity.getIntent().getParcelableExtra(PhotoSelectionActivity.EXTRA_VIEW_SPEC));
        intent.putParcelableArrayListExtra(ImagePreviewActivity.EXTRA_DEFAULT_CHECKED, (ArrayList<Uri>) checked);
        activity.startActivityForResult(intent, PhotoSelectionActivity.REQUEST_CODE_PREVIEW);
    }

    public static void callCamera(Context context) {
        PhotoSelectionActivity activity = (PhotoSelectionActivity) context;
        MediaStoreCompat compat = activity.getMediaStoreCompat();
        String preparedUri = compat.invokeCameraCapture(activity, PhotoSelectionActivity.REQUEST_CODE_CAPTURE);
        activity.prepareCapture(preparedUri);
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
        FragmentActivity activity = (FragmentActivity) context;
        ErrorViewSpec spec = activity.getIntent().getParcelableExtra(PhotoSelectionActivity.EXTRA_ERROR_SPEC);

        if (cause == null) {
            ErrorViewResources countSpec = spec.getCountErrorSpec();
            collection.add(uri);
            if (collection.isCountOver() && !countSpec.isNoView()) {
                ErrorViewUtils.showErrorView(activity, countSpec);
                collection.remove(uri);
                checkBox.setChecked(false);
                return;
            }
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
            ErrorViewUtils.showErrorView(activity, cause.getErrorResources(spec));
        }
    }

    public static void callCheckStateListener(AlbumPhotoAdapter.CheckStateListener listener) {
        if (listener == null) {
            return;
        }
        listener.onUpdate();
    }
}