package com.falanapp.todolist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.falanapp.todolist.database.TaskEntry
import com.falanapp.todolist.repository.TaskRepository

class MainFragmentViewModel(private val taskRepository: TaskRepository) :
    BaseViewModel() {

    private val _taskEntry = MutableLiveData<List<TaskEntry>>()
    val taskEntry: LiveData<List<TaskEntry>>
        get() = _taskEntry

    fun deleteTask(taskEntry: TaskEntry) {
        runDisposable {
            taskRepository.deleteTask(taskEntry).doOnComplete {
                loadTasks()
            }.subscribe({}, {})
        }
    }

    fun loadTasks() {
        runDisposable {
            taskRepository.loadTasks().subscribe({
                _taskEntry.postValue(it)
            }, {})
        }
    }

    fun loadTasksWithId(taskId: Int) {
        runDisposable {
            taskRepository.loadTasksWithId(taskId).subscribe({
                _taskEntry.postValue(listOf(it))
            }, {})
        }
    }

    fun addTask(taskEntry: TaskEntry) {
        runDisposable {
            taskRepository.addTask(taskEntry).subscribe()
        }
    }

    fun updateTask(taskEntry: TaskEntry) {
        runDisposable {
            taskRepository.updateTask(taskEntry)
                .subscribe({}, {})
        }
    }
}
