package com.app.androidcompose.ui.screens.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.app.androidcompose.ui.AppDestination
import com.app.androidcompose.ui.appNavigate
import com.app.androidcompose.ui.composable
import com.app.androidcompose.ui.screens.main.home.HomeScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    navigation(
        route = AppDestination.MainNavGraph.route,
        startDestination = MainDestination.Home.destination
    ) {
        composable(MainDestination.Home) {
            HomeScreen(
                navigator = { destination -> navController.appNavigate(destination) }
            )
        }
    }
}
