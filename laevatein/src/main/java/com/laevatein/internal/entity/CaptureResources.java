package com.laevatein.internal.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class CaptureResources implements Parcelable {

    public static final Creator<CaptureResources> CREATOR = new Creator<CaptureResources>() {
        @Override
        public CaptureResources createFromParcel(Parcel in) {
            return new CaptureResources(in);
        }

        @Override
        public CaptureResources[] newArray(int size) {
            return new CaptureResources[size];
        }
    };

    @Nullable
    private final String mFileProviderAuthorities;

    public CaptureResources(@Nullable String fileProviderAuthorities) {
        mFileProviderAuthorities = fileProviderAuthorities;
    }

    private CaptureResources(Parcel in) {
        mFileProviderAuthorities = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFileProviderAuthorities);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean enabled() {
        return mFileProviderAuthorities != null;
    }

    @Nullable
    public String getFileProviderAuthorities() {
        return mFileProviderAuthorities;
    }
}
