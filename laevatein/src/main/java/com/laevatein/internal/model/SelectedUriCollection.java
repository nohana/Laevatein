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

import com.amalgam.os.BundleUtils;
import com.laevatein.internal.entity.SelectionSpec;

import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 * @hide
 */
public class SelectedUriCollection {
    private static final String STATE_SELECTION = BundleUtils.buildKey(SelectedUriCollection.class, "STATE_SELECTION");
    private Set<Uri> mUris;
    private SelectionSpec mSpec;

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mUris = new LinkedHashSet<Uri>();
        } else {
            List<Uri> saved = savedInstanceState.getParcelableArrayList(STATE_SELECTION);
            mUris = new LinkedHashSet<Uri>(saved);
        }
    }

    public void prepareSelectionSpec(SelectionSpec spec) {
        mSpec = spec;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_SELECTION, new ArrayList<Uri>(mUris));
    }

    public boolean add(Uri uri) {
        return mUris.add(uri);
    }

    public boolean remove(Uri uri) {
        return mUris.remove(uri);
    }

    public List<Uri> asList() {
        return new ArrayList<Uri>(mUris);
    }

    public boolean isEmpty() {
        return mUris == null || mUris.isEmpty();
    }

    public boolean isSelected(Uri uri) {
        return mUris.contains(uri);
    }

    public boolean isAcceptable(Uri uri) {
        return true; // TODO
    }

    public int count() {
        return mUris.size();
    }
}
