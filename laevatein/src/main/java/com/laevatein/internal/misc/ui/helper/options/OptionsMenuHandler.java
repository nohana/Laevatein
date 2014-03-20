package com.laevatein.internal.misc.ui.helper.options;

import android.app.Activity;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 * @hide
 */
public interface OptionsMenuHandler<A extends Activity, E> {
    public boolean handle(A activity, E extra);
}