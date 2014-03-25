package com.laevatein.internal.ui.helper.options;

import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.ui.PhotoSelectionActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author keishin.yokomaku
 * @since 2014/03/25
 */
public class FinishSelectMenuHandler implements PhotoSelectionOptionsMenuHandler {
    @Override
    public boolean handle(PhotoSelectionActivity activity, Void extra) {
        SelectedUriCollection collection = activity.getCollection();
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(PhotoSelectionActivity.EXTRA_RESULT_SELECTION,
                (ArrayList<? extends Parcelable>) collection.asList());
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
        return true;
    }
}
