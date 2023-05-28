package com.adrpien.noteapp.feature_notes.presentation.add_edit_note.components

import androidx.compose.ui.graphics.*
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.graphics.toColor
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.adrpien.noteapp.feature_notes.domain.model.Note
import com.adrpien.noteapp.feature_notes.presentation.add_edit_note.AddEditNoteEvent
import com.adrpien.noteapp.feature_notes.presentation.add_edit_note.AddEditViewModel
import com.adrpien.noteapp.feature_notes.presentation.add_edit_note.TransparentTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    modifier: Modifier = Modifier,
    addEditViewModel: AddEditViewModel = hiltViewModel(),
    navController: NavController,
    noteColor: Int
) {

    val titleState = addEditViewModel.noteTitle.collectAsState().value
    val descriptionState = addEditViewModel.noteDescription.collectAsState().value

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(
                if (noteColor != -1) noteColor else addEditViewModel.noteColor.value
            )
        )
    }

    LaunchedEffect(key1 = true) {
        addEditViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
                is AddEditViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.messege
                    )

                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addEditViewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save"
                )
            }
        },
        scaffoldState = scaffoldState
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(padding)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.colorList.forEach() { colorInt ->
                    val color = Color(colorInt.red, colorInt.green, colorInt.blue)
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (addEditViewModel.noteColor.value == colorInt) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                coroutineScope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = color,
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                addEditViewModel.onEvent(
                                    AddEditNoteEvent.ChangeColor(
                                        colorInt
                                    )
                                )
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TransparentTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    addEditViewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    addEditViewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(16.dp))
            TransparentTextField(
                text = descriptionState.text,
                hint = descriptionState.hint,
                onValueChange = {
                    addEditViewModel.onEvent(AddEditNoteEvent.EnteredDescription(it))
                },
                onFocusChange = {
                    addEditViewModel.onEvent(AddEditNoteEvent.ChangeDescritionFocus(it))
                },
                isHintVisible = descriptionState.isHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
