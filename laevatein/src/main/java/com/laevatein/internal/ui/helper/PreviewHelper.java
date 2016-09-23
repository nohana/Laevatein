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
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
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
import com.laevatein.internal.ui.adapter.PreviewPagerAdapter;
import com.laevatein.internal.utils.ErrorViewUtils;
import com.laevatein.internal.utils.PhotoMetadataUtils;

import java.util.ArrayList;
import java.util.List;

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
        PreviewPagerAdapter adapter = new PreviewPagerAdapter(activity.getSupportFragmentManager(), activity);
        ViewPager pager = (ViewPager) activity.findViewById(R.id.l_pager);
        pager.setAdapter(adapter);
    }

    public static void setUpActionBar(ActionBarActivity activity) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.l_toolbar);
        toolbar.setTitle(activity.getApplicationContext().getString(R.string.l_detail_photo_title));
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
        checkBox.setChecked(activity.getStateHolder().isChecked(photo.buildContentUri()));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ViewPager pager = (ViewPager) activity.findViewById(R.id.l_pager);
                Uri currentUri = ((PreviewPagerAdapter) pager.getAdapter()).getUri(pager.getCurrentItem());
                if (!isChecked) {
                    activity.getStateHolder().setChecked(currentUri, false);
                    return;
                }
                UncapableCause cause = PhotoMetadataUtils
                        .isAcceptable(activity, spec, currentUri);
                int currentCount = activity.getStateHolder().getChechedCount();
                if (!activity.getStateHolder().isChecked(currentUri) && currentCount + 1 > spec.getMaxSelectable()) {
                    cause = UncapableCause.OVER_COUNT;
                }
                if (cause == null) {
                    activity.getStateHolder().setChecked(currentUri, true);
                    return;
                }

                ErrorViewResources error = cause.getErrorResources(errorSpec);
                ErrorViewUtils.showErrorView(activity, error);
                checkBox.setChecked(false);
                activity.getStateHolder().setChecked(currentUri, false);
            }
        });
    }

    public static void sendBackResult(ImagePreviewActivity activity) {
        Intent intent = new Intent();
        List<Uri> checked = activity.getStateHolder().getAllChecked();
        intent.putParcelableArrayListExtra(ImagePreviewActivity.EXTRA_RESULT_CHECKED, (ArrayList<Uri>) checked);
        activity.setResult(Activity.RESULT_OK, intent);
    }
}
