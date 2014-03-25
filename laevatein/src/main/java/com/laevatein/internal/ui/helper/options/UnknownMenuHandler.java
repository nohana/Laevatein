package com.laevatein.internal.ui.helper.options;

import com.laevatein.internal.ui.PhotoSelectionActivity;

/**
 * @author keishin.yokomaku
 * @since 2014/03/25
 */
public class UnknownMenuHandler implements PhotoSelectionOptionsMenuHandler {
    @Override
    public boolean handle(PhotoSelectionActivity activity, Void extra) {
        return false;
    }
}
