package ru.com.vbulat.notesmvvmcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.com.vbulat.notesmvvmcompose.MainViewModel
import ru.com.vbulat.notesmvvmcompose.screens.AddScreen
import ru.com.vbulat.notesmvvmcompose.screens.MainScreen
import ru.com.vbulat.notesmvvmcompose.screens.NoteScreen
import ru.com.vbulat.notesmvvmcompose.screens.StartScreen
import ru.com.vbulat.notesmvvmcompose.utils.Constants
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Screens.ADD_SCREEN
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Screens.MAIN_SCREEN
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Screens.NOTE_SCREEN
import ru.com.vbulat.notesmvvmcompose.utils.Constants.Screens.START_SCREEN

sealed class NavRoute(val route: String) {
    object Start : NavRoute (START_SCREEN)
    object Main : NavRoute (MAIN_SCREEN)
    object Add : NavRoute (ADD_SCREEN)
    object Note : NavRoute (NOTE_SCREEN)

}
@Composable
fun NotesNavHost(mainViewModel: MainViewModel, navController: NavHostController) {

    NavHost(navController = navController, startDestination = NavRoute.Start.route){
        composable(NavRoute.Start.route) { StartScreen(navController = navController, viewModel = mainViewModel)}
        composable(NavRoute.Main.route) { MainScreen(navController = navController, viewModel = mainViewModel)}
        composable(NavRoute.Add.route) { AddScreen(navController = navController, viewModel = mainViewModel)}
//        composable(NavRoute.Note.route) { NoteScreen(navController = navController, viewModel = mainViewModel, noteId = "1")}
        composable(NavRoute.Note.route + "/{${Constants.Keys.ID}}") {backstackEntry ->
            NoteScreen(
                navController = navController,
                viewModel = mainViewModel,
                noteId = backstackEntry.arguments?.getString(Constants.Keys.ID),
            )
        }
    }
}