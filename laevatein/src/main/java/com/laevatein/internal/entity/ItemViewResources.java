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
public final class ItemViewResources implements Parcelable {
    public static final Creator<ItemViewResources> CREATOR = new Creator<ItemViewResources>() {
        @Override
        public ItemViewResources createFromParcel(Parcel source) {
            return new ItemViewResources(source);
        }

        @Override
        public ItemViewResources[] newArray(int size) {
            return new ItemViewResources[size];
        }
    };
    private static volatile ItemViewResources sDefault;
    private final int mLayoutId;
    private final int mImageViewId;
    private final int mCheckBoxId;

    public ItemViewResources(int layoutId, int imageViewId, int checkBoxId) {
        mLayoutId = layoutId;
        mImageViewId = imageViewId;
        mCheckBoxId = checkBoxId;
    }

    /* package */ ItemViewResources(Parcel source) {
        mLayoutId = source.readInt();
        mImageViewId = source.readInt();
        mCheckBoxId = source.readInt();
    }

    public static ItemViewResources getDefault() {
        if (sDefault == null) {
            sDefault = new ItemViewResources(R.layout.grid_item_default_photo, R.id.default_grid_image, R.id.default_check_box);
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
        dest.writeInt(mCheckBoxId);
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public int getImageViewId() {
        return mImageViewId;
    }

    public int getCheckBoxId() {
        return mCheckBoxId;
    }
}
