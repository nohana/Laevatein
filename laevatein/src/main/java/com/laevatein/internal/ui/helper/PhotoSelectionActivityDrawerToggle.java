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

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.laevatein.R;

/**
 * @author KeithYokoma
 * @since 2014/03/24
 * @version 1.0.0
 * @hide
 */
public class PhotoSelectionActivityDrawerToggle extends ActionBarDrawerToggle {
    private FragmentActivity mActivity;
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
        super(activity, drawerLayout, R.string.l_content_desc_open_drawer, R.string.l_content_desc_close_drawer);
        mActivity = (FragmentActivity) activity;
        drawerLayout.setDrawerShadow(R.drawable.l_drawer_shadow, GravityCompat.START);
    }

    public PhotoSelectionActivityDrawerToggle(Activity activity, DrawerLayout drawerLayout,Toolbar toolbar) {
        super(activity, drawerLayout, toolbar, R.string.l_content_desc_open_drawer, R.string.l_content_desc_close_drawer);
        mActivity = (FragmentActivity) activity;
        drawerLayout.setDrawerShadow(R.drawable.l_drawer_shadow, GravityCompat.START);
    }

    public void setUpActionBar(ActionBar actionBar) {
        if (actionBar == null) {
            return; // FIXME for now just check null or not to avoid NPE, consider compatibility layer to deal with tool bar later on.
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        mActivity.supportInvalidateOptionsMenu();
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        mActivity.supportInvalidateOptionsMenu();
    }
}
