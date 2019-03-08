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
package com.laevatein.ui;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

import com.amalgam.os.BundleUtils;
import com.laevatein.R;
import com.laevatein.internal.entity.Album;
import com.laevatein.internal.entity.Item;
import com.laevatein.internal.entity.ViewResourceSpec;
import com.laevatein.internal.model.AlbumPhotoCollection;
import com.laevatein.internal.model.PreviewStateHolder;
import com.laevatein.internal.ui.PreviewFragment;
import com.laevatein.internal.ui.adapter.PreviewPagerAdapter;
import com.laevatein.internal.ui.helper.PreviewHelper;

import java.util.ArrayList;
import java.util.List;

import static com.laevatein.R.id.l_default_check_box;

/**
 * @author KeithYokoma
 * @version 1.0.0
 * @hide
 * @since 2014/03/24
 */
public class ImagePreviewActivity extends AppCompatActivity implements AlbumPhotoCollection.AlbumPhotoCallbacks, PreviewPagerAdapter.OnPrimaryItemSetListener {
    public static final String EXTRA_ITEM = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_ITEM");
    public static final String EXTRA_ALBUM = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_ALBUM");
    public static final String EXTRA_ERROR_SPEC = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_ERROR_SPEC");
    public static final String EXTRA_SELECTION_SPEC = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_SELECTION_SPEC");
    public static final String EXTRA_VIEW_SPEC = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_VIEW_SPEC");
    public static final String EXTRA_DEFAULT_CHECKED = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_DEFAULT_CHECKED");
    public static final String EXTRA_RESULT_CHECKED = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_RESULT_CHECKED");
    private final PreviewStateHolder mStateHolder = new PreviewStateHolder(this);

    private ViewPager mPager;
    private CheckBox mCheckBox;

    private final AlbumPhotoCollection mCollection = new AlbumPhotoCollection();
    private boolean mIsAlreadySetPosition;

    private int mPreviousPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewResourceSpec spec = getIntent().getParcelableExtra(EXTRA_VIEW_SPEC);
        setTheme(spec.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_preview);
        mStateHolder.onCreate();
        mStateHolder.onRestoreInstanceState(savedInstanceState);
        PreviewHelper.setUpActivity(this);
        PreviewHelper.setUpActionBar(this);
        mPager = findViewById(R.id.l_pager);
        mCollection.onCreate(this, this);
        Album album = getIntent().getParcelableExtra(EXTRA_ALBUM);
        mCollection.load(album);
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
        final MenuItem item = menu.findItem(R.id.l_action_selection_state);
        mCheckBox = item.getActionView().findViewById(l_default_check_box);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCollection.onDestroy();
    }

    public PreviewStateHolder getStateHolder() {
        return mStateHolder;
    }

    @Override
    public void onLoad(Cursor cursor) {
        List<Uri> uris = new ArrayList<>();
        while (cursor.moveToNext()) {
            final Item item = Item.valueOf(cursor);
            Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, item.getId());
            uris.add(uri);
        }
        PreviewPagerAdapter adapter = (PreviewPagerAdapter) mPager.getAdapter();
        adapter.addAll(uris);
        adapter.notifyDataSetChanged();
        if (!mIsAlreadySetPosition) {
            //onLoad is called many times..
            mIsAlreadySetPosition = true;
            Item selected = getIntent().getParcelableExtra(EXTRA_ITEM);
            int selectedIndex = uris.indexOf(selected.buildContentUri());
            mPager.setCurrentItem(selectedIndex, false);
        }
    }

    @Override
    public void onReset() {
    }

    @Override
    public void onPrimaryItemSet(int position) {
        if (!mIsAlreadySetPosition) {
            return;
        }

        PreviewPagerAdapter adapter = (PreviewPagerAdapter) mPager.getAdapter();
        if (mPreviousPos != -1 && mPreviousPos != position) {
            ((PreviewFragment) adapter.instantiateItem(mPager, mPreviousPos)).resetView();

            Uri uri = adapter.getUri(position);
            if (mStateHolder.isChecked(uri)) {
                mCheckBox.setChecked(true);
            } else {
                mCheckBox.setChecked(false);
            }

        }
        mPreviousPos = position;
    }
}
