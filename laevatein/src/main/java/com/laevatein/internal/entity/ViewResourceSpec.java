package com.laevatein.internal.entity;

import android.content.pm.ActivityInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import com.amalgam.os.ParcelUtils;
import com.laevatein.ui.ImagePreviewActivity;

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
    private final Class<? extends ImagePreviewActivity> mPreviewActivityClass;
    private final ItemViewResources mItemViewResources;
    private final CounterViewResources mCounterViewResources;
    private final PreviewViewResources mPreviewViewResources;
    private final boolean mOpenDrawer;
    private final boolean mEnableCapture;
    private final boolean mEnableSelectedView;
    private final int mActivityOrientation;

    /* package */ ViewResourceSpec(Parcel source) {
        mTheme = source.readInt();
        mPreviewActivityClass = (Class<? extends ImagePreviewActivity>) source.readSerializable();
        mItemViewResources = source.readParcelable(ItemViewResources.class.getClassLoader());
        mCounterViewResources = source.readParcelable(CounterViewResources.class.getClassLoader());
        mPreviewViewResources = source.readParcelable(PreviewViewResources.class.getClassLoader());
        mOpenDrawer = ParcelUtils.readBoolean(source);
        mEnableCapture = ParcelUtils.readBoolean(source);
        mEnableSelectedView = ParcelUtils.readBoolean(source);
        mActivityOrientation = source.readInt();
    }

    /* package */ ViewResourceSpec(
            @StyleRes int theme,
            Class<? extends ImagePreviewActivity> previewActivityClass,
            ItemViewResources itemViewResources,
            CounterViewResources counterViewResources,
            PreviewViewResources previewViewResources,
            boolean openDrawer,
            boolean enableCapture,
            boolean enableSelectedView,
            int activityOrientation) {
        mTheme = theme;
        mPreviewActivityClass = previewActivityClass;
        mItemViewResources = itemViewResources;
        mCounterViewResources = counterViewResources;
        mPreviewViewResources = previewViewResources;
        mOpenDrawer = openDrawer;
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
        dest.writeSerializable(mPreviewActivityClass);
        dest.writeParcelable(mItemViewResources, flags);
        dest.writeParcelable(mCounterViewResources, flags);
        dest.writeParcelable(mPreviewViewResources, flags);
        ParcelUtils.writeBoolean(dest, mOpenDrawer);
        ParcelUtils.writeBoolean(dest, mEnableCapture);
        ParcelUtils.writeBoolean(dest, mEnableSelectedView);
        dest.writeInt(mActivityOrientation);
    }

    public static class Builder {
        @StyleRes
        private int mTheme;
        private Class<? extends ImagePreviewActivity> mPreviewActivityClass;
        private ItemViewResources mItemViewResources;
        private CounterViewResources mCounterViewResources;
        private PreviewViewResources mPreviewViewResources;
        private boolean mOpenDrawer;
        private boolean mEnableCapture;
        private boolean mEnableSelectedView;
        private int mActivityOrientation;

        public Builder setTheme(@StyleRes int theme) {
            mTheme = theme;
            return this;
        }

        public Builder setPreviewClass(Class<? extends ImagePreviewActivity> previewActivityClass) {
            mPreviewActivityClass = previewActivityClass;
            return this;
        }

        public Builder setItemViewResources(ItemViewResources itemViewResources) {
            mItemViewResources = itemViewResources;
            return this;
        }

        public Builder setCounterViewResources(CounterViewResources counterViewResources) {
            mCounterViewResources = counterViewResources;
            return this;
        }

        public Builder setPreviewViewResources(PreviewViewResources previewViewResources) {
            mPreviewViewResources = previewViewResources;
            return this;
        }

        public Builder setOpenDrawer(boolean openDrawer) {
            mOpenDrawer = openDrawer;
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
            if (mCounterViewResources == null) {
                mCounterViewResources = CounterViewResources.getDefault();
            }
            if (mPreviewViewResources == null) {
                mPreviewViewResources = PreviewViewResources.getDefault();
            }
            return new ViewResourceSpec(mTheme, mPreviewActivityClass,
                    mItemViewResources, mCounterViewResources, mPreviewViewResources, mOpenDrawer,
                    mEnableCapture, mEnableSelectedView, mActivityOrientation);
        }
    }

    public int getTheme() {
        return mTheme;
    }

    public Class<? extends ImagePreviewActivity> getPreviewActivityClass() {
        return mPreviewActivityClass;
    }

    public ItemViewResources getItemViewResources() {
        return mItemViewResources;
    }

    public CounterViewResources getCounterViewResources() {
        return mCounterViewResources;
    }

    public PreviewViewResources getPreviewViewResources() {
        return mPreviewViewResources;
    }

    public boolean openDrawer() {
        return mOpenDrawer;
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
