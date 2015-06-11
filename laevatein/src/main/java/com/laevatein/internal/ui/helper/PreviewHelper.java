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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.laevatein.R;
import com.laevatein.internal.entity.ActionViewResources;
import com.laevatein.internal.entity.ErrorViewResources;
import com.laevatein.internal.entity.ErrorViewSpec;
import com.laevatein.internal.entity.Item;
import com.laevatein.internal.entity.SelectionSpec;
import com.laevatein.internal.entity.UncapableCause;
import com.laevatein.internal.entity.ViewResourceSpec;
import com.laevatein.internal.ui.ImagePreviewActivity;
import com.laevatein.internal.utils.ErrorViewUtils;
import com.laevatein.internal.utils.PhotoMetadataUtils;
import com.squareup.picasso.Picasso;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * @author KeithYokoma
 * @version 1.0.0
 * @hide
 * @since 2014/03/24
 */
public final class PreviewHelper {
    private PreviewHelper() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static void setUpActivity(ImagePreviewActivity activity) {
        ViewResourceSpec spec = activity.getIntent().getParcelableExtra(ImagePreviewActivity.EXTRA_VIEW_SPEC);
        if (spec != null && spec.needActivityOrientationRestriction()) {
            activity.setRequestedOrientation(spec.getActivityOrientation());
        }
    }

    public static void setUpActionBar(ActionBarActivity activity) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.l_toolbar);
        toolbar.setTitle("写真詳細");
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public static void setUpActionItem(final ImagePreviewActivity activity, Menu menu) {
        final MenuItem item = menu.findItem(R.id.l_action_selection_state);
        if (item == null) {
            return;
        }
        final Item photo = activity.getIntent().getParcelableExtra(ImagePreviewActivity.EXTRA_ITEM);
        ActionViewResources resources = activity.getIntent().getParcelableExtra(ImagePreviewActivity.EXTRA_CHECK_VIEW_RES);
        final SelectionSpec spec = activity.getIntent().getParcelableExtra(ImagePreviewActivity.EXTRA_SELECTION_SPEC);
        final ErrorViewSpec errorSpec = activity.getIntent().getParcelableExtra(ImagePreviewActivity.EXTRA_ERROR_SPEC);
        if (resources == null) {
            MenuItemCompat.setActionView(item, R.layout.l_action_layout_checkbox);
        } else {
            MenuItemCompat.setActionView(item, resources.getLayoutId());
        }
        final CheckBox checkBox = (CheckBox) MenuItemCompat.getActionView(item).findViewById(resources.getCheckBoxId());
        checkBox.setChecked(activity.getStateHolder().isChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    activity.getStateHolder().setChecked(false);
                    return;
                }
                UncapableCause cause = PhotoMetadataUtils
                        .isAcceptable(activity, spec, photo.buildContentUri());
                int currentCount = activity.getIntent().getIntExtra(ImagePreviewActivity.EXTRA_CURRENT_COUNT, 0);
                if (currentCount + 1 > spec.getMaxSelectable()) {
                    cause = UncapableCause.OVER_COUNT;
                }
                if (cause == null) {
                    activity.getStateHolder().setChecked(true);
                    return;
                }

                ErrorViewResources error = cause.getErrorResources(errorSpec);
                ErrorViewUtils.showErrorView(activity, error);
                checkBox.setChecked(false);
                activity.getStateHolder().setChecked(false);
            }
        });
    }

    public static void assign(Activity activity, Item item) {
        ImageViewTouch image = (ImageViewTouch) activity.findViewById(R.id.l_image_zoom_view);
        image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        Uri uri = item.buildContentUri();
        Point size = PhotoMetadataUtils.getBitmapSize(activity.getContentResolver(), uri, activity);
        Picasso.with(activity).load(item.buildContentUri()).resize(size.x, size.y).centerInside().into(image);
    }

    public static void sendBackResult(ImagePreviewActivity activity) {
        Intent intent = new Intent();
        Item item = activity.getIntent().getParcelableExtra(ImagePreviewActivity.EXTRA_ITEM);
        boolean checked = activity.getStateHolder().isChecked();
        intent.putExtra(ImagePreviewActivity.EXTRA_RESULT_ITEM, item);
        intent.putExtra(ImagePreviewActivity.EXTRA_RESULT_CHECKED, checked);
        activity.setResult(Activity.RESULT_OK, intent);
    }
}
