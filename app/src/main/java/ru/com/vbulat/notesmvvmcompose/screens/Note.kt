package ru.com.vbulat.notesmvvmcompose.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.com.vbulat.notesmvvmcompose.MainViewModel
import ru.com.vbulat.notesmvvmcompose.MainViewModelFactory
import ru.com.vbulat.notesmvvmcompose.model.Note
import ru.com.vbulat.notesmvvmcompose.ui.theme.NotesMVVMComposeTheme
import ru.com.vbulat.notesmvvmcompose.utils.Constants
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.NONE
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.SUBTITLE_TEXT
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.TITLE_TEXT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(navController: NavHostController, viewModel: MainViewModel, noteId: String?) {
    val notes = viewModel.readAllNotes().observeAsState(listOf()).value
    val note = notes.firstOrNull {it.id == noteId?.toInt()} ?: Note(title = NONE, subtitle = NONE)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = note.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = note.subtitle,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun PrevNoteScreen(){
    NotesMVVMComposeTheme {
        val context = LocalContext.current
        val mainViewModel : MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        NoteScreen(
            navController = rememberNavController(),
            viewModel = mainViewModel,
            noteId = "1",
        )
    }
}