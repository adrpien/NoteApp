package com.adrpien.noteapp.feature_notes.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adrpien.noteapp.feature_notes.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val dao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_database"
    }

}