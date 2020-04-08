package com.falanapp.todolist

import android.app.Application
import com.falanapp.todolist.di.databaseModule
import com.falanapp.todolist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class todoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@todoApp.applicationContext)
            modules(
                listOf(
                    databaseModule, viewModelModule
                )
            )
        }
    }


}