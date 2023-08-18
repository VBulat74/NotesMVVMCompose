package ru.com.vbulat.notesmvvmcompose

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModel (application: Application) : AndroidViewModel(application) {
    fun initDatabase(type: String) {
        Log.d ("AAA", "MainViewModel initDatabase($type)")
    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException ("Unknown ViewModel class")
    }
}