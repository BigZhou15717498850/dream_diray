package com.example.ly309313.demo_database.tasks.domain.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;

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


    public Task(@NonNull String mId, @Nullable String mTitle, @Nullable String mDescription, boolean mCompleted) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCompleted = mCompleted;
    }

    @Ignore
    public Task(@Nullable String mTitle, @Nullable String mDescription) {
        this(UUID.randomUUID().toString(),mTitle,mDescription,false);
    }
    @Ignore
    public Task(@Nullable String mTitle, @Nullable String mDescription, boolean mCompleted) {
        this(UUID.randomUUID().toString(),mTitle,mDescription,mCompleted);
    }

    @Ignore
    public Task(@NonNull String mId, @Nullable String mTitle, @Nullable String mDescription) {
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

    @Override
    public boolean equals(Object obj) {
        if(obj == this)return true;
        if(obj==null || obj.getClass()!=getClass())return false;
        Task task = (Task) obj;
        return Objects.equal(task.mId,this.mId)&&
                Objects.equal(task.mTitle,this.mTitle)&&
                Objects.equal(task.mDescription,this.mDescription)&&
                Objects.equal(task.mCompleted,this.mCompleted);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId,mTitle,mCompleted);
    }

    @Override
    public String toString() {
        return "task with title : " + mTitle;
    }
}
