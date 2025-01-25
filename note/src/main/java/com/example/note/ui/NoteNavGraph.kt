package com.example.note.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.note.ui.models.NoteUiModel
import com.example.note.ui.screens.note.NoteScreen
import com.example.note.ui.screens.notedetail.NoteDetailScreen
import leegroup.module.compose.support.extensions.appNavigate
import leegroup.module.compose.support.extensions.composable

fun NavGraphBuilder.noteNavGraph(
    navController: NavHostController,
) {
    composable(NoteDestination.NoteScreen) {
        NoteScreen(
            navigator = { destination -> navController.appNavigate(destination) },
            modifier = Modifier.fillMaxSize()
        )
    }
    composable<NoteUiModel> { entry ->
        val model = entry.toRoute<NoteUiModel>()
        NoteDetailScreen(
            content = model.content,
            navigator = { destination ->
                navController.appNavigate(destination)
            }
        )
    }
}
