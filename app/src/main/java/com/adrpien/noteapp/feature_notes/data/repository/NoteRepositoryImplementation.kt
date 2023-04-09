package com.adrpien.noteapp.feature_notes.data.repository

import com.adrpien.noteapp.feature_notes.data.data_source.local.NoteDao
import com.adrpien.noteapp.feature_notes.domain.model.Note
import com.adrpien.noteapp.feature_notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImplementation (
    private val noteDao: NoteDao
): NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
   }

    override suspend fun getNote(id: Int): Note? {
        return noteDao.getNote(id)
    }

    override suspend fun deleteNote(note: Note) {
        return noteDao.deleteNote(note)
    }

    override suspend fun insertNote(note: Note) {
        return noteDao.insertNote(note)
    }

}