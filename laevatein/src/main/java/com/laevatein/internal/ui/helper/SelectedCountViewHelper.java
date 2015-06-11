package com.laevatein.internal.ui.helper;

import android.view.View;
import android.widget.TextView;

import com.laevatein.R;
import com.laevatein.internal.entity.ViewResourceSpec;
import com.laevatein.internal.misc.ui.FragmentUtils;
import com.laevatein.internal.ui.PhotoSelectionActivity;
import com.laevatein.internal.ui.SelectedCountFragment;

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

    public static void setUpCountView(final SelectedCountFragment fragment) {
        View view = FragmentUtils.findViewById(fragment, R.id.l_container_count_view);
        TextView label = (TextView) fragment.getView().findViewById(R.id.l_label_selected_count);
        ViewResourceSpec spec = FragmentUtils.getIntentParcelableExtra(fragment, PhotoSelectionActivity.EXTRA_VIEW_SPEC);
        view.setBackgroundResource(spec.getCountViewResources().getBackgroundColorResource());
        label.setTextColor(fragment.getResources().getColor(spec.getCountViewResources().getTextColorResource()));
        view.setEnabled(spec.isEnableSelectedView());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.getListener().onClickSelectedView();
            }
        });
    }

    public static void updateCountView(PhotoSelectionActivity activity, SelectedCountFragment fragment) {
        if (activity == null || fragment == null || activity.getCollection() == null || fragment.getView() == null) {
            return;
        }
        TextView label = (TextView) fragment.getView().findViewById(R.id.l_label_selected_count);
        int max = activity.getCollection().maxCount();
        int current = activity.getCollection().count();
        label.setText(activity.getString(R.string.l_format_selection_count, current, max));
    }
}
