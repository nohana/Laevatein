package com.laevatein.internal.entity;

import com.laevatein.R;

import android.os.Parcel;
import android.os.Parcelable;

import android.support.annotation.Nullable;

/**
 * @author KeithYokoma
 * @since 2014/04/03
 * @version 1.0.0
 * @hide
 */
public class ActionViewResources implements Parcelable {
    public static final Creator<ActionViewResources> CREATOR = new Creator<ActionViewResources>() {
        @Override
        @Nullable
        public ActionViewResources createFromParcel(Parcel source) {
            return new ActionViewResources(source);
        }

        @Override
        public ActionViewResources[] newArray(int size) {
            return new ActionViewResources[size];
        }
    };
    private static volatile ActionViewResources sDefault;
    private final int mLayoutId;
    private final int mCheckBoxId;

    public ActionViewResources(int layoutId, int checkBoxId) {
        mLayoutId = layoutId;
        mCheckBoxId = checkBoxId;
    }

    /* package */ ActionViewResources(Parcel source) {
        mLayoutId = source.readInt();
        mCheckBoxId = source.readInt();
    }

    public static ActionViewResources getDefault() {
        if (sDefault == null) {
            sDefault = new ActionViewResources(R.layout.l_action_layout_checkbox, R.id.l_default_check_box);
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
        dest.writeInt(mCheckBoxId);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("layoutId:").append(mLayoutId)
                .append(", checkBoxId:").append(mCheckBoxId)
                .toString();
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public int getCheckBoxId() {
        return mCheckBoxId;
    }
}
