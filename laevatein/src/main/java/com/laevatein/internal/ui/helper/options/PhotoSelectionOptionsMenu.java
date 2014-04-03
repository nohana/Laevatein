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
    SELECT(R.id.action_finish_select, new FinishSelectMenuHandler()),
    UNKNOWN(-1, new UnknownMenuHandler()); // null object pattern

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
