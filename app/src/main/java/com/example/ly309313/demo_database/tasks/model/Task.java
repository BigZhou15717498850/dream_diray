package com.example.ly309313.demo_database.tasks.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * Created by LY309313 on 2018/5/11.
 */
@Entity(tableName = "tasks")
public class Task {

    @ColumnInfo(name = "taskid")
    private String taskId;

    @ColumnInfo(name = "desc")
    private String description;

    @ColumnInfo(name = "active")
    private boolean isActive;

    @ColumnInfo(name = "complele")
    private boolean isCompele;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isCompele() {
        return isCompele;
    }

    public void setCompele(boolean compele) {
        isCompele = compele;
    }
}
