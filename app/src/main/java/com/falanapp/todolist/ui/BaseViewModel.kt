package com.falanapp.todolist.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    fun runDisposable(disposable: () -> Disposable) {
        Log.d("disposable in base", "disposable in base")
        compositeDisposable.add(disposable.invoke())
    }

    override fun onCleared() {
        Log.d("oncleared in base", "oncleared in base")
        super.onCleared()
        compositeDisposable.clear()
    }

}