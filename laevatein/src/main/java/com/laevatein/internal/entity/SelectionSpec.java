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

    public SelectionSpec() {
        mMaxSelectable = 1;
    }

    /* package */ SelectionSpec(Parcel source) {
        mMaxSelectable = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMaxSelectable);
    }

    public void setMaxSelectable(int maxSelectable) {
        mMaxSelectable = maxSelectable;
    }

    public int getMaxSelectable() {
        return mMaxSelectable;
    }
}
