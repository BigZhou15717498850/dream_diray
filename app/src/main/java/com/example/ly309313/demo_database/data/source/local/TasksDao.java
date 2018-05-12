package com.example.ly309313.demo_database.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ly309313.demo_database.tasks.domain.model.Task;

import java.util.List;

/**
 * Created by LY309313 on 2018/5/11.
 */
@Dao
public interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addTask(Task task);

    @Query("DELETE FROM tasks")
    long deleteAllTasks();

    @Query("DELETE FROM TASKS WHERE entryid=:entryid")
    void deleteTaskById(String entryid);

    @Query("DELETE FROM TASKS WHERE complete=1")
    void deleteCompletedTask();

    @Update
    void updateTask(Task task);

    @Query("UPDATE TASKS SET complete=:completed WHERE entryid=:entryid")
    void updateTaskCompleted(String taskid,boolean completed);

    @Query("SELECT * FROM TASKS")
    List<Task> getTasks();

    @Query("SELECT * FROM TASKS WHERE entryid=:entryid")
    Task getTaskById(String entryid);


}
