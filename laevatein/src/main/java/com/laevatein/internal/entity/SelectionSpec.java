package com.laevatein.internal.entity;

import com.laevatein.MimeType;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

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
    private Set<MimeType> mMimeTypeSet;

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
        List<MimeType> list = new ArrayList<MimeType>();
        source.readList(list, MimeType.class.getClassLoader());
        mMimeTypeSet = EnumSet.copyOf(list);
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
        dest.writeList(new ArrayList<MimeType>(mMimeTypeSet));
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

    public void setMimeTypeSet(Set<MimeType> set) {
        mMimeTypeSet = set;
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

    public Set<MimeType> getMimeTypeSet() {
        return mMimeTypeSet;
    }
}
