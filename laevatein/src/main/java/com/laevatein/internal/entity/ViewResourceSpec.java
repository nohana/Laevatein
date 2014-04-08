package com.laevatein.internal.entity;

import com.amalgam.os.ParcelUtils;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Nullable;

/**
 * @author keishin.yokomaku
 * @since 2014/04/07
 */
public class ViewResourceSpec implements Parcelable {
    public static final Creator<ViewResourceSpec> CREATOR = new Creator<ViewResourceSpec>() {
        @Override
        @Nullable
        public ViewResourceSpec createFromParcel(Parcel source) {
            return new ViewResourceSpec(source);
        }

        @Override
        public ViewResourceSpec[] newArray(int size) {
            return new ViewResourceSpec[size];
        }
    };
    private final ActionViewResources mActionViewResources;
    private final AlbumViewResources mAlbumViewResources;
    private final CountViewResources mCountViewResources;
    private final ItemViewResources mItemViewResources;
    private final boolean mEnableCapture;
    private final boolean mEnableSelectedView;

    /* package */ ViewResourceSpec(Parcel source) {
        mActionViewResources = source.readParcelable(ActionViewResources.class.getClassLoader());
        mAlbumViewResources = source.readParcelable(AlbumViewResources.class.getClassLoader());
        mCountViewResources = source.readParcelable(CountViewResources.class.getClassLoader());
        mItemViewResources = source.readParcelable(ItemViewResources.class.getClassLoader());
        mEnableCapture = ParcelUtils.readBoolean(source);
        mEnableSelectedView = ParcelUtils.readBoolean(source);
    }

    public ViewResourceSpec(ActionViewResources actionViewResources, AlbumViewResources albumViewResources, CountViewResources countViewResources, ItemViewResources itemViewResources, boolean enableCapture, boolean enableSelectedView) {
        mActionViewResources = actionViewResources;
        mAlbumViewResources = albumViewResources;
        mCountViewResources = countViewResources;
        mItemViewResources = itemViewResources;
        mEnableCapture = enableCapture;
        mEnableSelectedView = enableSelectedView;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mActionViewResources, flags);
        dest.writeParcelable(mAlbumViewResources, flags);
        dest.writeParcelable(mCountViewResources, flags);
        dest.writeParcelable(mItemViewResources, flags);
        ParcelUtils.writeBoolean(dest, mEnableCapture);
        ParcelUtils.writeBoolean(dest, mEnableSelectedView);
    }

    public ActionViewResources getActionViewResources() {
        return mActionViewResources;
    }

    public AlbumViewResources getAlbumViewResources() {
        return mAlbumViewResources;
    }

    public CountViewResources getCountViewResources() {
        return mCountViewResources;
    }

    public ItemViewResources getItemViewResources() {
        return mItemViewResources;
    }

    public boolean isEnableCapture() {
        return mEnableCapture;
    }

    public boolean isEnableSelectedView() {
        return mEnableSelectedView;
    }
}
