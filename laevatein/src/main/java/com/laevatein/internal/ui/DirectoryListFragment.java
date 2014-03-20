package com.laevatein.internal.ui;

import com.laevatein.R;
import com.laevatein.internal.entity.DirectoryViewResources;
import com.laevatein.internal.misc.ui.FragmentUtils;
import com.laevatein.internal.ui.helper.options.DirectoryListViewHelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 */
public class DirectoryListFragment extends Fragment implements AdapterView.OnItemClickListener {
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DirectoryViewResources resources = FragmentUtils.getIntentParcelableExtra(this, PhotoSelectionActivity.EXTRA_DIR_VIEW_RES);
        DirectoryListViewHelper.setUpListView(this, this, resources);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public interface OnDirectorySelectListener {
        public void onSelect();
    }
}