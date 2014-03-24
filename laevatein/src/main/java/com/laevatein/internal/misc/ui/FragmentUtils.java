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
