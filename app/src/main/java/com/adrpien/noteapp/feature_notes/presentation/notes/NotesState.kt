package com.adrpien.noteapp.feature_notes.presentation.notes

import com.adrpien.noteapp.feature_notes.domain.model.Note
import com.adrpien.noteapp.feature_notes.domain.util.OrderType
import com.adrpien.noteapp.feature_notes.domain.util.SortType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val sortType: SortType = SortType.Time(OrderType.Descending),
    val isSortSectionVisible: Boolean = false
) {
}