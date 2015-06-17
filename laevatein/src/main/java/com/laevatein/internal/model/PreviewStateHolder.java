package com.laevatein.internal.model;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.amalgam.os.BundleUtils;
import com.laevatein.internal.ui.ImagePreviewActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @author keishin.yokomaku
 * @since 2014/04/03
 */
public class PreviewStateHolder {
    private static final String STATE_CHECKED = BundleUtils.buildKey(PreviewStateHolder.class, "STATE_CHECKED");
    private ArrayList<Uri> mChecked;
    private WeakReference<Activity> mContext;

    public PreviewStateHolder(Activity activity) {
        mContext = new WeakReference<Activity>(activity);
    }

    public void onCreate() {
        Activity activity = mContext.get();
        if (activity == null) {
            return;
        }
        mChecked = activity.getIntent().getParcelableArrayListExtra(ImagePreviewActivity.EXTRA_DEFAULT_CHECKED);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        mChecked = savedInstanceState.getParcelableArrayList(STATE_CHECKED);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_CHECKED, mChecked);
    }

    public boolean isChecked(Uri uri) {
        return mChecked.contains(uri);
    }

    public void setChecked(Uri uri, boolean checked) {
        if (checked) {
            mChecked.add(uri);
        } else {
            mChecked.remove(uri);
        }
    }

    public int getChechedCount(){
        return mChecked.size();
    }

    public ArrayList<Uri> asList() {
        return new ArrayList<Uri>(mChecked);
    }

}
