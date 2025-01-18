package leegroup.module.sample.gituser.ui.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import leegroup.module.compose.support.extensions.appNavigate
import leegroup.module.compose.support.extensions.composable
import leegroup.module.sample.gituser.ui.screens.gituser.GitUserListScreen
import leegroup.module.sample.gituser.ui.screens.gituserdetail.GitUserDetailScreen

fun NavGraphBuilder.gitUserNavGraph(
    navController: NavHostController,
) {

    navigation(
        route = GitUserDestination.GitUserRoot.route,
        startDestination = GitUserDestination.GitUserList.destination
    ) {
        composable(GitUserDestination.GitUserList) {
            GitUserListScreen(
                navigator = { destination -> navController.appNavigate(destination) }
            )
        }
        composable<GitUserDestination.GitUserDetail.GitUserDetailNav> {
            GitUserDetailScreen(
                navigator = { destination ->
                    navController.appNavigate(destination)
                }
            )
        }
    }
}
