package com.example.ly309313.demo_database.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.ly309313.demo_database.tasks.domain.model.Task;

/**
 * 作者 LY309313
 * 日期 2018/5/12
 * 描述
 */
@Database(entities = {Task.class},version = 1)
public abstract class ToDoDatabase extends RoomDatabase {

    private static ToDoDatabase INSTANCE;

    public abstract TasksDao tasksDao();

    public static Object sLock = new Object();
    public static ToDoDatabase getInstance(Context context){
        synchronized (sLock){
            if(INSTANCE ==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),ToDoDatabase.class,"tasks.db").build();
            }
            return INSTANCE;
        }

    }
}
