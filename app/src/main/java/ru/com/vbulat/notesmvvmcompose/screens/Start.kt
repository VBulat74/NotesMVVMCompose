package ru.com.vbulat.notesmvvmcompose.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import ru.com.vbulat.notesmvvmcompose.MainViewModel
import ru.com.vbulat.notesmvvmcompose.MainViewModelFactory
import ru.com.vbulat.notesmvvmcompose.navigation.NavRoute
import ru.com.vbulat.notesmvvmcompose.ui.theme.NotesMVVMComposeTheme
import ru.com.vbulat.notesmvvmcompose.utils.Constants
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.FIREBASE_DB
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.ROOM_DB
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Keys.WHAT_WILL_WE_USE
import ru.com.vbulat.notesmvvmcompose.utils.DB_TYPE
import ru.com.vbulat.notesmvvmcompose.utils.LOGIN
import ru.com.vbulat.notesmvvmcompose.utils.PASSWORD
import ru.com.vbulat.notesmvvmcompose.utils.TYPE_FIREBASE
import ru.com.vbulat.notesmvvmcompose.utils.TYPE_ROOM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(navController: NavHostController, viewModel: MainViewModel) {
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    //val coroutineScope = rememberCoroutineScope()
    var login by remember { mutableStateOf(Constants.Keys.EMPTY_TEXT) }
    var password by remember { mutableStateOf(Constants.Keys.EMPTY_TEXT) }

    Scaffold (
        modifier = Modifier.fillMaxSize()
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
                            text = Constants.Keys.LOG_IN,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        OutlinedTextField(
                            value = login,
                            onValueChange = {login = it},
                            label = { Text(text = Constants.Keys.LOGIN_TEXT)},
                            isError = login.isEmpty()
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = {password = it},
                            label = { Text(text = Constants.Keys.PASSWORD_TEXT)},
                            isError = password.isEmpty()
                        )
                        Button(
                            modifier = Modifier
                                .padding(top = 16.dp),
                            onClick = {
                                LOGIN = login
                                PASSWORD = password
                                viewModel.initDatabase(TYPE_FIREBASE){
                                    DB_TYPE = TYPE_FIREBASE
                                    showBottomSheet = false
                                    navController.navigate(route = NavRoute.Main.route)
                                }
                            },
                            enabled = login.isNotEmpty() && password.isNotEmpty()
                        ) {
                            Text(text = Constants.Keys.SIGN_IN)
                        }
                    }
                }
            }
        }

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
                        DB_TYPE = TYPE_ROOM
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
                    showBottomSheet = true
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