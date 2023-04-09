package com.adrpien.noteapp.feature_notes.presentation.notes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrpien.noteapp.feature_notes.domain.model.InvalidNoteException
import com.adrpien.noteapp.feature_notes.domain.model.Note
import com.adrpien.noteapp.feature_notes.domain.repository.NoteRepository
import com.adrpien.noteapp.feature_notes.domain.use_case.NoteUseCases
import com.adrpien.noteapp.feature_notes.domain.util.OrderType
import com.adrpien.noteapp.feature_notes.domain.util.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    private val _notesState = mutableStateOf(NotesState())
    val  notesState = _notesState

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(SortType.Time(OrderType.Descending))
    }

    fun onEvent(notesEvent: NotesEvent){
        when(notesEvent) {
            is NotesEvent.OrderNotes -> {
                if  (notesState.value.sortType == notesEvent.sortType::class &&
                    notesState.value.sortType.orderType == notesEvent.sortType.orderType) {
                    return
                }
                return getNotes(notesEvent.sortType)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(notesEvent.note)
                    recentlyDeletedNote = notesEvent.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    // In Kotlin, the return@label syntax is used for specifying which function among several nested ones this statement returns from.
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleSortSection -> {
                _notesState.value = _notesState.value.copy(
                    isSortSectionVisible = !_notesState.value.isSortSectionVisible
                )
            }
        }
    }

    private fun getNotes(sortType: SortType) {
        getNotesJob = null
        getNotesJob = noteUseCases.getNotes(sortType).onEach { notes ->
            notesState.value = notesState.value.copy(
                notes = notes,
                sortType = sortType
            )
        }.launchIn(viewModelScope)
        // WHY DO I NEED LAUNCHIN 
    }

}