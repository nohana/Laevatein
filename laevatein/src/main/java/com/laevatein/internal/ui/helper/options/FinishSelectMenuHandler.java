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

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import com.laevatein.internal.entity.ErrorViewResources;
import com.laevatein.internal.entity.ErrorViewSpec;
import com.laevatein.internal.model.SelectedUriCollection;
import com.laevatein.internal.utils.ErrorViewUtils;
import com.laevatein.ui.PhotoSelectionActivity;

import java.util.ArrayList;

/**
 * @author KeithYokoma
 * @since 2014/03/25
 */
public class FinishSelectMenuHandler implements PhotoSelectionOptionsMenuHandler {
    @Override
    public boolean handle(PhotoSelectionActivity activity, Void extra) {
        SelectedUriCollection collection = activity.getCollection();
        if (collection.isCountInRange()) {
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(PhotoSelectionActivity.EXTRA_RESULT_SELECTION,
                    (ArrayList<? extends Parcelable>) collection.asList());
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
        } else {
            ErrorViewSpec spec = activity.getIntent().getParcelableExtra(PhotoSelectionActivity.EXTRA_ERROR_SPEC);
            ErrorViewResources res = spec.getCountUnderErrorSpec();
            ErrorViewUtils.showErrorView(activity, res);
        }
        return true;
    }
}
