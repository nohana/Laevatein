package com.laevatein.internal.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amalgam.os.BundleUtils;
import com.bumptech.glide.Priority;
import com.laevatein.GlideApp;
import com.laevatein.internal.entity.PreviewViewResources;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by hiroyuki.seto on 15/06/16.
 *
 * @hide
 */
public class PreviewFragment extends Fragment {
    private static final String ARGS_URI = BundleUtils.buildKey(PhotoGridFragment.class, "ARGS_URI");
    private static final String ARGS_RESOURCES = BundleUtils.buildKey(PhotoGridFragment.class, "ARGS_RESOURCES");

    private PreviewViewResources mViewResources;

    public static PreviewFragment newInstance(Uri uri, PreviewViewResources resources) {
        PreviewFragment fragment = new PreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_URI, uri);
        bundle.putParcelable(ARGS_RESOURCES, resources);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewResources = getArguments().getParcelable(ARGS_RESOURCES);
        return inflater.inflate(mViewResources.getLayoutId(), container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageViewTouch image = getView().findViewById(mViewResources.getImageViewId());
        image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        Uri uri = getArguments().getParcelable(ARGS_URI);
        GlideApp.with(getActivity()).load(uri).priority(Priority.HIGH).fitCenter().centerInside().into(image);
    }

    public void resetView() {
        ((ImageViewTouch) getView()).resetMatrix();
    }
}
