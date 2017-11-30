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
package com.laevatein.internal.model;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.amalgam.os.BundleUtils;
import com.laevatein.internal.entity.SelectionSpec;
import com.laevatein.internal.entity.UncapableCause;
import com.laevatein.internal.utils.PhotoMetadataUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author KeithYokoma
 * @version 1.0.0
 * @hide
 * @since 2014/03/20
 */
public class SelectedUriCollection {
    private static final String STATE_SELECTION = BundleUtils.buildKey(SelectedUriCollection.class, "STATE_SELECTION");
    private final WeakReference<Context> mContext;
    private Set<Uri> mUris;
    private SelectionSpec mSpec;

    public SelectedUriCollection(Context context) {
        mContext = new WeakReference<>(context);
    }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mUris = new LinkedHashSet<>();
        } else {
            List<Uri> saved = savedInstanceState.getParcelableArrayList(STATE_SELECTION);
            mUris = new LinkedHashSet<>(saved);
        }
    }

    public void prepareSelectionSpec(SelectionSpec spec) {
        mSpec = spec;
    }

    public void setDefaultSelection(List<Uri> uris) {
        mUris.addAll(uris);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_SELECTION, new ArrayList<>(mUris));
    }

    public boolean add(Uri uri) {
        return mUris.add(uri);
    }

    public boolean remove(Uri uri) {
        return mUris.remove(uri);
    }

    public void overwrite(ArrayList<Uri> uriLists) {
        mUris.clear();
        mUris.addAll(uriLists);
    }

    public List<Uri> asList() {
        return new ArrayList<>(mUris);
    }

    public boolean isEmpty() {
        return mUris == null || mUris.isEmpty();
    }

    public boolean isSelected(Uri uri) {
        return mUris.contains(uri);
    }

    public UncapableCause isAcceptable(Uri uri) {
        return PhotoMetadataUtils.isAcceptable(mContext.get(), mSpec, uri);
    }

    public boolean isCountInRange() {
        return mSpec.getMinSelectable() <= mUris.size() && !isCountOver();
    }

    public boolean isCountOver() {
        return mUris.size() > mSpec.getMaxSelectable();
    }

    public int count() {
        return mUris.size();
    }

    public int maxCount() {
        return mSpec.getMaxSelectable();
    }
}
