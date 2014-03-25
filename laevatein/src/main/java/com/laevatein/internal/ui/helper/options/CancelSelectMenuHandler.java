package com.laevatein.internal.ui.helper.options;

import com.laevatein.internal.misc.ui.ConfirmationDialogFragment;
import com.laevatein.internal.ui.PhotoSelectionActivity;

/**
 * @author keishin.yokomaku
 * @since 2014/03/25
 */
public class CancelSelectMenuHandler implements PhotoSelectionOptionsMenuHandler {
    @Override
    public boolean handle(PhotoSelectionActivity activity, Void extra) {
        ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance(0, 0);
        dialog.show(activity.getSupportFragmentManager(), ConfirmationDialogFragment.TAG);
        return true;
    }
}
