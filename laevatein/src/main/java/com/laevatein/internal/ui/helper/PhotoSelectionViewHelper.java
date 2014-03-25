package com.laevatein.internal.ui.helper;

import com.laevatein.R;
import com.laevatein.internal.entity.Album;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.PhotoGridFragment;
import com.laevatein.internal.ui.PhotoSelectionActivity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author keishin.yokomaku
 * @since 2014/03/25
 */
public final class PhotoSelectionViewHelper {
    private PhotoSelectionViewHelper() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static void refreshOptionsMenuState(PhotoSelectionActivity activity, SelectedUriCollection collection, Menu menu) {
        MenuItem select = menu.findItem(R.id.action_finish_select);
        MenuItem cancel = menu.findItem(R.id.action_cancel_select);
        MenuItem count = menu.findItem(R.id.action_count_selection);
        updateSelectMenuState(select, collection, activity.isDrawerOpen());
        updateCancelMenuState(cancel, activity.isDrawerOpen());
        updateSelectionCount(count, collection.count());
    }

    public static void updateSelectMenuState(MenuItem item, SelectedUriCollection collection, boolean drawerOpen) {
        if (item == null) {
            return;
        }
        item.setVisible(!drawerOpen);
        item.setEnabled(!collection.isEmpty());
    }

    public static void updateCancelMenuState(MenuItem item, boolean drawerOpen) {
        if (item == null) {
            return;
        }
        item.setVisible(!drawerOpen);
    }

    public static void updateSelectionCount(MenuItem item, int count) {
        if (item == null) {
            return;
        }
        item.setTitle(String.valueOf(count));
    }

    public static void setPhotoGridFragment(FragmentActivity activity, DrawerLayout drawer, Album album) {
        FragmentManager manager = activity.getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container_grid_fragment, PhotoGridFragment.newInstance(album), PhotoGridFragment.TAG)
                .commit();
        drawer.closeDrawers();
        activity.supportInvalidateOptionsMenu();
    }
}
