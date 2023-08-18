package ru.com.vbulat.notesmvvmcompose

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.com.vbulat.notesmvvmcompose.model.Note
import ru.com.vbulat.notesmvvmcompose.utils.TYPE_FIREBASE
import ru.com.vbulat.notesmvvmcompose.utils.TYPE_ROOM

class MainViewModel (application: Application) : AndroidViewModel(application) {

    val readTest : MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>> ()
    }

    val dbType : MutableLiveData<String> by lazy {
        MutableLiveData<String> (TYPE_ROOM)
    }

    init {
        readTest.value =
            when (dbType.value) {
                TYPE_ROOM -> {
                    listOf<Note>(
                        Note(title = "Note1", subtitle = "Subtitle for note 1"),
                        Note(title = "Note2", subtitle = "Subtitle for note 2"),
                        Note(title = "Note3", subtitle = "Subtitle for note 3"),
                        Note(title = "Note4", subtitle = "Subtitle for note 4"),
                    )
                }
                TYPE_FIREBASE -> {
                    listOf<Note>(
                        Note(title = "Note1", subtitle = "Subtitle for note 1"),
                        Note(title = "Note2", subtitle = "Subtitle for note 2"),
                        Note(title = "Note3", subtitle = "Subtitle for note 3"),
                        Note(title = "Note4", subtitle = "Subtitle for note 4"),
                    )
                }
                else -> {
                    listOf<Note>()
                }
            }
    }

    fun initDatabase(type: String) {
        dbType.value = type
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