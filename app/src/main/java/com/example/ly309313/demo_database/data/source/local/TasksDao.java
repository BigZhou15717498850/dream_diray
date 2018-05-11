package com.example.ly309313.demo_database.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.example.ly309313.demo_database.tasks.model.Task;

/**
 * Created by LY309313 on 2018/5/11.
 */
@Dao
public interface TasksDao {

    @Insert
    long addTask(Task task);
}
