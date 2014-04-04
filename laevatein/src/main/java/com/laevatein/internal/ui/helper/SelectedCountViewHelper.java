package com.laevatein.internal.ui.helper;

import com.laevatein.R;
import com.laevatein.internal.ui.PhotoSelectionActivity;
import com.laevatein.internal.ui.SelectedCountFragment;

import android.widget.TextView;

/**
 * @author KeithYokoma
 * @since 2014/04/04
 * @version 1.0.0
 * @hide
 */
public final class SelectedCountViewHelper {
    private SelectedCountViewHelper() {
        throw new AssertionError("oops! the utility class is about to be instantiated...");
    }

    public static void updateCountView(PhotoSelectionActivity activity, SelectedCountFragment fragment) {
        TextView label = (TextView) fragment.getView().findViewById(R.id.l_label_selected_count);
        int max = activity.getCollection().maxCount();
        int current = activity.getCollection().count();
        label.setText(activity.getString(R.string.l_format_selection_count, current, max));
    }
}
