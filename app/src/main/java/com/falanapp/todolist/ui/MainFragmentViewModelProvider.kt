package com.falanapp.todolist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.falanapp.todolist.database.TaskDb

@Suppress("UNCHECKED_CAST")
class MainFragmentViewModelProvider(private val taskDb: TaskDb) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainFragmentViewModel(taskDb) as T
    }
}