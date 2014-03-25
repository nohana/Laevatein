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
import com.laevatein.internal.entity.SelectionErrorResource;
import com.laevatein.internal.entity.SelectionSpec;
import com.laevatein.internal.ui.PhotoSelectionActivity;

import android.app.Activity;
import android.content.Intent;

import java.util.Set;

/**
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
    private final SelectionErrorResource mCountErrorResource;
    private final SelectionErrorResource mQualityErrorResource;
    private ItemViewResources mItemViewResources;
    private AlbumViewResources mAlbumViewResources;

    /**
     *
     * @param laevatein
     * @param mimeType
     */
    /* package */ RequestBuilder(Laevatein laevatein, Set<MimeType> mimeType) {
        mLaevatein = laevatein;
        mMimeType = mimeType;
        mSelectionSpec = new SelectionSpec();
        mCountErrorResource = new SelectionErrorResource();
        mQualityErrorResource = new SelectionErrorResource();
    }

    /**
     *
     * @param layoutId
     * @param imageViewId
     * @param checkBoxId
     * @return
     */
    public RequestBuilder bindEachImageWith(int layoutId, int imageViewId, int checkBoxId) {
        mItemViewResources = new ItemViewResources(layoutId,    imageViewId, checkBoxId);
        return this;
    }

    /**
     *
     * @param layoutId
     * @param imageViewId
     * @param directoryNameViewId
     * @return
     */
    public RequestBuilder bindEachAlbumWith(int layoutId, int imageViewId, int directoryNameViewId) {
        mAlbumViewResources = new AlbumViewResources(layoutId, imageViewId, directoryNameViewId);
        return this;
    }

    /**
     *
     * @param min
     * @param max
     * @return
     */
    public RequestBuilder count(int min, int max) {
        mSelectionSpec.setMinSelectable(min);
        mSelectionSpec.setMaxSelectable(max);
        return this;
    }

    /**
     *
     * @param type
     * @param messageResource
     * @return
     */
    public RequestBuilder outOfCount(ErrorViewType type, int messageResource) {
        mCountErrorResource.setErrorViewType(type);
        mCountErrorResource.setErrorMessage(messageResource);
        return this;
    }

    /**
     *
     * @param minPixel
     * @param maxPixel
     * @return
     */
    public RequestBuilder quality(int minPixel, int maxPixel) {
        mSelectionSpec.setMinPixels(minPixel);
        mSelectionSpec.setMaxPixels(maxPixel);
        return this;
    }

    /**
     *
     * @param type
     * @param messageResource
     * @return
     */
    public RequestBuilder outOfQuality(ErrorViewType type, int messageResource) {
        mQualityErrorResource.setErrorViewType(type);
        mQualityErrorResource.setErrorMessage(messageResource);
        return this;
    }

    /**
     * Start to select photo.
     * @param requestCode
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
        activity.startActivityForResult(intent, requestCode);
    }
}