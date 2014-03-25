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
package com.laevatein.internal.ui.helper.options;

import com.laevatein.R;
import com.laevatein.internal.misc.ui.ConfirmationDialogFragment;
import com.laevatein.internal.ui.PhotoSelectionActivity;

import android.app.Activity;

/**
 * @author KeithYokoma
 * @since 2014/03/25
 */
public class CancelSelectMenuHandler implements PhotoSelectionOptionsMenuHandler {
    @Override
    public boolean handle(PhotoSelectionActivity activity, Void extra) {
        if (activity.getCollection().isEmpty()) {
            activity.setResult(Activity.RESULT_CANCELED);
            activity.finish();
            return true;
        }
        ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance(
                R.string.confirm_dialog_title, R.string.confirm_dialog_message);
        dialog.show(activity.getSupportFragmentManager(), ConfirmationDialogFragment.TAG);
        return true;
    }
}
