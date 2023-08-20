package ru.com.vbulat.notesmvvmcompose.utils

import ru.com.vbulat.notesmvvmcompose.database.DatabaseRepository

const val TYPE_DATABASE = "type_database"
const val TYPE_ROOM = "type_room"
const val TYPE_FIREBASE = "type_firebase"

lateinit var REPOSITORY : DatabaseRepository

object Constants {
    object Keys{
        const val NOTES_DATABASE = "notes_database"
        const val NOTE_TABLE = "notes_table"
        const val ADD_NEW_NOTE = "Add new Note"
        const val NOTE_TITLE = "Note title:"
        const val NOTE_SUBTITLE = "Note subtitle:"
        const val TITLE_TEXT = "Title"
        const val SUBTITLE_TEXT = "Subtitle"
        const val WHAT_WILL_WE_USE = "What will we use?"
        const val ROOM_DB = "ROOM database"
        const val FIREBASE_DB = "Firebase database"
        const val ID = "id"
        const val NONE = "none"


    }

    object Screens {
        const val START_SCREEN = "start_screen"
        const val MAIN_SCREEN = "main_screen"
        const val ADD_SCREEN = "add_screen"
        const val NOTE_SCREEN = "note_screen"

    }
}