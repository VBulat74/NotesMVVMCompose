package ru.com.vbulat.notesmvvmcompose

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.com.vbulat.notesmvvmcompose.database.firebase.repository.FirebaseRepositoryImpl
import ru.com.vbulat.notesmvvmcompose.database.room.AppRoomDatabase
import ru.com.vbulat.notesmvvmcompose.database.room.repository.RoomRepositoryImpl
import ru.com.vbulat.notesmvvmcompose.model.Note
import ru.com.vbulat.notesmvvmcompose.utils.DB_TYPE
import ru.com.vbulat.notesmvvmcompose.utils.REPOSITORY
import ru.com.vbulat.notesmvvmcompose.utils.TYPE_FIREBASE
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
            TYPE_FIREBASE -> {
                REPOSITORY = FirebaseRepositoryImpl()
                REPOSITORY.connectToDatabase(
                    {onSuccess()},
                    {error_message ->
                        //error_message -> Toast.makeText(context, "Error: $error_message", Toast.LENGTH_SHORT).show()
                        Log.d("AAA", "Error: $error_message")
                    }
                )
            }
        }
    }

    fun addNote (note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note){
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun updateNote (note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.update(note){
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun deleteNote (note:Note, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(note){
                viewModelScope.launch(Dispatchers.Main) { onSuccess() }
            }
        }
    }

    fun sign_out(onSuccess: () -> Unit){
        when(DB_TYPE.value) {
            TYPE_FIREBASE,
            TYPE_ROOM -> {
                REPOSITORY.signOut()
                DB_TYPE.value = ""
                onSuccess()
            }
            else -> {Log.d ("AAA", "sign_out ELSE: ${DB_TYPE.value}") }
        }
    }

    fun readAllNotes() = REPOSITORY.readAll
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