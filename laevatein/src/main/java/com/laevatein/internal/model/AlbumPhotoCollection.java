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
import com.laevatein.internal.entity.Album;
import com.laevatein.internal.loader.AlbumPhotoLoader;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.lang.ref.WeakReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author KeithYokoma
 * @since 2014/03/24
 * @version 1.0.0
 * @hide
 */
public class AlbumPhotoCollection implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 2;
    private static final String ARGS_ALBUM = BundleUtils.buildKey(AlbumPhotoCollection.class, "ARGS_ALBUM");
    private static final String ARGS_ENABLE_CAPTURE = BundleUtils.buildKey(AlbumPhotoCollection.class, "ARGS_ENABLE_CAPTURE");
    private WeakReference<Context> mContext;
    private LoaderManager mLoaderManager;
    private AlbumPhotoCallbacks mCallbacks;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }

        Album album = args.getParcelable(ARGS_ALBUM);
        if (album == null) {
            return null;
        }

        if (album.isCamera()) {
            boolean capture = args.getBoolean(ARGS_ENABLE_CAPTURE, false);
            return AlbumPhotoLoader.newInstance(context, album, capture);
        }
        return AlbumPhotoLoader.newInstance(context, album);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }

        mCallbacks.onLoad(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }

        mCallbacks.onReset();
    }

    public void onCreate(@Nonnull FragmentActivity context, @Nonnull AlbumPhotoCallbacks callbacks) {
        mContext = new WeakReference<Context>(context);
        mLoaderManager = context.getSupportLoaderManager();
        mCallbacks = callbacks;
    }

    public void onDestroy() {
        mLoaderManager.destroyLoader(LOADER_ID);
        mCallbacks = null;
    }

    public void load(@Nullable Album target) {
        load(target, false);
    }

    public void load(@Nullable Album target, boolean enableCapture) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_ALBUM, target);
        args.putBoolean(ARGS_ENABLE_CAPTURE, enableCapture);
        mLoaderManager.initLoader(LOADER_ID, args, this);
    }

    public static interface AlbumPhotoCallbacks {
        public void onLoad(Cursor cursor);
        public void onReset();
    }
}
