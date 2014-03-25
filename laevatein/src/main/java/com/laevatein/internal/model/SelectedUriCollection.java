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
import com.laevatein.MimeType;
import com.laevatein.internal.entity.SelectionSpec;
import com.laevatein.internal.entity.UncapableCause;
import com.laevatein.internal.utils.PhotoMetadataUtils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import java.lang.ref.WeakReference;
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
    private final WeakReference<Context> mContext;
    private Set<Uri> mUris;
    private SelectionSpec mSpec;

    public SelectedUriCollection(Context context) {
        mContext = new WeakReference<Context>(context);
    }

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

    public UncapableCause isAcceptable(Uri uri) {
        if (!isSelectableType(uri)) {
            return UncapableCause.FILE_TYPE;
        }
        if (!hasEnoughQuality(uri)) {
            return UncapableCause.QUALITY;
        }
        return null;
    }

    public boolean hasEnoughQuality(Uri uri) {
        Context context = mContext.get();
        if (context == null) {
            return false;
        }

        int pixels = PhotoMetadataUtils.getPixelsCount(context.getContentResolver(), uri);
        return mSpec.getMinPixels() <= pixels && pixels <= mSpec.getMaxPixels();
    }

    public boolean isSelectableType(Uri uri) {
        Context context = mContext.get();
        if (context == null) {
            return false;
        }

        ContentResolver resolver = context.getContentResolver();
        for (MimeType type : mSpec.getMimeTypeSet()) {
            if (type.checkType(resolver, uri)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCountInRange() {
        return mSpec.getMinSelectable() <= mUris.size() && mUris.size() <= mSpec.getMaxSelectable();
    }

    public int count() {
        return mUris.size();
    }

    public int maxCount() {
        return mSpec.getMaxSelectable();
    }
}
