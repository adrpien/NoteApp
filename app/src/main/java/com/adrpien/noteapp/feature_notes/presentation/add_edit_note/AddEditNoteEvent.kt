package com.adrpien.noteapp.feature_notes.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState
import com.adrpien.noteapp.feature_notes.domain.model.Note
import com.adrpien.noteapp.feature_notes.presentation.notes.NotesEvent

sealed class AddEditNoteEvent {
    data class EnteredTitle(val value: String): AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditNoteEvent()
    data class EnteredDescription(val value: String): AddEditNoteEvent()
    data class ChangeDescritionFocus(val focusState: FocusState): AddEditNoteEvent()
    data class ChangeColor( val color: Int): AddEditNoteEvent()
     object SaveNote: AddEditNoteEvent()

}