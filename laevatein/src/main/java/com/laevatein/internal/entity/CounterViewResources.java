package com.laevatein.internal.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;

import com.laevatein.R;

/**
 * @author keishin.yokomaku
 * @since 2014/04/07
 */
public class CounterViewResources implements Parcelable {
    public static final Creator<CounterViewResources> CREATOR = new Creator<CounterViewResources>() {
        @Override
        public CounterViewResources createFromParcel(Parcel source) {
            return new CounterViewResources(source);
        }

        @Override
        public CounterViewResources[] newArray(int size) {
            return new CounterViewResources[size];
        }
    };


    @IntDef({LAYOUT_APP_BAR, LAYOUT_BOTTOM})
    public @interface VIEW_POSITION {
    }

    public static final int LAYOUT_APP_BAR = 1;
    public static final int LAYOUT_BOTTOM = 2;

    private static volatile CounterViewResources sDefault;
    @VIEW_POSITION
    private final int mViewPosition;
    @StringRes
    private final int mTextId;

    /* package */ CounterViewResources(Parcel source) {
        mViewPosition = readViewPosition(source);
        mTextId = source.readInt();
    }

    public CounterViewResources(@VIEW_POSITION int viewPosition, @StringRes int textId) {
        mViewPosition = viewPosition;
        mTextId = textId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mViewPosition);
        dest.writeInt(mTextId);
    }

    @VIEW_POSITION
    public int getViewType() {
        return mViewPosition;
    }

    @StringRes
    public int getTextRes(){
        return mTextId;
    }

    public static CounterViewResources getDefault() {
        if (sDefault == null) {
            sDefault = new CounterViewResources(LAYOUT_BOTTOM, R.string.l_format_selection_count);
        }
        return sDefault;
    }

    @VIEW_POSITION
    private int readViewPosition(Parcel source) {
        int result = source.readInt();
        switch (result) {
            case LAYOUT_APP_BAR:
                return LAYOUT_APP_BAR;
            case LAYOUT_BOTTOM:
                return LAYOUT_BOTTOM;
            default:
                throw new IllegalArgumentException();
        }
    }
}