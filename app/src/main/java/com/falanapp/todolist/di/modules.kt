package com.falanapp.todolist.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.falanapp.todolist.database.TaskDb
import com.falanapp.todolist.ui.MainFragmentViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainFragmentViewModel(get()) }
}

val databaseModule = module {
    single { getDatabase(get()) }
}

private fun getDatabase(context: Context): TaskDb {

    return databaseBuilder(
        context,
        TaskDb::class.java,
        "task_db"
    )
        .fallbackToDestructiveMigration()
        .build()
}