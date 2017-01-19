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

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;

import com.laevatein.internal.entity.DialogResources;
import com.laevatein.internal.entity.ErrorViewResources;
import com.laevatein.internal.entity.ErrorViewSpec;
import com.laevatein.internal.entity.ItemViewResources;
import com.laevatein.internal.entity.PreviewViewResources;
import com.laevatein.internal.entity.SelectionSpec;
import com.laevatein.internal.entity.ViewResourceSpec;
import com.laevatein.ui.ImagePreviewActivity;
import com.laevatein.ui.PhotoSelectionActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Fluent API for building photo select specification.
 *
 * @author KeithYokoma
 * @version 1.0.0
 * @since 2014/03/19
 */
@SuppressWarnings("unused") // public APIs
public final class SelectionSpecBuilder {
    public static final String TAG = SelectionSpecBuilder.class.getSimpleName();
    private final Laevatein mLaevatein;
    private final Set<MimeType> mMimeType;
    private final SelectionSpec mSelectionSpec;
    @StyleRes
    private int mActivityTheme;
    private ItemViewResources mItemViewResources;
    private PreviewViewResources mPreviewViewResources;
    private ErrorViewResources mCountOverErrorSpec;
    private ErrorViewResources mUnderQualityErrorSpec;
    private ErrorViewResources mOverQualityErrorSpec;
    private ErrorViewResources mTypeErrorSpec;
    private DialogResources mConfirmDialogSpec;
    private boolean mEnableCapture;
    private boolean mEnableSelectedView;
    private int mActivityOrientation;
    private List<Uri> mResumeList;
    private Class<? extends PhotoSelectionActivity> mPhotoSelectionActivityClass;
    private Class<? extends ImagePreviewActivity> mPreviewActivityClass = ImagePreviewActivity.class;

    /**
     * Constructs a new specification builder on the context.
     * @param laevatein a requester context wrapper.
     * @param mimeType MimeType set to select.
     */
    /* package */ SelectionSpecBuilder(Laevatein laevatein, Set<MimeType> mimeType) {
        mLaevatein = laevatein;
        mMimeType = mimeType;
        mSelectionSpec = new SelectionSpec();
        mResumeList = new ArrayList<Uri>();
        mActivityOrientation = -1;
    }

    /**
     * Sets the theme of activities.
     *
     * @param theme theme of activity
     * @return the specification builder context.
     */
    public SelectionSpecBuilder theme(@StyleRes int theme) {
        mActivityTheme = theme;
        return this;
    }

    /**
     * Sets the binding cell of the grid view with the specified layout resource for photo list.
     * @param layoutId a layout resource id.
     * @param imageViewId an id for the image view.
     * @param checkBoxId an id for the check box.
     */
    public SelectionSpecBuilder bindEachImageWith(int layoutId, int imageViewId, int checkBoxId) {
        mItemViewResources = new ItemViewResources(layoutId, imageViewId, checkBoxId);
        return this;
    }

    /**
     * Sets the binding page of the ViewPager with the specified layout resource for photo list.
     *
     * @param layoutId    a layout resource id.
     * @param imageViewId an id for the image view.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder bindPreviewImageWith(int layoutId, int imageViewId) {
        mPreviewViewResources = new PreviewViewResources(layoutId, imageViewId);
        return this;
    }

    /**
     * Sets the limitation of a selectable count within the specified range.
     * @param min minimum value to select.
     * @param max maximum value to select.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder count(int min, int max) {
        mSelectionSpec.setMinSelectable(min);
        mSelectionSpec.setMaxSelectable(max);
        return this;
    }

    /**
     * Sets the error view specification for the error of count over.
     * @param type error view type.
     * @param errorMessageId an error message resource id.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder countOver(ErrorViewResources.ViewType type, int errorMessageId) {
        return countOver(type, -1, errorMessageId);
    }

    /**
     * Sets the error view specification for the error of count over.
     * @param type error view type.
     * @param errorTitleId an error title resource id. If type is not {@see ErrorViewResources.ViewType.DIALOG}, this parameter is ignored.
     * @param errorMessageId an error message resource id.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder countOver(ErrorViewResources.ViewType type, int errorTitleId, int errorMessageId) {
        mCountOverErrorSpec = type.createSpec(errorTitleId, errorMessageId);
        return this;
    }

    /**
     * Sets the flag to determine whether the list of which image has been selected should be shown or not.
     * The flag is set as false by default.
     * @param enableSelectedView the flag of visibility.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder enableSelectedView(boolean enableSelectedView) {
        mEnableSelectedView = enableSelectedView;
        return this;
    }

    /**
     * Sets the error view specification for the error of quality un-satisfaction.
     * @param type error view type.
     * @param errorMessageId an error message resource id.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder underQuality(ErrorViewResources.ViewType type, int errorMessageId) {
        return underQuality(type, -1, errorMessageId);
    }

    /**
     * Sets the error view specification for the error of quality un-satisfaction.
     * @param type error view type.
     * @param errorTitleId an error title resource id. If type is not {@see ErrorViewResources.ViewType.DIALOG}, this parameter is ignored.
     * @param errorMessageId an error message resource id.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder underQuality(ErrorViewResources.ViewType type, int errorTitleId, int errorMessageId) {
        mUnderQualityErrorSpec = type.createSpec(errorTitleId, errorMessageId);
        return this;
    }

    /**
     * Sets the error view specification for the error of quality un-satisfaction..
     * @param type error view type.
     * @param errorMessageId an error message resource id.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder overQuality(ErrorViewResources.ViewType type, int errorMessageId) {
        return overQuality(type, -1, errorMessageId);
    }

    /**
     * Sets the error view specification for the error of quality un-satisfaction..
     * @param type error view type.
     * @param errorTitleId an error title resource id. If type is not {@see ErrorViewResources.ViewType.DIALOG}, this parameter is ignored.
     * @param errorMessageId an error message resource id.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder overQuality(ErrorViewResources.ViewType type, int errorTitleId, int errorMessageId) {
        mOverQualityErrorSpec = type.createSpec(errorTitleId, errorMessageId);
        return this;
    }

    /**
     * Sets the error view specification for the error of type validation.
     * @param type error view type.
     * @param errorMessageId an error message resource id.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder invalidType(ErrorViewResources.ViewType type, int errorMessageId) {
        return invalidType(type, -1, errorMessageId);
    }

    /**
     * Sets the error view specification for the error of type validation.
     * @param type error view type.
     * @param errorTitleId an error title resource id. If type is not {@see ErrorViewResources.ViewType.DIALOG}, this parameter is ignored.
     * @param errorMessageId an error message resource id.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder invalidType(ErrorViewResources.ViewType type, int errorTitleId, int errorMessageId) {
        mTypeErrorSpec = type.createSpec(errorTitleId, errorMessageId);
        return this;
    }

    /**
     * Sets the error view specification for the press back without finish.
     * @param errorMessageId an error message resource id.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder confirmDialog(int errorMessageId) {
        return confirmDialog(-1, errorMessageId);
    }

    /**
     * Sets the error view specification for the press back without finish.
     * @param errorTitleId an error title resource id. If type is not {@see ErrorViewResources.ViewType.DIALOG}, this parameter is ignored.
     * @param errorMessageId an error message resource id.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder confirmDialog(int errorTitleId, int errorMessageId) {
        mConfirmDialogSpec = new DialogResources(errorTitleId, errorMessageId);
        return this;
    }

    /**
     * Sets the limitation of a selectable image quality by pixel count within the specified range.
     * @param minPixel minimum value to select.
     * @param maxPixel maximum value to select.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder quality(int minPixel, int maxPixel) {
        mSelectionSpec.setMinPixels(minPixel);
        mSelectionSpec.setMaxPixels(maxPixel);
        return this;
    }

    /**
     * Sets the Activity instead of PhotoSelectionActivity
     * @param photoSelectionActivityClass an Activity called on photo selecting
     * @return the specification builder context.
     */
    public SelectionSpecBuilder photoSelectionActivityClass(Class<? extends PhotoSelectionActivity> photoSelectionActivityClass) {
        mPhotoSelectionActivityClass = photoSelectionActivityClass;
        return this;
    }

    /**
     * Sets the Activity instead of ImagePreviewActivity
     *
     * @param previewActivityClass an Activity called on photo preview
     * @return the specification builder context.
     */
    public SelectionSpecBuilder previewActivityClass(Class<? extends ImagePreviewActivity> previewActivityClass) {
        mPreviewActivityClass = previewActivityClass;
        return this;
    }

    /**
     * Sets the default selection to resume photo picking activity.
     * @param uriList to set selected as default.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder resume(List<Uri> uriList) {
        if (uriList == null) { // nothing to do.
            return this;
        }
        mResumeList.addAll(uriList);
        return this;
    }

    /**
     * Determines whether the photo capturing is enabled or not on the camera photo grid view.
     * This flag is false by default.
     * @param enable whether to enable capturing or not.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder capture(boolean enable) {
        mEnableCapture = enable;
        return this;
    }

    public SelectionSpecBuilder restrictOrientation(int activityOrientation) {
        mActivityOrientation = activityOrientation;
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

        mSelectionSpec.setMimeTypeSet(mMimeType);
        if (mActivityTheme == 0) {
            mActivityTheme = R.style.L_DefaultTheme;
        }

        ViewResourceSpec viewSpec = new ViewResourceSpec.Builder()
                .setTheme(mActivityTheme)
                .setPreviewClass(mPreviewActivityClass)
                .setItemViewResources(mItemViewResources)
                .setPreviewViewResources(mPreviewViewResources)
                .setEnableCapture(mEnableCapture)
                .setEnableSelectedView(mEnableSelectedView)
                .setActivityOrientation(mActivityOrientation)
                .create();
        ErrorViewSpec errorSpec = new ErrorViewSpec.Builder()
                .setCountOverSpec(mCountOverErrorSpec)
                .setOverQualitySpec(mOverQualityErrorSpec)
                .setUnderQualitySpec(mUnderQualityErrorSpec)
                .setTypeSpec(mTypeErrorSpec)
                .setConfirmSpec(mConfirmDialogSpec)
                .create();

        if (mPhotoSelectionActivityClass == null) {
            mPhotoSelectionActivityClass = PhotoSelectionActivity.class;
        }

        Intent intent = new Intent(activity, mPhotoSelectionActivityClass);
        intent.putExtra(PhotoSelectionActivity.EXTRA_VIEW_SPEC, viewSpec);
        intent.putExtra(PhotoSelectionActivity.EXTRA_ERROR_SPEC, errorSpec);
        intent.putExtra(PhotoSelectionActivity.EXTRA_SELECTION_SPEC, mSelectionSpec);
        intent.putParcelableArrayListExtra(PhotoSelectionActivity.EXTRA_RESUME_LIST, (ArrayList<? extends android.os.Parcelable>) mResumeList);

        Fragment fragment = mLaevatein.getFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }
}
