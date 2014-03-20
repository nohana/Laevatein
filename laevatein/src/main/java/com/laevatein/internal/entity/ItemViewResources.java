package com.laevatein.internal.entity;

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
            sDefault = new ItemViewResources(0, 0, 0);
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
