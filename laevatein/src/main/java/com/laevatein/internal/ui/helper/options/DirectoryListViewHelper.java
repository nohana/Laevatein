/*
 * Copyright (C) 2014 nohana, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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