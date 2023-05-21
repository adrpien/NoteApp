package com.adrpien.noteapp.feature_notes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgs
import androidx.navigation.navArgument
import com.adrpien.noteapp.feature_notes.presentation.add_edit_note.components.AddEditNoteScreen
import com.adrpien.noteapp.feature_notes.presentation.notes.components.NotesScreen
import com.adrpien.noteapp.feature_notes.presentation.util.Screen
import com.adrpien.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route) {
                        composable(
                            route = Screen.NotesScreen.route
                        ){
                            NotesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument("noteId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument("noteColor") {
                                    type = androidx.navigation.NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ){
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = it.arguments?.getInt("noteColor") ?: -1 ,

                            )
                        }
                    }
                }
            }
        }
    }
}
