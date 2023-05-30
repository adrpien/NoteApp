package com.adrpien.noteapp.feature_notes.data.data_source.local

import com.google.common.truth.Truth.assertThat
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.adrpien.noteapp.feature_notes.domain.model.Note
import com.adrpien.noteapp.ui.theme.Purple200
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalDateTime

@SmallTest
@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    private lateinit var database: NoteDatabase
    private lateinit var dao: NoteDao


    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            NoteDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.dao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertNote() = runTest {
        val note = Note(
            "title",
            "description",
            LocalDate.of(1995, 4,3).toEpochDay(),
            Purple200,
            1
        )
        dao.insertNote(note)
        val result = dao.getAllNotes().first()
        assertThat(result).contains(note)
    }

    @Test
    fun deleteNote()= runTest {
        val note =  Note(
            "title",
            "description",
            LocalDate.of(1995,4,3).toEpochDay(),
            Purple200,
            1
        )
        dao.insertNote(note)
        dao.deleteNote(note)
        val result = dao.getAllNotes().first()
        assertThat(result).doesNotContain(note)
    }

}