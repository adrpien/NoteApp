package com.adrpien.noteapp.feature_notes.domain.use_case

data class NoteUseCases(
    val  getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote
) {
}