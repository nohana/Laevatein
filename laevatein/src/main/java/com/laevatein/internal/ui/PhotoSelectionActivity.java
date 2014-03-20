package com.laevatein.internal.ui;

import com.amalgam.os.BundleUtils;
import com.laevatein.R;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.helper.options.PhotoSelectionOptionsMenu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 */
public class PhotoSelectionActivity extends ActionBarActivity implements DirectoryListFragment.OnDirectorySelectListener {
    public static final String EXTRA_SELECTION_SPEC = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_SELECTION_SPEC");
    public static final String EXTRA_DIR_VIEW_RES = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_DIR_VIEW_RES");
    public static final String EXTRA_ITEM_VIEW_RES = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_ITEM_VIEW_RES");
    public static final String EXTRA_RESULT_SELECTION = BundleUtils.buildKey(PhotoSelectionActivity.class, "EXTRA_RESULT_SELECTION");
    private final SelectedUriCollection mCollection = new SelectedUriCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);
        mCollection.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mCollection.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PhotoSelectionOptionsMenu menu = PhotoSelectionOptionsMenu.valueOf(item);
        return menu.getHandler().handle(this, null) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelect() {

    }

    /* package */ SelectedUriCollection getCollection() {
        return mCollection;
    }
}