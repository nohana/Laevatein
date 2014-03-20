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
public final class DirectoryViewResources implements Parcelable {
    public static final Creator<DirectoryViewResources> CREATOR = new Creator<DirectoryViewResources>() {
        @Override
        public DirectoryViewResources createFromParcel(Parcel source) {
            return new DirectoryViewResources(source);
        }

        @Override
        public DirectoryViewResources[] newArray(int size) {
            return new DirectoryViewResources[size];
        }
    };
    private static volatile DirectoryViewResources sDefault;
    private final int mLayoutId;
    private final int mImageViewId;
    private final int mLabelViewId;

    public DirectoryViewResources(int layoutId, int imageViewId, int labelViewId) {
        mLayoutId = layoutId;
        mImageViewId = imageViewId;
        mLabelViewId = labelViewId;
    }

    /* package */ DirectoryViewResources(Parcel source) {
        mLayoutId = source.readInt();
        mImageViewId = source.readInt();
        mLabelViewId = source.readInt();
    }

    public static DirectoryViewResources getDefault() {
        if (sDefault == null) {
            sDefault = new DirectoryViewResources(R.layout.list_item_default_directory, R.id.default_list_image, R.id.default_directory_label);
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
