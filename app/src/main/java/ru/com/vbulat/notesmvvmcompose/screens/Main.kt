package ru.com.vbulat.notesmvvmcompose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun MainScreen (navController: NavHostController) {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(NavRoute.Add.route) },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Icons")
            }
        },
        ) {paddingValues ->
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)) {
            NoteItem(
                title = "Title 1",
                subtitle = "Subtitle for note 1",
                navController = navController
            )
            NoteItem(
                title = "Title 2",
                subtitle = "Subtitle for note 2",
                navController = navController
            )
            NoteItem(
                title = "Title 3",
                subtitle = "Subtitle for note 3",
                navController = navController
            )
        }


    }
}

@Composable
fun NoteItem(
    title: String,
    subtitle: String,
    navController: NavHostController
){
    Card (
        modifier = Modifier
            .padding(
                vertical = 8.dp,
                horizontal = 24.dp
            )
            .fillMaxWidth()
            .clickable {
                navController.navigate(NavRoute.Note.route)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
            )
        }
    }
}

@Preview (showBackground = true)
@Composable
fun PrevMainScreen(){
    NotesMVVMComposeTheme {
        MainScreen(navController = rememberNavController())
    }
}