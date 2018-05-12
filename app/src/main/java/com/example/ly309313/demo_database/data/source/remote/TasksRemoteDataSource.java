package com.example.ly309313.demo_database.data.source.remote;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.example.ly309313.demo_database.data.source.TasksDataSource;
import com.example.ly309313.demo_database.tasks.domain.model.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 作者 LY309313
 * 日期 2018/5/12
 * 描述
 */

public class TasksRemoteDataSource implements TasksDataSource {

    public static TasksRemoteDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MINILION = 5000;

    private static final Map<String,Task> TASK_SERVICE_DATA;

    static {
        TASK_SERVICE_DATA = new LinkedHashMap<>(2);
        addTask("烧一块砖","入门很重要");
        addTask("建一栋房子","一块砖到一栋房子是需要时间的");
    }

    private TasksRemoteDataSource(){}

    public static TasksRemoteDataSource getInstance(){
        if(INSTANCE ==null){
            INSTANCE = new TasksRemoteDataSource();
        }
        return INSTANCE;
    }

    private static void addTask(String title,String description){
        Task newTask = new Task(title,description);
        TASK_SERVICE_DATA.put(newTask.getId(),newTask);
    }

    @Override
    public void getTasks(@NonNull final LoadTasksCallBack callBack) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callBack.onLoadTasks(new ArrayList<Task>(TASK_SERVICE_DATA.values()));
            }
        },SERVICE_LATENCY_IN_MINILION);
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull final GetTaskCallBack callBack) {
        final Task task = TASK_SERVICE_DATA.get(taskId);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callBack.onTaskLoaded(task);
            }
        },SERVICE_LATENCY_IN_MINILION);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        TASK_SERVICE_DATA.put(task.getId(),task);
    }

    @Override
    public void completeTask(Task task) {

    }

    @Override
    public void completeTask(String taskid) {
        Task task = TASK_SERVICE_DATA.get(taskid);
        Task newTask = new Task(task.getId(),task.getTitle(),task.getDescription(),true);
        TASK_SERVICE_DATA.put(taskid,newTask);
    }

    @Override
    public void activeTask(Task task) {

    }

    @Override
    public void activeTask(String taskid) {
        Task task = TASK_SERVICE_DATA.get(taskid);
        Task newTask = new Task(task.getId(),task.getTitle(),task.getDescription(),false);
        TASK_SERVICE_DATA.put(taskid,newTask);
    }

    @Override
    public void deleteTask(String taskid) {
        TASK_SERVICE_DATA.remove(taskid);
    }

    @Override
    public void deleteAllTasks() {
        TASK_SERVICE_DATA.clear();
    }

    @Override
    public void clearCompletedTasks() {
        Iterator<Map.Entry<String, Task>> iterator = TASK_SERVICE_DATA.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Task> next = iterator.next();
            if(next.getValue().isCompleted())iterator.remove();
        }
    }

    @Override
    public void refreshTasks() {

    }
}
