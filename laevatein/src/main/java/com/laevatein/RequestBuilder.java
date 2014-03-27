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
package com.laevatein;

import com.laevatein.internal.entity.AlbumViewResources;
import com.laevatein.internal.entity.ItemViewResources;
import com.laevatein.internal.entity.SelectionSpec;
import com.laevatein.internal.ui.PhotoSelectionActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Fluent API for building photo select specification.
 *
 * @author KeithYokoma
 * @since 2014/03/19
 * @version 1.0.0
 */
@SuppressWarnings("unused") // public APIs
public final class RequestBuilder {
    public static final String TAG = RequestBuilder.class.getSimpleName();
    private final Laevatein mLaevatein;
    private final Set<MimeType> mMimeType;
    private final SelectionSpec mSelectionSpec;
    private ItemViewResources mItemViewResources;
    private AlbumViewResources mAlbumViewResources;
    private List<Uri> mResumeList;

    /**
     * Constructs a new specification builder on the context.
     * @param laevatein a requester context wrapper.
     * @param mimeType MimeType set to select.
     */
    /* package */ RequestBuilder(Laevatein laevatein, Set<MimeType> mimeType) {
        mLaevatein = laevatein;
        mMimeType = mimeType;
        mSelectionSpec = new SelectionSpec();
        mResumeList = new ArrayList<Uri>();
    }

    /**
     * Sets the binding cell of the grid view with the specified layout resource for photo list.
     * @param layoutId a layout resource id.
     * @param imageViewId an id for the image view.
     * @param checkBoxId an id for the check box.
     * @return the specification builder context.
     */
    public RequestBuilder bindEachImageWith(int layoutId, int imageViewId, int checkBoxId) {
        mItemViewResources = new ItemViewResources(layoutId,    imageViewId, checkBoxId);
        return this;
    }

    /**
     * Sets the binding cell of the list view with the specified layout resource for album list.
     * @param layoutId a layout resource id.
     * @param imageViewId an id for the image view.
     * @param directoryNameViewId an id for the text view of the album name
     * @return the specification builder context.
     */
    public RequestBuilder bindEachAlbumWith(int layoutId, int imageViewId, int directoryNameViewId) {
        mAlbumViewResources = new AlbumViewResources(layoutId, imageViewId, directoryNameViewId);
        return this;
    }

    /**
     * Sets the limitation of a selectable count within the specified range.
     * @param min minimum value to select.
     * @param max maximum value to select.
     * @return the specification builder context.
     */
    public RequestBuilder count(int min, int max) {
        mSelectionSpec.setMinSelectable(min);
        mSelectionSpec.setMaxSelectable(max);
        return this;
    }

    /**
     * Sets the limitation of a selectable image quality by pixel count within the specified range.
     * @param minPixel minimum value to select.
     * @param maxPixel maximum value to select.
     * @return the specification builder context.
     */
    public RequestBuilder quality(int minPixel, int maxPixel) {
        mSelectionSpec.setMinPixels(minPixel);
        mSelectionSpec.setMaxPixels(maxPixel);
        return this;
    }

    /**
     * Sets the default selection to resume photo picking activity.
     * @param uriList to set selected as default.
     * @return the specification builder context.
     */
    public RequestBuilder resume(List<Uri> uriList) {
        if (uriList == null) { // nothing to do.
            return this;
        }
        mResumeList.addAll(uriList);
        return this;
    }

    /**
     * Start to select photo.
     * @param requestCode identity of the requester activity.
     */
    public void forResult(int requestCode) {
        Activity activity = mLaevatein.getActivity();
        if (activity == null) {
            return; // cannot continue;
        }
        if (mAlbumViewResources == null) {
            mAlbumViewResources = AlbumViewResources.getDefault();
        }
        if (mItemViewResources == null) {
            mItemViewResources = ItemViewResources.getDefault();
        }
        mSelectionSpec.setMimeTypeSet(mMimeType);

        Intent intent = new Intent(activity, PhotoSelectionActivity.class);
        intent.putExtra(PhotoSelectionActivity.EXTRA_DIR_VIEW_RES, mAlbumViewResources);
        intent.putExtra(PhotoSelectionActivity.EXTRA_ITEM_VIEW_RES, mItemViewResources);
        intent.putExtra(PhotoSelectionActivity.EXTRA_SELECTION_SPEC, mSelectionSpec);
        intent.putParcelableArrayListExtra(PhotoSelectionActivity.EXTRA_RESUME_LIST,
                (ArrayList<? extends android.os.Parcelable>) mResumeList);
        activity.startActivityForResult(intent, requestCode);
    }
}