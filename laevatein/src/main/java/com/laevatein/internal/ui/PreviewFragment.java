package com.laevatein.internal.ui;

import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amalgam.os.BundleUtils;
import com.laevatein.internal.entity.PreviewViewResources;
import com.laevatein.internal.utils.PhotoMetadataUtils;
import com.squareup.picasso.Picasso;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by hiroyuki.seto on 15/06/16.
 *
 * @hide
 */
public class PreviewFragment extends Fragment {
    private static final String ARGS_URI = BundleUtils.buildKey(PhotoGridFragment.class, "ARGS_URI");
    private static final String ARGS_LAYOUT_ID = BundleUtils.buildKey(PhotoGridFragment.class, "ARGS_LAYOUT_ID");
    private static final String ARGS_IMAGE_VIEW_ID = BundleUtils.buildKey(PhotoGridFragment.class, "ARGS_IMAGE_VIEW_ID");

    private int mLayoutId;
    private int mImageViewId;

    public static PreviewFragment newInstance(Uri uri, PreviewViewResources resources) {
        PreviewFragment fragment = new PreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_URI, uri);
        bundle.putInt(ARGS_LAYOUT_ID, resources.getLayoutId());
        bundle.putInt(ARGS_IMAGE_VIEW_ID, resources.getImageViewId());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutId = getArguments().getInt(ARGS_LAYOUT_ID);
        mImageViewId = getArguments().getInt(ARGS_IMAGE_VIEW_ID);
        return inflater.inflate(mLayoutId, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageViewTouch image = (ImageViewTouch) getView().findViewById(mImageViewId);
        image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        Uri uri = getArguments().getParcelable(ARGS_URI);
        Point size = PhotoMetadataUtils.getBitmapSize(getActivity().getContentResolver(), uri, getActivity());
        Picasso.with(getActivity()).load(uri).priority(Picasso.Priority.HIGH).resize(size.x, size.y).centerInside().into(image);
    }

    public void resetView() {
        ((ImageViewTouch) getView()).resetMatrix();
    }
}
