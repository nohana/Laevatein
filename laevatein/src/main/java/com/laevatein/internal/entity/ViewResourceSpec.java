package com.laevatein.internal.entity;

import android.content.pm.ActivityInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.amalgam.os.ParcelUtils;

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
    public static final int DEFAULT_SCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED; // no restriction
    private final ActionViewResources mActionViewResources;
    private final ToolbarBackgroundResources mToolbarBackgroundResources;
    private final AlbumViewResources mAlbumViewResources;
    private final CountViewResources mCountViewResources;
    private final ItemViewResources mItemViewResources;
    private final boolean mEnableCapture;
    private final boolean mEnableSelectedView;
    private final int mActivityOrientation;

    /* package */ ViewResourceSpec(Parcel source) {
        mActionViewResources = source.readParcelable(ActionViewResources.class.getClassLoader());
        mToolbarBackgroundResources = source.readParcelable(ToolbarBackgroundResources.class.getClassLoader());
        mAlbumViewResources = source.readParcelable(AlbumViewResources.class.getClassLoader());
        mCountViewResources = source.readParcelable(CountViewResources.class.getClassLoader());
        mItemViewResources = source.readParcelable(ItemViewResources.class.getClassLoader());
        mEnableCapture = ParcelUtils.readBoolean(source);
        mEnableSelectedView = ParcelUtils.readBoolean(source);
        mActivityOrientation = source.readInt();
    }

    public ViewResourceSpec(
            ActionViewResources actionViewResources,
            ToolbarBackgroundResources toolbarBackgroundResources,
            AlbumViewResources albumViewResources,
            CountViewResources countViewResources,
            ItemViewResources itemViewResources,
            boolean enableCapture,
            boolean enableSelectedView,
            int activityOrientation) {
        mActionViewResources = actionViewResources;
        mToolbarBackgroundResources = toolbarBackgroundResources;
        mAlbumViewResources = albumViewResources;
        mCountViewResources = countViewResources;
        mItemViewResources = itemViewResources;
        mEnableCapture = enableCapture;
        mEnableSelectedView = enableSelectedView;
        mActivityOrientation = activityOrientation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mActionViewResources, flags);
        dest.writeParcelable(mToolbarBackgroundResources, flags);
        dest.writeParcelable(mAlbumViewResources, flags);
        dest.writeParcelable(mCountViewResources, flags);
        dest.writeParcelable(mItemViewResources, flags);
        ParcelUtils.writeBoolean(dest, mEnableCapture);
        ParcelUtils.writeBoolean(dest, mEnableSelectedView);
        dest.writeInt(mActivityOrientation);
    }

    public ActionViewResources getActionViewResources() {
        return mActionViewResources;
    }

    public ToolbarBackgroundResources getToolbarBackgroundResources() {
        return mToolbarBackgroundResources;
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

    public boolean needActivityOrientationRestriction() {
        return mActivityOrientation != DEFAULT_SCREEN_ORIENTATION;
    }

    public int getActivityOrientation() {
        return mActivityOrientation;
    }
}
