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
 * @author KeithYokoma
 * @version 1.0.0
 * @hide
 * @since 2014/03/20
 */
public final class ItemViewResources implements Parcelable {
    private static final int DEFAULT_SPAN_COUNT = 4;
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
    private final int mSpanCount;

    public ItemViewResources(int layoutId, int imageViewId, int checkBoxId, int spanCount) {
        mLayoutId = layoutId;
        mImageViewId = imageViewId;
        mCheckBoxId = checkBoxId;
        mSpanCount = spanCount;
    }

    /* package */ ItemViewResources(Parcel source) {
        mLayoutId = source.readInt();
        mImageViewId = source.readInt();
        mCheckBoxId = source.readInt();
        mSpanCount = source.readInt();
    }

    public static ItemViewResources getDefault() {
        if (sDefault == null) {
            sDefault = new ItemViewResources(R.layout.l_grid_item_default_photo, R.id.l_default_grid_image, R.id.l_default_check_box, DEFAULT_SPAN_COUNT);
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
        dest.writeInt(mSpanCount);
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

    public int getSpanCount() {
        return mSpanCount;
    }
}
