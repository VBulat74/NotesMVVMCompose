package ru.com.vbulat.notesmvvmcompose.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.com.vbulat.notesmvvmcompose.MainViewModel
import ru.com.vbulat.notesmvvmcompose.MainViewModelFactory
import ru.com.vbulat.notesmvvmcompose.navigation.NavRoute
import ru.com.vbulat.notesmvvmcompose.ui.theme.NotesMVVMComposeTheme
import ru.com.vbulat.notesmvvmcompose.utils.Constants
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.FIREBASE_DB
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.ROOM_DB
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.WHAT_WILL_WE_USE
import ru.com.vbulat.notesmvvmcompose.utils.TYPE_FIREBASE
import ru.com.vbulat.notesmvvmcompose.utils.TYPE_ROOM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(navController: NavHostController, viewModel: MainViewModel) {
    Scaffold (
        modifier = Modifier.fillMaxSize()
    ) {paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = WHAT_WILL_WE_USE)
            Button(
                onClick = {
                    viewModel.initDatabase(TYPE_ROOM){
                        navController.navigate(route = NavRoute.Main.route)
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp),
            ) {
                Text(text = ROOM_DB)
            }

            Button(
                onClick = {
                    viewModel.initDatabase(TYPE_FIREBASE) {
                        navController.navigate(route = NavRoute.Main.route)
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp),
            ) {
                Text(text = FIREBASE_DB)
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun PrevStartScreen(){
    NotesMVVMComposeTheme {
        val context = LocalContext.current
        val mainViewModel : MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

        StartScreen(navController = rememberNavController(), viewModel = mainViewModel)
    }
}