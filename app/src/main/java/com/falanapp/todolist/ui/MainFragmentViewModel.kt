package com.falanapp.todolist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.falanapp.todolist.database.TaskDb
import com.falanapp.todolist.database.TaskEntry
import io.reactivex.schedulers.Schedulers

class MainFragmentViewModel(private val taskDb: TaskDb) :
    BaseViewModel() {

    private val _taskEntry = MutableLiveData<List<TaskEntry>>()
    val taskEntry: LiveData<List<TaskEntry>>
        get() = _taskEntry

    fun deleteTask(taskEntry: TaskEntry) {
        runDisposable {
            taskDb.taskDao().deleteTask(taskEntry)
                .subscribeOn(Schedulers.io()).doOnComplete {
                    taskDb.taskDao().loadAllTasks().subscribe({
                        _taskEntry.postValue(it)
                    }, {})
                }.subscribe({}, {})
        }
    }

    fun loadTasks() {
        runDisposable {
            taskDb.taskDao().loadAllTasks().subscribeOn(Schedulers.io()).subscribe({
                _taskEntry.postValue(it)
            }, {})
        }
    }

    fun loadTasksWithId(taskId: Int) {
        runDisposable {
            taskDb.taskDao().loadTask(taskId).subscribeOn(Schedulers.io()).subscribe({
                _taskEntry.postValue(listOf(it))
            }, {})
        }
    }

    fun addTask(taskEntry: TaskEntry) {
        runDisposable {
            taskDb.taskDao().insertTask(taskEntry).subscribeOn(Schedulers.io())
                .subscribe({
                }, {})
        }
    }

    fun updateTask(taskEntry: TaskEntry) {
        runDisposable {
            taskDb.taskDao().updateTask(taskEntry)
                .subscribeOn(Schedulers.io())
                .subscribe({}, {})
        }
    }
}