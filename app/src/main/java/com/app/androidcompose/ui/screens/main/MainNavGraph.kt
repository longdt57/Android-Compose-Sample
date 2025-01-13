package com.app.androidcompose.ui.screens.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.androidcompose.ui.AppDestination
import com.app.androidcompose.ui.screens.main.gituser.GitUserListScreen
import com.app.androidcompose.ui.screens.main.gituserdetail.GitUserDetailScreen
import leegroup.module.compose.support.extensions.appNavigate
import leegroup.module.compose.support.extensions.composable

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
        composable<MainDestination.GitUserDetail.GitUserDetailNav> {
            GitUserDetailScreen(
                navigator = { destination ->
                    navController.appNavigate(destination)
                }
            )
        }
    }
}
