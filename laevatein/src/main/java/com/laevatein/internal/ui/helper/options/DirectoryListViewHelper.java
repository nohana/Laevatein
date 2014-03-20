package com.laevatein.internal.ui.helper.options;

import com.laevatein.R;
import com.laevatein.internal.entity.Directory;
import com.laevatein.internal.entity.DirectoryViewResources;
import com.laevatein.internal.ui.adapter.DirectoryListAdapter;

import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * @author keishin.yokomaku
 * @since 2014/03/20
 */
public class DirectoryListViewHelper {
    public static void setUpListView(Fragment fragment, AdapterView.OnItemClickListener listener, DirectoryViewResources resources) {
        ListView listView = (ListView) fragment.getView().findViewById(R.id.l_list_directory);
        listView.setOnItemClickListener(listener);
        listView.setAdapter(new DirectoryListAdapter(fragment.getActivity(), resources, Directory.getPictureDirs()));
    }
}