package com.laevatein.internal.entity;

import com.laevatein.ErrorViewType;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Nullable;

/**
 * @author keishin.yokomaku
 * @since 2014/03/24
 */
public class SelectionErrorResource implements Parcelable {
    public static final Creator<SelectionErrorResource> CREATOR = new Creator<SelectionErrorResource>() {
        @Override
        @Nullable
        public SelectionErrorResource createFromParcel(Parcel source) {
            return new SelectionErrorResource(source);
        }

        @Override
        public SelectionErrorResource[] newArray(int size) {
            return new SelectionErrorResource[size];
        }
    };
    private ErrorViewType mErrorViewType;
    private int mErrorMessage;

    public SelectionErrorResource() {
        mErrorViewType = ErrorViewType.TOAST;
        mErrorMessage = -1;
    }

    /* package */ SelectionErrorResource(Parcel source) {
        mErrorViewType = (ErrorViewType) source.readSerializable();
        mErrorMessage = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mErrorViewType);
        dest.writeInt(mErrorMessage);
    }

    public void setErrorViewType(ErrorViewType errorViewType) {
        mErrorViewType = errorViewType;
    }

    public void setErrorMessage(int errorMessage) {
        mErrorMessage = errorMessage;
    }

    public ErrorViewType getErrorViewType() {
        return mErrorViewType;
    }

    public int getErrorMessage() {
        return mErrorMessage;
    }
}
