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
import com.laevatein.internal.entity.ErrorViewResources;
import com.laevatein.internal.entity.ErrorViewSpec;
import com.laevatein.internal.entity.ViewResourceSpec;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.PhotoGridFragment;
import com.laevatein.ui.PhotoSelectionActivity;
import com.laevatein.internal.ui.SelectedPhotoGridFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
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

    public static void setUpActivity(PhotoSelectionActivity activity) {
        ViewResourceSpec spec = activity.getIntent().getParcelableExtra(PhotoSelectionActivity.EXTRA_VIEW_SPEC);
        if (spec != null && spec.needActivityOrientationRestriction()) {
            activity.setRequestedOrientation(spec.getActivityOrientation());
        }
    }

    public static void refreshOptionsMenuState(PhotoSelectionActivity activity, SelectedUriCollection collection, Menu menu) {
        if (collection == null) {
            return;
        }
        ErrorViewSpec spec = activity.getIntent().getParcelableExtra(PhotoSelectionActivity.EXTRA_ERROR_SPEC);
        ErrorViewResources res = spec.getCountUnderErrorSpec();
        MenuItem select = menu.findItem(R.id.action_finish_select);
        updateSelectMenuState(select, res, collection, activity.isDrawerOpen());
    }

    public static void updateSelectMenuState(MenuItem item, ErrorViewResources res, SelectedUriCollection collection, boolean drawerOpen) {
        if (item == null) {
            return;
        }
        item.setVisible(!drawerOpen);
        if (res.isNoView()) {
            item.setEnabled(!collection.isEmpty() && collection.isCountInRange());
        } else {
            item.setEnabled(!collection.isEmpty());
        }
    }

    public static void setPhotoGridFragment(FragmentActivity activity, DrawerLayout drawer, Album album) {
        Fragment fragment = PhotoGridFragment.newInstance(album);
        FragmentManager manager = activity.getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.l_container_grid_fragment, fragment, PhotoGridFragment.TAG)
                .commit();
        if (((PhotoSelectionActivity) activity).isDrawerOpen()) {
            drawer.closeDrawers();
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    public static void setSelectedGridFragment(FragmentActivity activity) {
        Fragment fragment = SelectedPhotoGridFragment.newInstance();
        FragmentManager manager = activity.getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.l_container_grid_fragment, fragment, PhotoGridFragment.TAG)
                .commit();
    }

    public static void refreshGridView(FragmentActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(PhotoGridFragment.TAG);
        if (fragment instanceof PhotoGridFragment) {
            ((PhotoGridFragment) fragment).refreshGrid();
        }
    }
}
