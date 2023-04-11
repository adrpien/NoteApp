package com.adrpien.noteapp.feature_notes.presentation.add_edit_note

import android.graphics.Color
import android.os.AsyncTask
import android.os.AsyncTask.execute
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.loader.content.AsyncTaskLoader
import com.adrpien.noteapp.feature_notes.domain.model.InvalidNoteException
import com.adrpien.noteapp.feature_notes.domain.model.Note
import com.adrpien.noteapp.feature_notes.domain.use_case.NoteUseCases
import com.adrpien.noteapp.ui.theme.Purple200
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var currentNoteId: Int? = null

    private val _noteTitle = MutableStateFlow(NoteTextFieldState(
        hint = "EnterTitle"
    ))

    val noteTitle= _noteTitle

    private val _noteDescription = MutableStateFlow(NoteTextFieldState(
        hint = "Enter description..."
    ))
    val noteDescription= _noteDescription

    private val _noteColor = MutableStateFlow(Note.colorList[0])
    val noteColor= _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        object SaveNote: UiEvent()
        data class ShowSnackBar(val messege: String): UiEvent()
    }


    init{
        savedStateHandle.get<Int>("noteId")?.let { id ->
            if(id != -1) {
                CoroutineScope(Dispatchers.IO).launch{
                    noteUseCases.getNote(id)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteDescription.value = noteDescription.value.copy(
                            text = note.description,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(addEditNoteEvent: AddEditNoteEvent){
        when(addEditNoteEvent) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = addEditNoteEvent.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible  =! addEditNoteEvent.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredDescription -> {
                _noteDescription.value = noteDescription.value.copy(
                    text = addEditNoteEvent.value
                )
            }
            is AddEditNoteEvent.ChangeDescritionFocus -> {
                _noteDescription.value = noteDescription.value.copy(
                    isHintVisible  =! addEditNoteEvent.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredDescription -> {

            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                description = noteDescription.value.text,
                                color = noteColor.value,
                                time = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                messege = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = addEditNoteEvent.color
            }
        }
    }

}