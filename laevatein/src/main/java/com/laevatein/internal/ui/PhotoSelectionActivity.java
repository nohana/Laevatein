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
import com.amalgam.os.HandlerUtils;
import com.laevatein.R;
import com.laevatein.internal.entity.Album;
import com.laevatein.internal.entity.Item;
import com.laevatein.internal.entity.SelectionSpec;
import com.laevatein.internal.misc.ui.ConfirmationDialogFragment;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.helper.PhotoSelectionActivityDrawerToggle;
import com.laevatein.internal.ui.helper.PhotoSelectionViewHelper;
import com.laevatein.internal.ui.helper.options.PhotoSelectionOptionsMenu;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import jp.mixi.compatibility.android.provider.MediaStoreCompat;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 * @hide
 */
public class PhotoSelectionActivity extends ActionBarActivity implements
        AlbumListFragment.OnDirectorySelectListener,
        ConfirmationDialogFragment.ConfirmationSelectionListener {
    public static final String EXTRA_RESUME_LIST = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_RESUME_LIST");
    public static final String EXTRA_SELECTION_SPEC = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_SELECTION_SPEC");
    public static final String EXTRA_DIR_VIEW_RES = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_DIR_VIEW_RES");
    public static final String EXTRA_ITEM_VIEW_RES = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_ITEM_VIEW_RES");
    public static final String EXTRA_RESULT_SELECTION = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_RESULT_SELECTION");
    public static final String EXTRA_ENABLE_CAPTURE = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_ENABLE_CAPTURE");
    public static final String EXTRA_ACTION_VIEW_RES = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_ACTION_VIEW_RES");
    public static final String STATE_CAPTURE_PHOTO_URI = BundleUtils.buildKey(PhotoSelectionActivity.class, "STATE_CAPTURE_PHOTO_URI");
    public static final int REQUEST_CODE_CAPTURE = 1;
    public static final int REQUEST_CODE_PREVIEW = 2;
    private final SelectedUriCollection mCollection = new SelectedUriCollection(this);
    private MediaStoreCompat mMediaStoreCompat;
    private PhotoSelectionActivityDrawerToggle mToggle;
    private DrawerLayout mDrawer;
    private String mCapturePhotoUriHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_select_photo);
        mMediaStoreCompat = new MediaStoreCompat(this, HandlerUtils.getMainHandler());
        mCapturePhotoUriHolder = savedInstanceState != null ? savedInstanceState.getString(STATE_CAPTURE_PHOTO_URI) : "";
        mCollection.onCreate(savedInstanceState);
        mCollection.prepareSelectionSpec(getIntent().<SelectionSpec>getParcelableExtra(EXTRA_SELECTION_SPEC));
        mCollection.setDefaultSelection(getIntent().<Uri>getParcelableArrayListExtra(EXTRA_RESUME_LIST));
        mDrawer = (DrawerLayout) findViewById(R.id.l_container_drawer);
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
        outState.putString(STATE_CAPTURE_PHOTO_URI, mCapturePhotoUriHolder);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        mMediaStoreCompat.destroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Uri captured = mMediaStoreCompat.getCapturedPhotoUri(data, mCapturePhotoUriHolder);
            if (captured != null) {
                mCollection.add(captured);
                mMediaStoreCompat.cleanUp(mCapturePhotoUriHolder);
            }
        } else if (requestCode == REQUEST_CODE_PREVIEW && resultCode == Activity.RESULT_OK) {
            boolean checked = data.getBooleanExtra(ImagePreviewActivity.EXTRA_RESULT_CHECKED, false);
            Item item = data.getParcelableExtra(ImagePreviewActivity.EXTRA_RESULT_ITEM);
            if (checked) {
                mCollection.add(item.buildContentUri());
            } else {
                mCollection.remove(item.buildContentUri());
            }
            PhotoSelectionViewHelper.refreshGridView(this);
            supportInvalidateOptionsMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.l_activity_options_select_photo, menu);
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
    public void onBackPressed() {
        if (mCollection.isEmpty()) {
            setResult(Activity.RESULT_CANCELED);
            super.onBackPressed();
            return;
        }
        ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance(R.string.l_confirm_dialog_title, R.string.l_confirm_dialog_message);
        dialog.show(getSupportFragmentManager(), ConfirmationDialogFragment.TAG);
    }

    @Override
    public void onSelect(Album album) {
        PhotoSelectionViewHelper.setPhotoGridFragment(this, mDrawer, album);
    }

    public SelectedUriCollection getCollection() {
        return mCollection;
    }

    public MediaStoreCompat getMediaStoreCompat() { return mMediaStoreCompat; }

    public void prepareCapture(String uri) {
        mCapturePhotoUriHolder = uri;
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