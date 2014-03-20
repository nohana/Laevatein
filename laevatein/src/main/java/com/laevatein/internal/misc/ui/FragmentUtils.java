package com.laevatein.internal.misc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

/**
 * @author keishin.yokomaku
 * @since 2014/03/20
 * @version 1.0.0
 * @hide
 */
public final class FragmentUtils {
    private FragmentUtils() {}

    public static <T extends Parcelable> T getIntentParcelableExtra(Fragment fragment, String key) {
        Activity activity = fragment.getActivity();
        Intent intent = activity.getIntent();
        return intent.getParcelableExtra(key);
    }
}
