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
import com.laevatein.internal.entity.Album;
import com.laevatein.internal.entity.SelectionSpec;
import com.laevatein.internal.misc.ui.ConfirmationDialogFragment;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.helper.PhotoSelectionActivityDrawerToggle;
import com.laevatein.internal.ui.helper.PhotoSelectionViewHelper;
import com.laevatein.internal.ui.helper.options.PhotoSelectionOptionsMenu;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 */
public class PhotoSelectionActivity extends ActionBarActivity implements
        AlbumListFragment.OnDirectorySelectListener,
        ConfirmationDialogFragment.ConfirmationSelectionListener {
    public static final String EXTRA_SELECTION_SPEC = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_SELECTION_SPEC");
    public static final String EXTRA_DIR_VIEW_RES = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_DIR_VIEW_RES");
    public static final String EXTRA_ITEM_VIEW_RES = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_ITEM_VIEW_RES");
    public static final String EXTRA_RESULT_SELECTION = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_RESULT_SELECTION");
    private final SelectedUriCollection mCollection = new SelectedUriCollection();
    private PhotoSelectionActivityDrawerToggle mToggle;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);
        mCollection.onCreate(savedInstanceState);
        mCollection.prepareSelectionSpec(getIntent().<SelectionSpec>getParcelableExtra(EXTRA_SELECTION_SPEC));
        mDrawer = (DrawerLayout) findViewById(R.id.container_drawer);
        mToggle = new PhotoSelectionActivityDrawerToggle(this, mDrawer);
        mToggle.setUpActionBar(getSupportActionBar());
        mDrawer.setDrawerListener(mToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mCollection.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_options_select_photo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        PhotoSelectionViewHelper.refreshOptionsMenuState(this, mCollection, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PhotoSelectionOptionsMenu menu = PhotoSelectionOptionsMenu.valueOf(item);
        return mToggle.onOptionsItemSelected(item) || menu.getHandler().handle(this, null) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelect(Album album) {
        PhotoSelectionViewHelper.setPhotoGridFragment(this, mDrawer, album);
    }

    public SelectedUriCollection getCollection() {
        return mCollection;
    }

    public boolean isDrawerOpen() {
        return mDrawer.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void onPositive() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onNegative() {
        // nothing to do.
    }
}