package com.laevatein.internal.ui.adapter;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.laevatein.internal.ui.PreviewFragment;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Created by hiroyuki.seto on 15/06/16.
 *
 * @hide
 */
public class PreviewPagerAdapter extends FragmentPagerAdapter {

    private Fragment mCurrentFragment;

    private ArrayList<Uri> mUris = new ArrayList<>();


    public PreviewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return PreviewFragment.newInstance(mUris.get(position));
    }

    @Override
    public int getCount() {
        return mUris.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (mCurrentFragment != object) {
            if (mCurrentFragment != null) {
                ((ImageViewTouch) mCurrentFragment.getView()).resetMatrix();
            }
            mCurrentFragment = (Fragment) object;
        }
        super.setPrimaryItem(container, position, object);
    }

    public Uri getUri(int position) {
        return mUris.get(position);
    }

    public void addAll(List<Uri> uris) {
        mUris.addAll(uris);
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

}
