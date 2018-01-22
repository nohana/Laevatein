/*
 * Copyright (C) 2014 nohana, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.laevatein.internal.entity;

/**
 * @author KeithYokoma
 * @since 2014/03/25
 * @version 1.0.0
 * @hide
 */
public enum UncapableCause {
    OVER_COUNT {
        @Override
        public ErrorViewResources getErrorResources(ErrorViewSpec spec) {
            return spec.getCountOverErrorSpec();
        }
    },
    UNDER_QUALITY {
        @Override
        public ErrorViewResources getErrorResources(ErrorViewSpec spec) {
            return spec.getUnderQualitySpec();
        }
    },
    OVER_QUALITY {
        @Override
        public ErrorViewResources getErrorResources(ErrorViewSpec spec) {
            return spec.getOverQualitySpec();
        }
    },
    UNDER_SIZE {
        @Override
        public ErrorViewResources getErrorResources(ErrorViewSpec spec) {
            return spec.getUnderSizeSpec();
        }
    },
    OVER_SIZE {
        @Override
        public ErrorViewResources getErrorResources(ErrorViewSpec spec) {
            return spec.getOverSizeSpec();
        }
    },
    FILE_TYPE {
        @Override
        public ErrorViewResources getErrorResources(ErrorViewSpec spec) {
            return spec.getTypeErrorSpec();
        }
    };

    public abstract ErrorViewResources getErrorResources(ErrorViewSpec spec);
}
