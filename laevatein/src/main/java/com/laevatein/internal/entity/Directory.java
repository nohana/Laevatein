package com.laevatein.internal.entity;

import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author KeithYokoma
 * @since 2014/03/20
 * @version 1.0.0
 * @hide
 */
public class Directory implements Parcelable {
    public static final Creator<Directory> CREATOR = new Creator<Directory>() {
        @Nullable
        @Override
        public Directory createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Directory[] newArray(int size) {
            return new Directory[0];
        }
    };
    private final String mDirectoryName;
    private final File mPath;

    /* package */ Directory(String directoryName, File path) {
        mDirectoryName = directoryName;
        mPath = path;
    }

    /* package */ Directory(Parcel source) {
        mDirectoryName = source.readString();
        mPath = (File) source.readSerializable();
    }

    public static Directory fromFile(@Nonnull File dir) {
        return new Directory(dir.getName(), dir);
    }

    public static List<Directory> getPictureDirs() {
        List<Directory> dirs = new ArrayList<Directory>();
        File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File[] dirArray = picDir.listFiles();
        if (dirArray == null) {
            return Collections.emptyList();
        }

        for (File dir : dirArray) {
            dirs.add(fromFile(dir));
        }
        return dirs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDirectoryName);
        dest.writeSerializable(mPath);
    }

    public String getDirectoryName() {
        return mDirectoryName;
    }

    public File getPath() {
        return mPath;
    }
}
