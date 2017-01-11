package com.laevatein.internal.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author hiroyuki.setp
 * @since 2017/01/11
 */
public class DialogResources implements Parcelable {
    public static final Creator<DialogResources> CREATOR = new Creator<DialogResources>() {
        @Override
        public DialogResources createFromParcel(Parcel source) {
            return new DialogResources(source);
        }

        @Override
        public DialogResources[] newArray(int size) {
            return new DialogResources[size];
        }
    };
    private final int mTitleId;
    private final int mMessageId;

    /* package */ DialogResources(Parcel source) {
        mTitleId = source.readInt();
        mMessageId = source.readInt();
    }

    public DialogResources(int messageId) {
        this(-1, messageId);
    }

    public DialogResources(int titleId, int messageId) {
        mTitleId = titleId;
        mMessageId = messageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTitleId);
        dest.writeInt(mMessageId);
    }

    public int getTitleId() {
        return mTitleId;
    }

    public int getMessageId() {
        return mMessageId;
    }
}
