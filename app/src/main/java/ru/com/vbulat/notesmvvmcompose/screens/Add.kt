package ru.com.vbulat.notesmvvmcompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.com.vbulat.notesmvvmcompose.navigation.NavRoute
import ru.com.vbulat.notesmvvmcompose.ui.theme.NotesMVVMComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen (navController: NavHostController) {
    var title by remember {mutableStateOf("")}
    var subtitle by remember {mutableStateOf("")}

    Scaffold {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Add new Note:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(text = "Note title:")}
            )

            OutlinedTextField(
                value = subtitle,
                onValueChange = { subtitle = it },
                label = { Text(text = "Note subtitle:")}
            )

            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = { navController.navigate(NavRoute.Main.route) },
            ) {
                Text(text = "Add new Note")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddScreen(){
    NotesMVVMComposeTheme {
        AddScreen(navController = rememberNavController())
    }
}