package com.adrpien.noteapp.feature_notes.presentation.notes.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.adrpien.noteapp.feature_notes.presentation.notes.NotesEvent
import com.adrpien.noteapp.feature_notes.presentation.notes.NotesViewModel
import com.adrpien.noteapp.feature_notes.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
) {

    val state = viewModel.notesState.value
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEditNoteScreen.route)},
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add")
            }
        },
        scaffoldState = scaffoldState

    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(padding)) {
            Row(
                modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Notes")
                IconButton(onClick = {
                    viewModel.onEvent(NotesEvent.ToggleSortSection)

                }) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort")
                }
            }
            AnimatedVisibility(
                visible = state.isSortSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SortSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    sortType = state.sortType,
                    onSortChange = {
                        viewModel.onEvent(NotesEvent.OrderNotes(it))
                    }
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            LazyColumn(Modifier.fillMaxSize()){
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.AddEditNoteScreen.route +
                                        "?noteId=${note.id}&noteColor=${note.color}")
                            },
                         onDeleteClick = {
                             viewModel.onEvent(NotesEvent.DeleteNote(note = note))
                             coroutineScope.launch {
                                 val actionResult = scaffoldState.snackbarHostState.showSnackbar(
                                     message = "Note Deleted",
                                     actionLabel = "Undo",
                                 )
                                 if(actionResult == SnackbarResult.ActionPerformed) {
                                     viewModel.onEvent(NotesEvent.RestoreNote)
                                 }
                             }
                         }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

}