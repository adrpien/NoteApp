package com.adrpien.noteapp.feature_notes.domain.use_case

import com.adrpien.noteapp.feature_notes.domain.model.Note
import com.adrpien.noteapp.feature_notes.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke (note: Note){
        return repository.deleteNote(note)
    }
}