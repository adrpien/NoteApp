package com.adrpien.noteapp.feature_notes.domain.repository

import com.adrpien.noteapp.feature_notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNote(id: Int): Note?

    suspend fun deleteNote(note: Note)

    suspend fun insertNote(note: Note)



}