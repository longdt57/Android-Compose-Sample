package com.app.androidcompose.ui.screens.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.androidcompose.ui.AppDestination
import leegroup.module.compose.ui.models.BaseDestination
import com.app.androidcompose.ui.composable
import com.app.androidcompose.ui.navigateWithDestination
import com.app.androidcompose.ui.screens.main.gituser.GitUserListScreen
import com.app.androidcompose.ui.screens.main.gituserdetail.GitUserDetailScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {

    navigation(
        route = AppDestination.MainNavGraph.route,
        startDestination = MainDestination.GitUserList.destination
    ) {
        composable(MainDestination.GitUserList) {
            GitUserListScreen(
                navigator = { destination -> navController.appNavigate(destination) }
            )
        }
        composable<MainDestination.GitUserDetail.GitUserDetailNav> { backStackEntry ->
            GitUserDetailScreen(
                navigator = { destination ->
                    navController.appNavigate(destination)
                }
            )
        }
    }
}

fun NavHostController.appNavigate(destination: Any) {
    when (destination) {
        is BaseDestination -> navigateWithDestination(destination)
        else -> navigate(destination)
    }
}
