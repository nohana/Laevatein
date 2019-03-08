package com.laevatein.internal.ui.adapter;

import android.net.Uri;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.laevatein.internal.entity.PreviewViewResources;
import com.laevatein.internal.ui.PreviewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiroyuki.seto on 15/06/16.
 *
 * @hide
 */
public class PreviewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Uri> mUris = new ArrayList<>();

    private PreviewViewResources mPreviewViewResources;
    private OnPrimaryItemSetListener mListener;

    public PreviewPagerAdapter(FragmentManager manager, PreviewViewResources previewViewResources,
                               OnPrimaryItemSetListener listener) {
        super(manager);
        mPreviewViewResources = previewViewResources;
        mListener = listener;
    }

    @Override
    public Fragment getItem(int position) {
        return PreviewFragment.newInstance(mUris.get(position), mPreviewViewResources);
    }

    @Override
    public int getCount() {
        return mUris.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mListener.onPrimaryItemSet(position);
    }

    public Uri getUri(int position) {
        return mUris.get(position);
    }

    public void addAll(List<Uri> uris) {
        mUris.addAll(uris);
    }

    public interface OnPrimaryItemSetListener {
        void onPrimaryItemSet(int position);
    }

}
