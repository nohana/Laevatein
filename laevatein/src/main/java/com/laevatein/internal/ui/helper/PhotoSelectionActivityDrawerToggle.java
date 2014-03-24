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

import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

/**
 * @author KeithYokoma
 * @since 2014/03/24
 * @version 1.0.0
 * @hide
 */
public class PhotoSelectionActivityDrawerToggle extends ActionBarDrawerToggle {
    /**
     * Construct a new ActionBarDrawerToggle.
     *
     * <p>The given {@link android.app.Activity} will be linked to the specified {@link android.support.v4.widget.DrawerLayout}.
     * The provided drawer indicator drawable will animate slightly off-screen as the drawer
     * is opened, indicating that in the open state the drawer will move off-screen when pressed
     * and in the closed state the drawer will move on-screen when pressed.</p>
     *
     * <p>String resources must be provided to describe the open/close drawer actions for
     * accessibility services.</p>
     *
     * @param activity                  The Activity hosting the drawer
     * @param drawerLayout              The DrawerLayout to link to the given Activity's ActionBar
     */
    public PhotoSelectionActivityDrawerToggle(Activity activity, DrawerLayout drawerLayout) {
        super(activity, drawerLayout, R.drawable.ic_drawer, R.string.content_desc_open_drawer, R.string.content_desc_close_drawer);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    public void setUpActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
}
