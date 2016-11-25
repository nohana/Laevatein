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
package com.laevatein.internal.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.laevatein.R;

/**
 * @author HiroyukiSeto
 * @version 2.0.0
 * @hide
 * @since 2016/11/25
 */
public final class PreviewViewResources implements Parcelable {
    public static final Creator<PreviewViewResources> CREATOR = new Creator<PreviewViewResources>() {
        @Override
        public PreviewViewResources createFromParcel(Parcel source) {
            return new PreviewViewResources(source);
        }

        @Override
        public PreviewViewResources[] newArray(int size) {
            return new PreviewViewResources[size];
        }
    };
    private static volatile PreviewViewResources sDefault;
    private final int mLayoutId;
    private final int mImageViewId;

    public PreviewViewResources(int layoutId, int imageViewId) {
        mLayoutId = layoutId;
        mImageViewId = imageViewId;
    }

    /* package */ PreviewViewResources(Parcel source) {
        mLayoutId = source.readInt();
        mImageViewId = source.readInt();
    }

    public static PreviewViewResources getDefault() {
        if (sDefault == null) {
            sDefault = new PreviewViewResources(R.layout.l_fragment_default_preview, R.id.l_default_image_zoom_view);
        }
        return sDefault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mLayoutId);
        dest.writeInt(mImageViewId);
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public int getImageViewId() {
        return mImageViewId;
    }
}
