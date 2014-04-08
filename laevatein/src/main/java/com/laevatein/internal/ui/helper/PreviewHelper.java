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
import com.laevatein.internal.entity.ActionViewResources;
import com.laevatein.internal.entity.ErrorViewResources;
import com.laevatein.internal.entity.ErrorViewSpec;
import com.laevatein.internal.entity.Item;
import com.laevatein.internal.entity.SelectionSpec;
import com.laevatein.internal.entity.UncapableCause;
import com.laevatein.internal.ui.ImagePreviewActivity;
import com.laevatein.internal.utils.ErrorViewUtils;
import com.laevatein.internal.utils.PhotoMetadataUtils;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * @author KeithYokoma
 * @since 2014/03/24
 * @version 1.0.0
 * @hide
 */
public final class PreviewHelper {
    private PreviewHelper() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static void setUpActionBar(ActionBarActivity activity) {
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
                    return;
                }
                UncapableCause cause = PhotoMetadataUtils
                        .isAcceptable(activity, spec, photo.buildContentUri());
                if (cause == null) {
                    activity.getStateHolder().setChecked(isChecked);
                    return;
                }

                ErrorViewResources error = cause.getErrorResources(errorSpec);
                ErrorViewUtils.showErrorView(activity, error);
                checkBox.setChecked(false);
            }
        });
    }

    public static void assign(Activity activity, Item item) {
        ImageViewTouch image = (ImageViewTouch) activity.findViewById(R.id.l_image_zoom_view);
        image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        Uri uri = item.buildContentUri();
        Point size = PhotoMetadataUtils.getBitmapSize(activity.getContentResolver(), uri);
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
