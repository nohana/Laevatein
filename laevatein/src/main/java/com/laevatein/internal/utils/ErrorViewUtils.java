package com.laevatein.internal.utils;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.amalgam.app.SupportSimpleAlertDialogFragment;
import com.laevatein.internal.entity.DialogResources;
import com.laevatein.internal.entity.ErrorViewResources;
import com.laevatein.internal.misc.ui.ConfirmationDialogFragment;

/**
 * @author KeithYokoma
 * @version 1.0.0
 * @hide
 * @since 2014/04/07
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
            if (resources.getTitleId() == -1) {
                SupportSimpleAlertDialogFragment.newInstance(resources.getMessageId())
                        .show(activity.getSupportFragmentManager(), SupportSimpleAlertDialogFragment.TAG);
            } else {
                SupportSimpleAlertDialogFragment.newInstance(resources.getTitleId(), resources.getMessageId())
                        .show(activity.getSupportFragmentManager(), SupportSimpleAlertDialogFragment.TAG);
            }
        } else if (resources.getViewType() == ErrorViewResources.ViewType.TOAST) {
            Toast.makeText(activity.getApplicationContext(), resources.getMessageId(), Toast.LENGTH_LONG).show();
        } else if (resources.getViewType() == ErrorViewResources.ViewType.SNACKBAR) {
            Snackbar.make(activity.findViewById(android.R.id.content), resources.getMessageId(), Snackbar.LENGTH_LONG).show();
        }
    }

    public static void showConfirmDialog(FragmentActivity activity, DialogResources resources) {
        if (resources.getTitleId() == -1) {
            ConfirmationDialogFragment.newInstance(resources.getMessageId())
                    .show(activity.getSupportFragmentManager(), ConfirmationDialogFragment.TAG);
        } else {
            ConfirmationDialogFragment.newInstance(resources.getTitleId(), resources.getMessageId())
                    .show(activity.getSupportFragmentManager(), ConfirmationDialogFragment.TAG);
        }
    }
}
