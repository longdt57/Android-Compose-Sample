package leegroup.module.phototopdf.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import leegroup.module.compose.support.extensions.appNavigate
import leegroup.module.compose.support.extensions.composable
import leegroup.module.phototopdf.ui.screens.main.PhotoToPdfScreen

@Composable
fun PhotoToPdfNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        route = "Root",
        startDestination = PhotoToPdfDestination.PhotoToPdfMainScreen.destination,
        modifier = modifier
    ) {
        photoToPdfNavGraph(navController = navController)
    }
}


fun NavGraphBuilder.photoToPdfNavGraph(
    navController: NavHostController,
) {
    composable(PhotoToPdfDestination.PhotoToPdfMainScreen) {
        PhotoToPdfScreen(
            navigator = { destination -> navController.appNavigate(destination) },
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .statusBarsPadding()
                .navigationBarsPadding()
        )
    }
}
