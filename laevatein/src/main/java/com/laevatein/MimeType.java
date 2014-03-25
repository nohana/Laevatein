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
package com.laevatein;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author KeithYokoma
 * @since 2014/03/19
 */
@SuppressWarnings("unused") // public APIs
public enum MimeType {
    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif");

    private final String mMimeTypeName;

    private MimeType(String mimeTypeName) {
        mMimeTypeName = mimeTypeName;
    }

    public static Set<MimeType> allOf() {
        return EnumSet.allOf(MimeType.class);
    }

    public static Set<MimeType> of(MimeType type) {
        return EnumSet.of(type);
    }

    @Override
    public String toString() {
        return mMimeTypeName;
    }
}
