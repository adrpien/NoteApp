package com.adrpien.noteapp.feature_notes.domain.use_case

import com.adrpien.noteapp.feature_notes.domain.model.Note
import com.adrpien.noteapp.feature_notes.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNote(id)
    }

}