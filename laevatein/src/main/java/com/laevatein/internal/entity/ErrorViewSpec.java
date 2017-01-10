package com.laevatein.internal.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.laevatein.R;

/**
 * @author Keithyokoma
 * @since 2014/04/07
 * @version 1.0.0
 * @hide
 */
public class ErrorViewSpec implements Parcelable {
    public static final Creator<ErrorViewSpec> CREATOR = new Creator<ErrorViewSpec>() {
        @Override
        @Nullable
        public ErrorViewSpec createFromParcel(Parcel source) {
            return new ErrorViewSpec(source);
        }

        @Override
        public ErrorViewSpec[] newArray(int size) {
            return new ErrorViewSpec[size];
        }
    };
    private final ErrorViewResources mCountErrorSpec;
    private final ErrorViewResources mUnderQualitySpec;
    private final ErrorViewResources mOverQualitySpec;
    private final ErrorViewResources mTypeErrorSpec;

    /* package */ ErrorViewSpec(Parcel source) {
        mCountErrorSpec = source.readParcelable(ErrorViewResources.class.getClassLoader());
        mUnderQualitySpec = source.readParcelable(ErrorViewResources.class.getClassLoader());
        mOverQualitySpec = source.readParcelable(ErrorViewResources.class.getClassLoader());
        mTypeErrorSpec = source.readParcelable(ErrorViewResources.class.getClassLoader());
    }

    /* package */ ErrorViewSpec(ErrorViewResources countSpec, ErrorViewResources underQualitySpec, ErrorViewResources overQualitySpec, ErrorViewResources typeErrorSpec) {
        mCountErrorSpec = countSpec;
        mUnderQualitySpec = underQualitySpec;
        mOverQualitySpec = overQualitySpec;
        mTypeErrorSpec = typeErrorSpec;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mCountErrorSpec, flags);
        dest.writeParcelable(mUnderQualitySpec, flags);
        dest.writeParcelable(mOverQualitySpec, flags);
        dest.writeParcelable(mTypeErrorSpec, flags);
    }

    public ErrorViewResources getCountErrorSpec() {
        return mCountErrorSpec;
    }

    public ErrorViewResources getUnderQualitySpec() {
        return mUnderQualitySpec;
    }

    public ErrorViewResources getOverQualitySpec() {
        return mOverQualitySpec;
    }

    public ErrorViewResources getTypeErrorSpec() {
        return mTypeErrorSpec;
    }

    public static class Builder {
        private ErrorViewResources mCountSpec;
        private ErrorViewResources mUnderQualitySpec;
        private ErrorViewResources mOverQualitySpec;
        private ErrorViewResources mTypeSpec;

        public Builder setCountSpec(ErrorViewResources spec) {
            mCountSpec = spec;
            return this;
        }

        public Builder setUnderQualitySpec(ErrorViewResources spec) {
            mUnderQualitySpec = spec;
            return this;
        }

        public Builder setOverQualitySpec(ErrorViewResources spec) {
            mOverQualitySpec = spec;
            return this;
        }

        public Builder setTypeSpec(ErrorViewResources spec) {
            mTypeSpec = spec;
            return this;
        }

        public ErrorViewSpec create() {
            if (mCountSpec == null) {
                mCountSpec = ErrorViewResources.ViewType.NONE.createSpec(0, 0);
            }
            if (mUnderQualitySpec == null) {
                mUnderQualitySpec = ErrorViewResources.ViewType.DIALOG.createSpec(-1, R.string.l_error_quality);
            }
            if (mOverQualitySpec == null) {
                mOverQualitySpec = ErrorViewResources.ViewType.DIALOG.createSpec(-1, R.string.l_error_quality);
            }
            if (mTypeSpec == null) {
                mTypeSpec = ErrorViewResources.ViewType.DIALOG.createSpec(-1, R.string.l_error_invalid_format);
            }
            return new ErrorViewSpec(mCountSpec, mUnderQualitySpec, mOverQualitySpec, mTypeSpec);
        }
    }
}
