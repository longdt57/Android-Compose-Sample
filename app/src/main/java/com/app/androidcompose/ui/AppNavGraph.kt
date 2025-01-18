package com.app.androidcompose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import leegroup.module.sample.gituser.ui.screens.GitUserDestination
import leegroup.module.sample.gituser.ui.screens.gitUserNavGraph

@Composable
fun AppNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = AppDestination.RootNavGraph.route,
        startDestination = GitUserDestination.GitUserRoot.route
    ) {
        gitUserNavGraph(navController = navController)
    }
}
