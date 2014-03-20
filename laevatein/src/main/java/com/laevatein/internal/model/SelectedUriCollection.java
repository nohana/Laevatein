package com.laevatein.internal.model;

import com.amalgam.os.BundleUtils;

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

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mUris = new LinkedHashSet<Uri>();
        } else {
            List<Uri> saved = savedInstanceState.getParcelableArrayList(STATE_SELECTION);
        }
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
}
