package ru.com.vbulat.notesmvvmcompose

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.com.vbulat.notesmvvmcompose.navigation.NotesNavHost
import ru.com.vbulat.notesmvvmcompose.ui.theme.NotesMVVMComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesMVVMComposeTheme {
                val context = LocalContext.current
                val mainViewModel : MainViewModel =
                    viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Notes App") },
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
                            NotesNavHost(mainViewModel)
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