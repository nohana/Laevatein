package com.laevatein.internal.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.laevatein.internal.ui.adapter.PreviewPagerAdapter;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Created by hiroyuki.seto on 15/06/16.
 *
 * @hide
 */
public class MyViewPager extends ViewPager {

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        PreviewPagerAdapter adapter = (PreviewPagerAdapter) getAdapter();
        return ((ImageViewTouch) adapter.getCurrentFragment().getView()).canScroll(dx) || super.canScroll(v, checkV, dx, x, y);
    }
}
