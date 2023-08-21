package ru.com.vbulat.notesmvvmcompose.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import ru.com.vbulat.notesmvvmcompose.MainViewModel
import ru.com.vbulat.notesmvvmcompose.MainViewModelFactory
import ru.com.vbulat.notesmvvmcompose.model.Note
import ru.com.vbulat.notesmvvmcompose.ui.theme.NotesMVVMComposeTheme
import ru.com.vbulat.notesmvvmcompose.utils.Constants
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.DELETE
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.NAV_BACK
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.NONE
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.UPDATE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(navController: NavHostController, viewModel: MainViewModel, noteId: String?) {
    val notes = viewModel.readAllNotes().observeAsState(listOf()).value
    val note = notes.firstOrNull {it.id == noteId?.toInt()} ?: Note(title = NONE, subtitle = NONE)
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var title by remember { mutableStateOf(Constants.Keys.EMPTY) }
    var subtitle by remember { mutableStateOf(Constants.Keys.EMPTY) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {paddingValues ->

        if (showBottomSheet){
            ModalBottomSheet(
                sheetState = bottomSheetState,
                tonalElevation = 5.dp,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                onDismissRequest = {showBottomSheet = false},
            ) {
                Surface {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    ) {
                        Text(
                            text = Constants.Keys.EDIT_NOTES,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        OutlinedTextField(
                            value = title,
                            onValueChange = {title = it},
                            label = { Text(text = Constants.Keys.TITLE_TEXT)},
                            isError = title.isEmpty()
                        )
                        OutlinedTextField(
                            value = subtitle,
                            onValueChange = {subtitle = it},
                            label = { Text(text = Constants.Keys.SUBTITLE_TEXT)},
                            isError = subtitle.isEmpty()
                        )
                        Button(
                            modifier = Modifier
                                .padding(top = 16.dp),
                            onClick = {
                                viewModel.updateNote(note = Note(id = note.id, title = title, subtitle = subtitle)){
                                    showBottomSheet = false
                                    //navController.navigate(NavRoute.Main.route)
                                    navController.popBackStack()
                                }
                            },
                        ) {
                            Text(text = UPDATE)
                        }
                    }
                }
            }
        }

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
            Row (
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,

            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            title = note.title
                            subtitle = note.subtitle
                            showBottomSheet = true
                        }

                    },
                ) {
                    Text(text = UPDATE)
                }
                Button(
                    onClick = {
                        viewModel.deleteNote(note){
                            //navController.navigate(NavRoute.Main.route)
                            navController.popBackStack()
                        }
                    },
                ) {
                    Text(text = DELETE)
                }
            }

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                onClick = {
                    //navController.navigate(NavRoute.Main.route)
                    navController.popBackStack()
                },
            ) {
                Text(text = NAV_BACK)
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