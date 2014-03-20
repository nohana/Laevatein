package com.laevatein.internal.ui.adapter;

import com.laevatein.internal.entity.Directory;
import com.laevatein.internal.entity.DirectoryViewResources;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import javax.annotation.Nullable;

/**
 * @author keishin.yokomaku
 * @since 2014/03/20
 */
public class DirectoryListAdapter extends ArrayAdapter<Directory> {
    private DirectoryViewResources mResources;

    public DirectoryListAdapter(Context context, DirectoryViewResources resources, List<Directory> objects) {
        super(context, resources.getLayoutId(), resources.getLabelViewId(), objects);
        mResources = resources;
    }

    @Nullable
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (view == null) {
            return null;
        }

        ImageView icon = (ImageView) view.findViewById(mResources.getImageViewId());
        TextView dirName = (TextView) view.findViewById(mResources.getLabelViewId());
        Directory directory = getItem(position);

        dirName.setText(directory.getDirectoryName());
        return view;
    }
}