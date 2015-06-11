package com.laevatein.internal.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * @author HiroyukiSeto
 * @since 2015/06/11
 * @version 1.0.0
 * @hide
 */
public class ToolbarBackgroundResources implements Parcelable {
    public static final Creator<ToolbarBackgroundResources> CREATOR = new Creator<ToolbarBackgroundResources>() {
        @Override
        @Nullable
        public ToolbarBackgroundResources createFromParcel(Parcel source) {
            return new ToolbarBackgroundResources(source);
        }

        @Override
        public ToolbarBackgroundResources[] newArray(int size) {
            return new ToolbarBackgroundResources[size];
        }
    };
    private static volatile ToolbarBackgroundResources sDefault;
    private final int mDrawableId;

    public ToolbarBackgroundResources(int drawableId) {
        mDrawableId = drawableId;
    }

    /* package */ ToolbarBackgroundResources(Parcel source) {
        mDrawableId = source.readInt();
    }

    public static ToolbarBackgroundResources getDefault() {
        if (sDefault == null) {
            sDefault = new ToolbarBackgroundResources(0);
        }
        return sDefault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mDrawableId);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("drawableId:").append(mDrawableId)
                .toString();
    }

    public int getDrawableId() {
        return mDrawableId;
    }
}
