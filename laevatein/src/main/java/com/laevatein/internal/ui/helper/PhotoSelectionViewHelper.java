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

import com.laevatein.R;
import com.laevatein.internal.entity.Album;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.PhotoGridFragment;
import com.laevatein.internal.ui.PhotoSelectionActivity;
import com.laevatein.internal.ui.SelectedPhotoGridFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author KeithYokoma
 * @since 2014/03/25
 * @version 1.0.0
 * @hide
 */
public final class PhotoSelectionViewHelper {
    private PhotoSelectionViewHelper() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static void refreshOptionsMenuState(PhotoSelectionActivity activity, SelectedUriCollection collection, Menu menu) {
        if (collection == null) {
            return;
        }
        MenuItem select = menu.findItem(R.id.action_finish_select);
        updateSelectMenuState(select, collection, activity.isDrawerOpen());
    }

    public static void updateSelectMenuState(MenuItem item, SelectedUriCollection collection, boolean drawerOpen) {
        if (item == null) {
            return;
        }
        item.setVisible(!drawerOpen);
        item.setEnabled(!collection.isEmpty() && collection.isCountInRange());
    }

    public static void setPhotoGridFragment(FragmentActivity activity, DrawerLayout drawer, Album album) {
        Fragment fragment = album.isChecked() ?
                SelectedPhotoGridFragment.newInstance() :
                PhotoGridFragment.newInstance(album);
        FragmentManager manager = activity.getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.l_container_grid_fragment, fragment, PhotoGridFragment.TAG)
                .commit();
        drawer.closeDrawers();
    }

    public static void refreshGridView(FragmentActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(PhotoGridFragment.TAG);
        if (fragment instanceof PhotoGridFragment) {
            ((PhotoGridFragment) fragment).refreshGrid();
        }
    }
}
