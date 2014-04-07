package com.laevatein.internal.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author keishin.yokomaku
 * @since 2014/04/07
 */
public class ErrorViewResources implements Parcelable {
    public static final Creator<ErrorViewResources> CREATOR = new Creator<ErrorViewResources>() {
        @Override
        public ErrorViewResources createFromParcel(Parcel source) {
            return new ErrorViewResources(source);
        }

        @Override
        public ErrorViewResources[] newArray(int size) {
            return new ErrorViewResources[size];
        }
    };
    private final ViewType mViewType;
    private final int mMessageId;

    /* package */ ErrorViewResources(Parcel source) {
        mViewType = (ViewType) source.readSerializable();
        mMessageId = source.readInt();
    }

    /* package */ ErrorViewResources(ViewType viewType, int messageId) {
        mViewType = viewType;
        mMessageId = messageId;
    }

    /* package */ static ErrorViewResources asNoView() {
        return new ErrorViewResources(ViewType.NONE, 0);
    }

    /* package */  static ErrorViewResources asAlertDialog(int messageId) {
        return new ErrorViewResources(ViewType.DIALOG, messageId);
    }

    /* package */  static ErrorViewResources asToast(int messageId) {
        return new ErrorViewResources(ViewType.TOAST, messageId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mViewType);
        dest.writeInt(mMessageId);
    }

    public ViewType getViewType() {
        return mViewType;
    }

    public int getMessageId() {
        return mMessageId;
    }

    public boolean isNoView() {
        return mViewType == ViewType.NONE;
    }

    public static enum ViewType {
        TOAST {
            @Override
            public ErrorViewResources createSpec(int messageId) {
                return ErrorViewResources.asToast(messageId);
            }
        },
        DIALOG {
            @Override
            public ErrorViewResources createSpec(int messageId) {
                return ErrorViewResources.asAlertDialog(messageId);
            }
        },
        NONE {
            @Override
            public ErrorViewResources createSpec(int messageId) {
                return ErrorViewResources.asNoView();
            }
        };

        public abstract ErrorViewResources createSpec(int messageId);
    }
}
