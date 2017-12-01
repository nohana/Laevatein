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
    private final int mTitleId;
    private final int mMessageId;

    /* package */ ErrorViewResources(Parcel source) {
        mViewType = (ViewType) source.readSerializable();
        mTitleId = source.readInt();
        mMessageId = source.readInt();
    }

    /* package */ ErrorViewResources(ViewType viewType, int messageId) {
        this(viewType, -1, messageId);
    }

    /* package */ ErrorViewResources(ViewType viewType, int titleId, int messageId) {
        mViewType = viewType;
        mTitleId = titleId;
        mMessageId = messageId;
    }

    /* package */ static ErrorViewResources asNoView() {
        return new ErrorViewResources(ViewType.NONE, 0);
    }

    /* package */ static ErrorViewResources asAlertDialog(int titleId, int messageId) {
        return new ErrorViewResources(ViewType.DIALOG, titleId, messageId);
    }

    /* package */  static ErrorViewResources asToast(int messageId) {
        return new ErrorViewResources(ViewType.TOAST, messageId);
    }

    /* package */  static ErrorViewResources asSnackbar(int messageId) {
        return new ErrorViewResources(ViewType.SNACKBAR, messageId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mViewType);
        dest.writeInt(mTitleId);
        dest.writeInt(mMessageId);
    }

    public ViewType getViewType() {
        return mViewType;
    }

    public int getTitleId() {
        return mTitleId;
    }

    public int getMessageId() {
        return mMessageId;
    }

    public boolean isNoView() {
        return mViewType == ViewType.NONE;
    }

    public enum ViewType {
        TOAST {
            @Override
            public ErrorViewResources createSpec(int titleId, int messageId) {
                return ErrorViewResources.asToast(messageId);
            }
        },
        SNACKBAR {
            @Override
            public ErrorViewResources createSpec(int titleId, int messageId) {
                return ErrorViewResources.asSnackbar(messageId);
            }
        },
        DIALOG {
            @Override
            public ErrorViewResources createSpec(int titleId, int messageId) {
                return ErrorViewResources.asAlertDialog(titleId, messageId);
            }
        },
        NONE {
            @Override
            public ErrorViewResources createSpec(int titleId, int messageId) {
                return ErrorViewResources.asNoView();
            }
        };

        public abstract ErrorViewResources createSpec(int titleId, int messageId);
    }
}
