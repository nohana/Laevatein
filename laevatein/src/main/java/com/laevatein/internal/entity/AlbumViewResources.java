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

import com.laevatein.R;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 * @hide
 */
public final class AlbumViewResources implements Parcelable {
    public static final Creator<AlbumViewResources> CREATOR = new Creator<AlbumViewResources>() {
        @Override
        public AlbumViewResources createFromParcel(Parcel source) {
            return new AlbumViewResources(source);
        }

        @Override
        public AlbumViewResources[] newArray(int size) {
            return new AlbumViewResources[size];
        }
    };
    private static volatile AlbumViewResources sDefault;
    private final int mLayoutId;
    private final int mImageViewId;
    private final int mLabelViewId;

    public AlbumViewResources(int layoutId, int imageViewId, int labelViewId) {
        mLayoutId = layoutId;
        mImageViewId = imageViewId;
        mLabelViewId = labelViewId;
    }

    /* package */ AlbumViewResources(Parcel source) {
        mLayoutId = source.readInt();
        mImageViewId = source.readInt();
        mLabelViewId = source.readInt();
    }

    public static AlbumViewResources getDefault() {
        if (sDefault == null) {
            sDefault = new AlbumViewResources(R.layout.list_item_default_album, R.id.default_list_image, R.id.default_directory_label);
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
        dest.writeInt(mLabelViewId);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("layoutId:").append(mLayoutId)
                .append(", imageId:").append(mImageViewId)
                .append(", labelId:").append(mLabelViewId)
                .toString();
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public int getImageViewId() {
        return mImageViewId;
    }

    public int getLabelViewId() {
        return mLabelViewId;
    }
}
