package com.laevatein.internal.entity;

import com.laevatein.R;

/**
 * @author keishin.yokomaku
 * @since 2014/03/25
 */
public enum UncapableCause {
    QUALITY(R.string.error_quality),
    FILE_TYPE(R.string.error_invalid_format);

    private final int mErrorMessageRes;

    private UncapableCause(int errorMessageRes) {
        mErrorMessageRes = errorMessageRes;
    }

    public int getErrorMessageRes() {
        return mErrorMessageRes;
    }
}
