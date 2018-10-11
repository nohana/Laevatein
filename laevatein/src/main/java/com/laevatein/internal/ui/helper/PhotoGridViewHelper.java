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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.laevatein.R;
import com.laevatein.internal.entity.Album;
import com.laevatein.internal.entity.ErrorViewResources;
import com.laevatein.internal.entity.ErrorViewSpec;
import com.laevatein.internal.entity.Item;
import com.laevatein.internal.entity.UncapableCause;
import com.laevatein.internal.entity.ViewResourceSpec;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.PhotoGridFragment;
import com.laevatein.internal.ui.adapter.AlbumPhotoAdapter;
import com.laevatein.internal.ui.adapter.RecyclerViewCursorAdapter;
import com.laevatein.internal.ui.widget.PhotoDecoration;
import com.laevatein.internal.utils.ErrorViewUtils;
import com.laevatein.internal.utils.MediaStoreUtils;
import com.laevatein.ui.ImagePreviewActivity;
import com.laevatein.ui.PhotoSelectionActivity;

import java.util.ArrayList;
import java.util.List;

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

    public static void setUpGridView(Fragment fragment, ViewResourceSpec resources, SelectedUriCollection collection, AlbumPhotoAdapter.BindViewListener listener) {
        RecyclerView recyclerView = fragment.getView().findViewById(R.id.l_recyclerview);
        int spanCount = resources.getItemViewResources().getSpanCount();
        recyclerView.setLayoutManager(new GridLayoutManager(fragment.getContext(), resources.getItemViewResources().getSpanCount()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);

        AlbumPhotoAdapter adapter = new AlbumPhotoAdapter(fragment.getActivity(), resources, collection, listener);
        adapter.registerCheckStateListener((AlbumPhotoAdapter.CheckStateListener) fragment);

        int spacing = fragment.getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerView.addItemDecoration(new PhotoDecoration(spanCount, spacing, false));
        recyclerView.setAdapter(adapter);

        TextView emptyMessage = fragment.getView().findViewById(R.id.l_label_empty);
        if (adapter.getItemCount() == 0) {
            emptyMessage.setVisibility(View.VISIBLE);
            emptyMessage.setText(R.string.l_empty_photo);
        } else {
            emptyMessage.setVisibility(View.GONE);
        }
    }

    public static void tearDownGridView(Fragment fragment) {
        RecyclerView recyclerView = fragment.getView().findViewById(R.id.l_recyclerview);
        AlbumPhotoAdapter adapter = (AlbumPhotoAdapter) recyclerView.getAdapter();
        adapter.unregisterCheckStateListener();
    }

    public static void setCursor(Fragment fragment, Cursor cursor) {
        RecyclerView recyclerView = fragment.getView().findViewById(R.id.l_recyclerview);
        RecyclerViewCursorAdapter adapter = (RecyclerViewCursorAdapter) recyclerView.getAdapter();
        adapter.swapCursor(cursor);

        TextView emptyMessage = fragment.getView().findViewById(R.id.l_label_empty);
        if (adapter.getItemCount() == 0) {
            emptyMessage.setVisibility(View.VISIBLE);
            emptyMessage.setText(R.string.l_empty_photo);
        } else {
            emptyMessage.setVisibility(View.GONE);
        }
    }

    public static void refreshView(Fragment fragment) {
        RecyclerView recyclerView = fragment.getView().findViewById(R.id.l_recyclerview);
        RecyclerViewCursorAdapter adapter = (RecyclerViewCursorAdapter) recyclerView.getAdapter();
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

    public static void callCamera(Context context, String fileProviderAuthorities) {
        PhotoSelectionActivity activity = (PhotoSelectionActivity) context;
        MediaStoreUtils utils = activity.getMediaStoreUtils();
        String preparedUri = utils.invokeCameraCapture(activity, fileProviderAuthorities, PhotoSelectionActivity.REQUEST_CODE_CAPTURE);
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
            ErrorViewResources countSpec = spec.getCountOverErrorSpec();
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