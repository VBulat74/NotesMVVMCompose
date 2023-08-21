package ru.com.vbulat.notesmvvmcompose.database.firebase.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ru.com.vbulat.notesmvvmcompose.database.DatabaseRepository
import ru.com.vbulat.notesmvvmcompose.model.Note
import ru.com.vbulat.notesmvvmcompose.utils.Constants
import ru.com.vbulat.notesmvvmcompose.utils.FIREBASE_ID
import ru.com.vbulat.notesmvvmcompose.utils.LOGIN
import ru.com.vbulat.notesmvvmcompose.utils.PASSWORD

class FirebaseRepositoryImpl : DatabaseRepository {

    private val mAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference
        .child(mAuth.currentUser?.uid.toString())

    override val readAll: LiveData<List<Note>> = AllNotesLiveData()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        val noteId = database.push().key.toString()
        val mapNotes = hashMapOf<String, Any>()

        mapNotes[FIREBASE_ID] = noteId
        mapNotes[Constants.Keys.TITLE_TEXT] = note.title
        mapNotes[Constants.Keys.SUBTITLE_TEXT] = note.subtitle

        database.child(noteId).updateChildren(mapNotes)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("AAA", "Error: ${it.message.toString()}") }
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        database.child(note.firebase_id).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("AAA", "Error: ${it.message.toString()}") }
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        val noteId = note.firebase_id
        val mapNotes = hashMapOf<String, Any>()

        mapNotes[FIREBASE_ID] = noteId
        mapNotes[Constants.Keys.TITLE_TEXT] = note.title
        mapNotes[Constants.Keys.SUBTITLE_TEXT] = note.subtitle

        database.child(noteId)
            .updateChildren(mapNotes)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { Log.d("AAA", "Error: ${it.message.toString()}") }
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener {
                        onFail(it.message.toString())
                    }
            }
    }
}