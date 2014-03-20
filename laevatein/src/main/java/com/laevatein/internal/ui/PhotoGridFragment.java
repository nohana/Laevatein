package com.laevatein.internal.ui;

import com.laevatein.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author keishin.yokomaku
 * @since 2014/03/20
 * @version 1.0.0
 */
public class PhotoGridFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid_photo, container, false);
    }
}