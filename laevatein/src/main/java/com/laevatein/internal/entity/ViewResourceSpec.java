package com.laevatein.internal.entity;

import android.content.pm.ActivityInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

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
    @StyleRes
    private int mTheme;
    private final ItemViewResources mItemViewResources;
    private final boolean mEnableCapture;
    private final boolean mEnableSelectedView;
    private final int mActivityOrientation;

    /* package */ ViewResourceSpec(Parcel source) {
        mTheme = source.readInt();
        mItemViewResources = source.readParcelable(ItemViewResources.class.getClassLoader());
        mEnableCapture = ParcelUtils.readBoolean(source);
        mEnableSelectedView = ParcelUtils.readBoolean(source);
        mActivityOrientation = source.readInt();
    }

    /* package */ ViewResourceSpec(
            @StyleRes int theme,
            ItemViewResources itemViewResources,
            boolean enableCapture,
            boolean enableSelectedView,
            int activityOrientation) {
        mTheme = theme;
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
        dest.writeInt(mTheme);
        dest.writeParcelable(mItemViewResources, flags);
        ParcelUtils.writeBoolean(dest, mEnableCapture);
        ParcelUtils.writeBoolean(dest, mEnableSelectedView);
        dest.writeInt(mActivityOrientation);
    }

    public static class Builder {
        @StyleRes
        private int mTheme;
        private ItemViewResources mItemViewResources;
        private boolean mEnableCapture;
        private boolean mEnableSelectedView;
        private int mActivityOrientation;

        public Builder setTheme(@StyleRes int theme) {
            mTheme = theme;
            return this;
        }

        public Builder setItemViewResources(ItemViewResources itemViewResources) {
            mItemViewResources = itemViewResources;
            return this;
        }

        public Builder setEnableCapture(boolean enableCapture) {
            mEnableCapture = enableCapture;
            return this;
        }

        public Builder setEnableSelectedView(boolean enableSelectedView) {
            mEnableSelectedView = enableSelectedView;
            return this;
        }

        public Builder setActivityOrientation(int activityOrientation) {
            mActivityOrientation = activityOrientation;
            return this;
        }

        public ViewResourceSpec create() {
            if (mItemViewResources == null) {
                mItemViewResources = ItemViewResources.getDefault();
            }
            return new ViewResourceSpec(mTheme,
                    mItemViewResources, mEnableCapture, mEnableSelectedView, mActivityOrientation);
        }
    }

    public int getTheme() {
        return mTheme;
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
