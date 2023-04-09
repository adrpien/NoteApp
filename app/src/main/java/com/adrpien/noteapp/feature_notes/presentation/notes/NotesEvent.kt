package com.adrpien.noteapp.feature_notes.presentation.notes

import com.adrpien.noteapp.feature_notes.domain.model.Note
import com.adrpien.noteapp.feature_notes.domain.util.OrderType
import com.adrpien.noteapp.feature_notes.domain.util.SortType

sealed class NotesEvent{
    data class OrderNotes(val sortType: SortType): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleSortSection: NotesEvent()
}
