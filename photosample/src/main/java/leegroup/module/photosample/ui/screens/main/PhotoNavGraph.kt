package leegroup.module.photosample.ui.screens.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import leegroup.module.compose.support.extensions.appNavigate
import leegroup.module.compose.support.extensions.composable
import leegroup.module.photosample.ui.models.PhotoUiModel
import leegroup.module.photosample.ui.screens.main.photodetail.PhotoDetailScreen
import leegroup.module.photosample.ui.screens.main.photolist.PhotoListScreen

fun NavGraphBuilder.photoNavGraph(
    navController: NavHostController,
) {

    navigation(
        route = PhotoDestination.PhotoRoot.route,
        startDestination = PhotoDestination.PhotoList.destination
    ) {
        composable(PhotoDestination.PhotoList) {
            PhotoListScreen(
                navigator = { destination -> navController.appNavigate(destination) }
            )
        }
        composable<PhotoUiModel> {
            PhotoDetailScreen(
                navigator = { destination ->
                    navController.appNavigate(destination)
                }
            )
        }
    }
}
