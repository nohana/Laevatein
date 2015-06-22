package com.laevatein;

import android.content.Context;
import android.net.Uri;
import android.view.View;

/**
 * Created by hiroyuki.seto on 15/06/19.
 */
@SuppressWarnings("unused") // public APIs
public interface PhotoGridViewBindListener {
    void onBindView(Context context, View view, Uri uri);
}
