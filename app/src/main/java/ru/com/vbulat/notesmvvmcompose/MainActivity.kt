package ru.com.vbulat.notesmvvmcompose

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ru.com.vbulat.notesmvvmcompose.navigation.NavRoute
import ru.com.vbulat.notesmvvmcompose.navigation.NotesNavHost
import ru.com.vbulat.notesmvvmcompose.ui.theme.NotesMVVMComposeTheme
import ru.com.vbulat.notesmvvmcompose.utils.DB_TYPE

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesMVVMComposeTheme {
                val context = LocalContext.current
                val mainViewModel : MainViewModel =
                    viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Row (modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "Notes App")
                                    if (DB_TYPE.value.isNotEmpty()){
                                        Icon(
                                            imageVector = Icons.Default.ExitToApp,
                                            contentDescription = "Icon exit",
                                            modifier = Modifier.clickable {
                                                mainViewModel.sign_out {
                                                    navController.navigate(NavRoute.Start.route){
                                                        popUpTo(NavRoute.Start.route){
                                                            inclusive = true
                                                        }
                                                    }
                                                }
                                            }
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults
                                .smallTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                ),
                        )
                    },
                    content = { padding ->
                        Surface(
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxSize(),
                        ) {
                            NotesNavHost(mainViewModel = mainViewModel, navController = navController)
                        }

                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesMVVMComposeTheme {

    }
}