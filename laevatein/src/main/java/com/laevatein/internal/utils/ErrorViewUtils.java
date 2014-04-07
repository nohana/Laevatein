package com.laevatein.internal.utils;

import com.amalgam.app.SupportSimpleAlertDialogFragment;
import com.laevatein.internal.entity.ErrorViewResources;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * @author KeithYokoma
 * @since 2014/04/07
 * @version 1.0.0
 * @hide
 */
public final class ErrorViewUtils {
    private ErrorViewUtils() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static void showErrorView(FragmentActivity activity, ErrorViewResources resources) {
        if (resources.isNoView()) {
            return;
        }

        if (resources.getViewType() == ErrorViewResources.ViewType.DIALOG) {
            SupportSimpleAlertDialogFragment.newInstance(resources.getMessageId())
                    .show(activity.getSupportFragmentManager(), SupportSimpleAlertDialogFragment.TAG);
        } else if (resources.getViewType() == ErrorViewResources.ViewType.TOAST) {
            Toast.makeText(activity.getApplicationContext(), resources.getMessageId(), Toast.LENGTH_LONG).show();
        }
    }
}
