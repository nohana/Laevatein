package com.laevatein.internal.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 * @hide
 */
public final class SelectionSpec implements Parcelable {
    public static final Creator<SelectionSpec> CREATOR = new Creator<SelectionSpec>() {
        @Override
        public SelectionSpec createFromParcel(Parcel source) {
            return new SelectionSpec(source);
        }

        @Override
        public SelectionSpec[] newArray(int size) {
            return new SelectionSpec[size];
        }
    };
    private int mMaxSelectable;
    private int mMinSelectable;
    private long mMinPixels;
    private long mMaxPixels;

    public SelectionSpec() {
        mMinSelectable = 0;
        mMaxSelectable = 1;
        mMinPixels = 0L;
        mMaxPixels = Long.MAX_VALUE;
    }

    /* package */ SelectionSpec(Parcel source) {
        mMinSelectable = source.readInt();
        mMaxSelectable = source.readInt();
        mMinPixels = source.readLong();
        mMaxPixels = source.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMinSelectable);
        dest.writeInt(mMaxSelectable);
        dest.writeLong(mMinPixels);
        dest.writeLong(mMaxPixels);
    }

    public void setMaxSelectable(int maxSelectable) {
        mMaxSelectable = maxSelectable;
    }

    public void setMinSelectable(int minSelectable) {
        mMinSelectable = minSelectable;
    }

    public void setMinPixels(long minPixels) {
        mMinPixels = minPixels;
    }

    public void setMaxPixels(long maxPixels) {
        mMaxPixels = maxPixels;
    }

    public int getMinSelectable() {
        return mMinSelectable;
    }

    public int getMaxSelectable() {
        return mMaxSelectable;
    }

    public long getMinPixels() {
        return mMinPixels;
    }

    public long getMaxPixels() {
        return mMaxPixels;
    }
}
