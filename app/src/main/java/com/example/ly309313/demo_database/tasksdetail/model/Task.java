package com.example.ly309313.demo_database.tasksdetail.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by LY309313 on 2018/5/11.
 */

@Entity(tableName = "Tasks")
public class Task {

    @PrimaryKey
    @ColumnInfo(name = "entryid")
    @NonNull
    private final String mId;

    @ColumnInfo(name = "title")
    @Nullable
    private final String mTitle;

    @ColumnInfo(name = "desc")
    @Nullable
    private final String mDescription;

    @ColumnInfo(name = "complete")
    private final boolean mCompleted;


    public Task(@NonNull String mId,@Nullable String mTitle,@Nullable String mDescription, boolean mCompleted) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCompleted = mCompleted;
    }

    @Ignore
    public Task(@Nullable String mTitle,@Nullable String mDescription) {
        this(UUID.randomUUID().toString(),mTitle,mDescription,false);
    }
    @Ignore
    public Task(@Nullable String mTitle,@Nullable String mDescription, boolean mCompleted) {
        this(UUID.randomUUID().toString(),mTitle,mDescription,mCompleted);
    }

    @Ignore
    public Task(@NonNull String mId,@Nullable String mTitle,@Nullable String mDescription) {
        this(mId,mTitle,mDescription,false);
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public boolean isActive(){
        return !mCompleted;
    }
}
