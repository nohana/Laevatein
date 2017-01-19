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
    private final ErrorViewResources mCountOverErrorSpec;
    private final ErrorViewResources mUnderQualitySpec;
    private final ErrorViewResources mOverQualitySpec;
    private final ErrorViewResources mTypeErrorSpec;
    private final DialogResources mBackConfirmSpec;

    /* package */ ErrorViewSpec(Parcel source) {
        mCountOverErrorSpec = source.readParcelable(ErrorViewResources.class.getClassLoader());
        mUnderQualitySpec = source.readParcelable(ErrorViewResources.class.getClassLoader());
        mOverQualitySpec = source.readParcelable(ErrorViewResources.class.getClassLoader());
        mTypeErrorSpec = source.readParcelable(ErrorViewResources.class.getClassLoader());
        mBackConfirmSpec = source.readParcelable(DialogResources.class.getClassLoader());
    }

    /* package */ ErrorViewSpec(ErrorViewResources countSpec, ErrorViewResources underQualitySpec,
                                ErrorViewResources overQualitySpec, ErrorViewResources typeErrorSpec,
                                DialogResources backConfirmSpec) {
        mCountOverErrorSpec = countSpec;
        mUnderQualitySpec = underQualitySpec;
        mOverQualitySpec = overQualitySpec;
        mTypeErrorSpec = typeErrorSpec;
        mBackConfirmSpec = backConfirmSpec;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mCountOverErrorSpec, flags);
        dest.writeParcelable(mUnderQualitySpec, flags);
        dest.writeParcelable(mOverQualitySpec, flags);
        dest.writeParcelable(mTypeErrorSpec, flags);
        dest.writeParcelable(mBackConfirmSpec, flags);
    }

    public ErrorViewResources getCountOverErrorSpec() {
        return mCountOverErrorSpec;
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

    public DialogResources getBackConfirmSpec() {
        return mBackConfirmSpec;
    }

    public static class Builder {
        private ErrorViewResources mCountOverSpec;
        private ErrorViewResources mUnderQualitySpec;
        private ErrorViewResources mOverQualitySpec;
        private ErrorViewResources mTypeSpec;
        private DialogResources mBackSpec;

        public Builder setCountOverSpec(ErrorViewResources spec) {
            mCountOverSpec = spec;
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

        public Builder setConfirmSpec(DialogResources spec) {
            mBackSpec = spec;
            return this;
        }

        public ErrorViewSpec create() {
            if (mCountOverSpec == null) {
                mCountOverSpec = ErrorViewResources.ViewType.NONE.createSpec(0, 0);
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
            if (mBackSpec == null) {
                mBackSpec = new DialogResources(R.string.l_confirm_dialog_title, R.string.l_confirm_dialog_message);
            }
            return new ErrorViewSpec(mCountOverSpec, mUnderQualitySpec, mOverQualitySpec, mTypeSpec, mBackSpec);
        }
    }
}
