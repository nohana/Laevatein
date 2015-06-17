package com.laevatein.internal.ui;

import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amalgam.os.BundleUtils;
import com.laevatein.R;
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

    public static PreviewFragment newInstance(Uri uri) {
        PreviewFragment fragment = new PreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_URI, uri);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.l_fragment_preview, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageViewTouch image = (ImageViewTouch) getView().findViewById(R.id.l_image_zoom_view);
        image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        Uri uri = getArguments().getParcelable(ARGS_URI);
        Point size = PhotoMetadataUtils.getBitmapSize(getActivity().getContentResolver(), uri, getActivity());
        Picasso.with(getActivity()).load(uri).priority(Picasso.Priority.HIGH).resize(size.x, size.y).centerInside().into(image);
    }
}
