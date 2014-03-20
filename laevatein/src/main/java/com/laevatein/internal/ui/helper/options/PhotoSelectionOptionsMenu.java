package com.laevatein.internal.ui.helper.options;

import com.laevatein.internal.misc.ui.helper.options.OptionsMenu;
import com.laevatein.internal.ui.PhotoSelectionActivity;

import android.view.MenuItem;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 * @hide
 */
public enum PhotoSelectionOptionsMenu implements OptionsMenu<PhotoSelectionActivity, PhotoSelectionOptionsMenuHandler> {
    SELECT(0, null),
    CANCEL(0, null),
    UNKNOWN(-1, null); // null object pattern

    private final int mMenuId;
    private final PhotoSelectionOptionsMenuHandler mHandler;

    private PhotoSelectionOptionsMenu(int menuId, PhotoSelectionOptionsMenuHandler handler) {
        mMenuId = menuId;
        mHandler = handler;
    }

    public static PhotoSelectionOptionsMenu valueOf(MenuItem item) {
        for (PhotoSelectionOptionsMenu menu : values()) {
            if (menu.getMenuId() == item.getItemId()) {
                return menu;
            }
        }
        return UNKNOWN;
    }

    @Override
    public int getMenuId() {
        return mMenuId;
    }

    @Override
    public PhotoSelectionOptionsMenuHandler getHandler() {
        return mHandler;
    }
}
