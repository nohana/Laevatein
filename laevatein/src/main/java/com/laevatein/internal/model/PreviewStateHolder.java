package com.laevatein.internal.model;

import com.amalgam.os.BundleUtils;
import com.laevatein.internal.ui.ImagePreviewActivity;

import android.app.Activity;
import android.os.Bundle;

import java.lang.ref.WeakReference;

/**
 * @author keishin.yokomaku
 * @since 2014/04/03
 */
public class PreviewStateHolder {
    private static final String STATE_CHECKED = BundleUtils.buildKey(PreviewStateHolder.class, "STATE_CHECKED");
    private boolean mChecked;
    private WeakReference<Activity> mContext;

    public PreviewStateHolder(Activity activity) {
        mContext = new WeakReference<Activity>(activity);
    }

    public void onCreate() {
        Activity activity = mContext.get();
        if (activity == null) {
            return;
        }
        mChecked = activity.getIntent().getBooleanExtra(ImagePreviewActivity.EXTRA_DEFAULT_CHECKED, false);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        mChecked = savedInstanceState.getBoolean(STATE_CHECKED);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_CHECKED, mChecked);
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }
}
