package com.laevatein.internal.ui;

import com.amalgam.os.BundleUtils;
import com.laevatein.R;
import com.laevatein.internal.entity.Item;
import com.laevatein.internal.ui.helper.PreviewHelper;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * @author keishin.yokomaku
 * @since 2014/03/24
 * @version 1.0.0
 * @hide
 */
public class ImagePreviewActivity extends ActionBarActivity {
    public static final String EXTRA_ITEM = BundleUtils.buildKey(ImagePreviewActivity.class, "EXTRA_ITEM");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        PreviewHelper.assign(this, getIntent().<Item>getParcelableExtra(EXTRA_ITEM));
    }
}
