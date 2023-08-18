package ru.com.vbulat.notesmvvmcompose

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.com.vbulat.notesmvvmcompose.database.room.AppRoomDatabase
import ru.com.vbulat.notesmvvmcompose.database.room.repository.RoomRepositoryImpl
import ru.com.vbulat.notesmvvmcompose.utils.REPOSITORY
import ru.com.vbulat.notesmvvmcompose.utils.TYPE_ROOM

class MainViewModel (application: Application) : AndroidViewModel(application) {

    private val context = application

    fun initDatabase(type: String, onSuccess : () -> Unit) {
        Log.d ("AAA", "MainViewModel initDatabase($type)")
        when (type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(context).getRoomDao()
                REPOSITORY = RoomRepositoryImpl(dao)
                onSuccess()
            }
        }
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