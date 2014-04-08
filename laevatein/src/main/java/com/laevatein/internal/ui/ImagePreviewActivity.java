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
package com.laevatein.internal.ui;

import com.amalgam.os.BundleUtils;
import com.laevatein.R;
import com.laevatein.internal.entity.Item;
import com.laevatein.internal.model.PreviewStateHolder;
import com.laevatein.internal.ui.helper.PreviewHelper;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author KeithYokoma
 * @since 2014/03/24
 * @version 1.0.0
 * @hide
 */
public class ImagePreviewActivity extends ActionBarActivity {
    public static final String EXTRA_ITEM = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_ITEM");
    public static final String EXTRA_ERROR_SPEC = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_ERROR_SPEC");
    public static final String EXTRA_SELECTION_SPEC = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_SELECTION_SPEC");
    public static final String EXTRA_VIEW_SPEC = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_VIEW_SPEC");
    public static final String EXTRA_CURRENT_COUNT = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_CURRENT_COUNT");
    public static final String EXTRA_CHECK_VIEW_RES = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_CHECK_VIEW_RES");
    public static final String EXTRA_DEFAULT_CHECKED = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_DEFAULT_CHECKED");
    public static final String EXTRA_RESULT_ITEM = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_RESULT_ITEM");
    public static final String EXTRA_RESULT_CHECKED = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_RESULT_CHECKED");
    private final PreviewStateHolder mStateHolder = new PreviewStateHolder(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_preview);
        mStateHolder.onCreate();
        mStateHolder.onRestoreInstanceState(savedInstanceState);
        PreviewHelper.setUpActionBar(this);
        PreviewHelper.assign(this, getIntent().<Item>getParcelableExtra(EXTRA_ITEM));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mStateHolder.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.l_activity_image_preview, menu);
        PreviewHelper.setUpActionItem(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.home == item.getItemId() || android.R.id.home == item.getItemId()) {
            PreviewHelper.sendBackResult(this);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        PreviewHelper.sendBackResult(this);
        super.onBackPressed();
    }

    public PreviewStateHolder getStateHolder() {
        return mStateHolder;
    }
}
