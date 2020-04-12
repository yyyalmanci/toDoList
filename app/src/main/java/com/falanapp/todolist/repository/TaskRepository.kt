package com.falanapp.todolist.repository

import com.falanapp.todolist.database.TaskDb
import com.falanapp.todolist.database.TaskEntry
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TaskRepository(private val taskDb: TaskDb) {

    fun deleteTask(taskEntry: TaskEntry): Completable {
        return taskDb.taskDao().deleteTask(taskEntry).subscribeOn(Schedulers.io())
    }

    fun loadTasks(): Single<List<TaskEntry>> {
        return taskDb.taskDao().loadAllTasks().subscribeOn(Schedulers.io())
    }

    fun loadTasksWithId(taskId: Int): Single<TaskEntry> {
        return taskDb.taskDao().loadTask(taskId).subscribeOn(Schedulers.io())
    }

    fun addTask(taskEntry: TaskEntry): Completable {
        return taskDb.taskDao().insertTask(taskEntry).subscribeOn(Schedulers.io())
    }

    fun updateTask(taskEntry: TaskEntry): Completable {
        return taskDb.taskDao().updateTask(taskEntry)
            .subscribeOn(Schedulers.io())
    }
}