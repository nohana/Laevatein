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
package com.laevatein;

import com.laevatein.internal.ui.PhotoSelectionActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

/**
 *
 * @author KeithYokoma
 * @since 2014/03/19
 * @version 1.0.0
 */
@SuppressWarnings("unused") // public APIs
public final class Laevatein {
    public static final String TAG = Laevatein.class.getSimpleName();
    private final WeakReference<Activity> mContext;

    protected Laevatein(Activity context) {
        mContext = new WeakReference<Activity>(context);
    }

    /**
     *
     * @param activity
     * @return
     */
    public static Laevatein from(Activity activity) {
        return new Laevatein(activity);
    }

    /**
     *
     * @param data
     * @return
     */
    public static List<Uri> obtainResult(Intent data) {
        return data.getParcelableArrayListExtra(PhotoSelectionActivity.EXTRA_RESULT_SELECTION);
    }

    /**
     *
     * @param mimeType
     * @return
     */
    public RequestBuilder choose(Set<MimeType> mimeType) {
        return new RequestBuilder(this, mimeType);
    }

    /**
     *
     * @return
     */
    /* package */ @Nullable Activity getActivity() {
        return mContext.get();
    }
}