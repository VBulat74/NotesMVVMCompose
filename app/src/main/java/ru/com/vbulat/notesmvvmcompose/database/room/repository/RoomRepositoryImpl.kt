package ru.com.vbulat.notesmvvmcompose.database.room.repository

import androidx.lifecycle.LiveData
import ru.com.vbulat.notesmvvmcompose.database.DatabaseRepository
import ru.com.vbulat.notesmvvmcompose.database.room.dao.NoteRoomDao
import ru.com.vbulat.notesmvvmcompose.model.Note

class RoomRepositoryImpl (private val noteRoomDao: NoteRoomDao) : DatabaseRepository {
    override val readAll: LiveData<List<Note>>
        get() = noteRoomDao.getAllNotes()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.addNote(note = note)
        onSuccess()
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.deleteNote(note = note)
        onSuccess()
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.updateNote(note = note)
        onSuccess()
    }
}