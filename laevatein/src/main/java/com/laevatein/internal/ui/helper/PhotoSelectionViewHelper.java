package com.laevatein.internal.ui.helper;

import com.laevatein.R;
import com.laevatein.internal.model.SelectedUriCollection;

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

    public static void refreshOptionsMenuState(SelectedUriCollection collection, Menu menu) {
        MenuItem select = menu.findItem(R.id.action_finish_select);
        updateSelectMenuState(select, !collection.isEmpty());
    }

    public static void updateSelectMenuState(MenuItem item, boolean condition) {
        if (item == null) {
            return;
        }
        item.setEnabled(condition);
    }
}
