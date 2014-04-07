package com.laevatein.internal.entity;

import com.laevatein.R;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Nullable;

/**
 * @author KeithYokoma
 * @since 2014/04/07
 * @version 1.0.0
 * @hide
 */
public class CountViewResources implements Parcelable {
    public static final Creator<CountViewResources> CREATOR = new Creator<CountViewResources>() {
        @Override
        @Nullable
        public CountViewResources createFromParcel(Parcel source) {
            return new CountViewResources(source);
        }

        @Override
        public CountViewResources[] newArray(int size) {
            return new CountViewResources[size];
        }
    };
    private static volatile CountViewResources sDefault;
    private final int mTextColorResource;
    private final int mBackgroundColorResource;

    /* package */ CountViewResources(Parcel source) {
        mTextColorResource = source.readInt();
        mBackgroundColorResource = source.readInt();
    }

    public CountViewResources(int textColorResource, int backgroundColorResource) {
        mTextColorResource = textColorResource;
        mBackgroundColorResource = backgroundColorResource;
    }

    public static CountViewResources getDefault() {
        if (sDefault == null) {
            sDefault = new CountViewResources(R.color.l_text_color_count, R.color.l_background_count);
        }
        return sDefault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTextColorResource);
        dest.writeInt(mBackgroundColorResource);
    }

    public int getTextColorResource() {
        return mTextColorResource;
    }

    public int getBackgroundColorResource() {
        return mBackgroundColorResource;
    }
}
