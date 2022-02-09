package com.ajhavery.jetnoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.ajhavery.jetnoteapp.screens.NoteScreen
import com.ajhavery.jetnoteapp.screens.NoteViewModel
import com.ajhavery.jetnoteapp.ui.theme.JetNoteAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ajhavery.jetnoteapp.model.Note
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNoteAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    // val noteViewModel = viewModel<NoteViewModel>() <- this also works
                    val noteViewModel: NoteViewModel by viewModels()
                    NotesApp(noteViewModel)
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun NotesApp(noteViewModel: NoteViewModel) {
    // val notesList = noteViewModel.getAllNotes()
    val notesList = noteViewModel.noteList.collectAsState().value
    NoteScreen(notes = notesList,
        onAddNote = { noteViewModel.addNote(it) },
        onRemoveNote = { noteViewModel.removeNote(it) }
    )
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(color = MaterialTheme.colors.background)
    {
        NoteScreen(notes = emptyList(), onAddNote = {}, onRemoveNote = {})
    }
}