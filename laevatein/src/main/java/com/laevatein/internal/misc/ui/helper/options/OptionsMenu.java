package com.laevatein.internal.misc.ui.helper.options;

import android.app.Activity;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 * @hide
 */
public interface OptionsMenu<A extends Activity, H extends OptionsMenuHandler<A, ?>> {
    public int getMenuId();
    public H getHandler();
}