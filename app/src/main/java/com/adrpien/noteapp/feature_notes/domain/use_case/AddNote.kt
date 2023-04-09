package com.adrpien.noteapp.feature_notes.domain.use_case

import com.adrpien.noteapp.feature_notes.domain.model.InvalidNoteException
import com.adrpien.noteapp.feature_notes.domain.model.Note
import com.adrpien.noteapp.feature_notes.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()){
            throw InvalidNoteException("The title note can not be empty")
        }
        if(note.description.isBlank()){
            throw InvalidNoteException("The description note can not be empty")
        }
        return repository.insertNote(note)
    }

}