package ru.com.vbulat.notesmvvmcompose.utils

import androidx.compose.runtime.mutableStateOf
import ru.com.vbulat.notesmvvmcompose.database.DatabaseRepository

const val TYPE_ROOM = "type_room"
const val TYPE_FIREBASE = "type_firebase"
const val FIREBASE_ID = "firebase_id"

lateinit var REPOSITORY : DatabaseRepository
lateinit var LOGIN : String
lateinit var PASSWORD : String
var DB_TYPE  = mutableStateOf("")

object Constants {
    object Keys{
        const val NOTES_DATABASE = "notes_database"
        const val NOTE_TABLE = "notes_table"
        const val ADD_NEW_NOTE = "Add new Note"
        const val NOTE_TITLE = "Note title:"
        const val NOTE_SUBTITLE = "Note subtitle:"
        const val TITLE_TEXT = "title"
        const val SUBTITLE_TEXT = "subtitle"
        const val WHAT_WILL_WE_USE = "What will we use?"
        const val ROOM_DB = "ROOM database"
        const val FIREBASE_DB = "Firebase database"
        const val ID = "id"
        const val UPDATE = "UPDATE"
        const val DELETE = "DELETE"
        const val NAV_BACK = "NAV_BACK"
        const val EDIT_NOTES = "Edit note"
        const val EMPTY = "EMPTY"
        const val EMPTY_TEXT = ""
        const val SIGN_IN = "Sign In"
        const val LOG_IN = "Login:"
        const val LOGIN_TEXT = "Login"
        const val PASSWORD_TEXT = "Password"



    }

    object Screens {
        const val START_SCREEN = "start_screen"
        const val MAIN_SCREEN = "main_screen"
        const val ADD_SCREEN = "add_screen"
        const val NOTE_SCREEN = "note_screen"

    }
}