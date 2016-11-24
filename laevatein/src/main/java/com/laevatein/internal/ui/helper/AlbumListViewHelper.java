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
package com.laevatein.internal.ui.helper;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.CursorAdapter;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import com.amalgam.os.HandlerUtils;
import com.laevatein.R;
import com.laevatein.internal.entity.Album;
import com.laevatein.internal.misc.ui.FragmentUtils;
import com.laevatein.internal.ui.AlbumListFragment;
import com.laevatein.internal.ui.adapter.DevicePhotoAlbumAdapter;

/**
 * @author KeithYokoma
 * @version 1.0.0
 * @hide
 * @since 2014/03/20
 */
public final class AlbumListViewHelper {
    private AlbumListViewHelper() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static void setUpHeader(Fragment fragment) {
        TypedValue value = new TypedValue();
        fragment.getContext().getTheme().resolveAttribute(R.attr.l_drawerHeaderLayout, value, true);
        if (value.resourceId == 0) {
            return;
        }
        ListView listView = (ListView) FragmentUtils.findViewById(fragment, R.id.l_list_album);
        LayoutInflater inflater = LayoutInflater.from(fragment.getContext());
        View header = inflater.inflate(value.resourceId, listView, false);
        listView.addHeaderView(header);
    }

    public static void setUpListView(Fragment fragment, AdapterView.OnItemClickListener listener) {
        ListView listView = (ListView) FragmentUtils.findViewById(fragment, R.id.l_list_album);
        listView.setOnItemClickListener(listener);
        listView.setAdapter(new DevicePhotoAlbumAdapter(fragment.getActivity(), null));
    }

    public static void setCursor(Fragment fragment, Cursor cursor) {
        ListView listView = (ListView) FragmentUtils.findViewById(fragment, R.id.l_list_album);
        CursorAdapter adapter;
        if (listView.getHeaderViewsCount() > 0) {
            adapter = (CursorAdapter) ((HeaderViewListAdapter) listView.getAdapter()).getWrappedAdapter();
        } else {
            adapter = (CursorAdapter) listView.getAdapter();
        }
        adapter.swapCursor(cursor);
    }

    public static void callOnSelect(AlbumListFragment.OnDirectorySelectListener listener, Cursor cursor) {
        Album album = Album.valueOf(cursor);
        listener.onSelect(album);
    }

    public static void callOnDefaultSelect(final Fragment fragment, final AlbumListFragment.OnDirectorySelectListener listener, final Cursor cursor) {
        HandlerUtils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                FragmentActivity fragmentActivity = fragment.getActivity();
                if (fragmentActivity == null) {
                    return;
                }

                FragmentManager manager = fragmentActivity.getSupportFragmentManager();
                Fragment f = manager.findFragmentById(R.id.l_container_grid_fragment);
                if (f != null) {
                    return;
                }

                cursor.moveToFirst();
                callOnSelect(listener, cursor);
            }
        });
    }

    public static void setCheckedState(Fragment fragment, int position) {
        ListView listView = (ListView) FragmentUtils.findViewById(fragment, R.id.l_list_album);
        listView.setItemChecked(position, true);
    }
}