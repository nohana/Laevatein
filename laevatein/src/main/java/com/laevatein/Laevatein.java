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

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.laevatein.internal.ui.PhotoSelectionActivity;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;

/**
 * Photo selection activity set provider.
 * Use {@link #from(android.app.Activity)} to start selection activity with the specification you'd like.
 * And the result will be delivered to {@link Activity#onActivityResult(int, int, android.content.Intent)},
 * and use {@link #obtainResult(android.content.Intent)} for the convenience of receiving the selection result.
 *
 * @author KeithYokoma
 * @version 1.0.0
 * @since 2014/03/19
 */
@SuppressWarnings("unused") // public APIs
public final class Laevatein {
    public static final String TAG = Laevatein.class.getSimpleName();
    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;

    private static PhotoGridViewBindListener bindListener;

    protected Laevatein(Activity context) {
        mContext = new WeakReference<Activity>(context);
        mFragment = null;
    }

    protected Laevatein(Activity activity, Fragment fragment) {
        mContext = new WeakReference<Activity>(activity);
        mFragment = new WeakReference<Fragment>(fragment);
    }

    /**
     * Starts selection from the specified {@link android.app.Activity}.
     *
     * @param activity to start.
     * @return the requester context wrapper.
     */
    public static Laevatein from(Activity activity) {
        return new Laevatein(activity);
    }

    public static Laevatein from(Fragment fragment) {
        return new Laevatein(fragment.getActivity(), fragment);
    }

    /**
     * @param listener
     * @Hide
     */
    public static void setListener(PhotoGridViewBindListener listener) {
        bindListener = listener;
    }

    /**
     * @Hide
     */
    public static void removeListener() {
        bindListener = null;
    }

    /**
     * @Hide
     */
    public static PhotoGridViewBindListener getListener() {
        return bindListener;
    }

    /**
     * Obtains the selection result passed to your {@link Activity#onActivityResult(int, int, android.content.Intent)}.
     *
     * @param data the data.
     * @return the selected {@link android.net.Uri}s.
     */
    public static List<Uri> obtainResult(Intent data) {
        return data.getParcelableArrayListExtra(PhotoSelectionActivity.EXTRA_RESULT_SELECTION);
    }

    /**
     * Specifies the MIME Type to select.
     *
     * @param mimeType the mime type of the photo you would like to choose.
     * @return the specification builder context.
     */
    public SelectionSpecBuilder choose(Set<MimeType> mimeType) {
        return new SelectionSpecBuilder(this, mimeType);
    }

    /**
     * @return the actual requester context.
     */
    /* package */
    @Nullable
    Activity getActivity() {
        return mContext.get();
    }

    /**
     * @return the fragment that is responsible for result handling.
     */
    /* package */
    @Nullable
    Fragment getFragment() {
        return mFragment != null ? mFragment.get() : null;
    }
}
