package com.app.androidcompose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.androidcompose.ui.screens.main.MainScreen
import com.example.note.ui.noteNavGraph
import leegroup.module.compose.support.extensions.appNavigate
import leegroup.module.photosample.ui.screens.main.photoNavGraph
import leegroup.module.sample.gituser.ui.screens.gitUserNavGraph

@Composable
fun AppNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = AppDestination.RootNavGraph.route,
        startDestination = AppDestination.MainScreen.destination
    ) {
        composable(AppDestination.MainScreen.route) {
            MainScreen(
                navigator = { destination -> navController.appNavigate(destination) }
            )
        }
        photoNavGraph(navController = navController)
        gitUserNavGraph(navController = navController)
        noteNavGraph(navController = navController)
    }
}
