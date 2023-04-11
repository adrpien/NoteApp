package com.adrpien.noteapp.feature_notes.presentation.add_edit_note

data class NoteTextFieldState(
    val isHintVisible: Boolean = true,
    val hint: String = "",
    val text: String = ""
) {
}