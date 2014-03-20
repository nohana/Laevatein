package com.laevatein.internal.ui;

import com.laevatein.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 */
public class DirectoryListFragment extends Fragment {
    private OnDirectorySelectListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDirectorySelectListener) activity;
        } catch (ClassCastException e) {
            throw new IllegalStateException("the host activity should implement OnDirectorySelectListener.", e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_directory, container, false);
    }

    public interface OnDirectorySelectListener {
        public void onSelect();
    }
}