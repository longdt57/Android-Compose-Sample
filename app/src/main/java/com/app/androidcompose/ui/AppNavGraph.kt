package com.app.androidcompose.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.app.androidcompose.ui.base.BaseDestination
import com.app.androidcompose.ui.screens.main.mainNavGraph

@Composable
fun AppNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = AppDestination.RootNavGraph.route,
        startDestination = AppDestination.MainNavGraph.destination
    ) {
        mainNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.composable(
    destination: BaseDestination,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        deepLinks = destination.deepLinks.map {
            navDeepLink {
                uriPattern = it
            }
        },
        content = content
    )
}

fun NavHostController.appNavigate(destination: Any) {
    when (destination) {
        is BaseDestination -> navigateWithDestination(destination)
        else -> navigate(destination)
    }
}

fun NavHostController.navigateWithDestination(destination: BaseDestination) {
    when (destination) {
        is BaseDestination.Up -> {
            destination.results.forEach { (key, value) ->
                previousBackStackEntry?.savedStateHandle?.set(key, value)
            }
            navigateUp()
        }
        else -> navigate(route = destination.destination)
    }
}
