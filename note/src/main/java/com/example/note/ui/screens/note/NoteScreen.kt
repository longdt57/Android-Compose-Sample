package com.example.note.ui.screens.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.note.ui.components.SearchBar
import com.example.note.ui.models.NoteUiModel
import com.example.note.ui.models.NoteUiState
import com.example.note.ui.screens.note.components.AddNoteInput
import com.example.note.ui.screens.note.components.NoteAppBar
import com.example.note.ui.screens.note.components.NoteEmpty
import com.example.note.ui.screens.note.components.NotesGrid
import kotlinx.collections.immutable.persistentListOf
import leegroup.module.compose.support.extensions.randomString
import leegroup.module.compose.ui.components.BaseScreen
import leegroup.module.compose.ui.models.LoadingState
import leegroup.module.compose.ui.theme.ComposeTheme

@Composable
internal fun NoteScreen(
    navigator: (destination: Any) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: NoteViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()

    val showEmpty by remember {
        derivedStateOf {
            uiState.items.isEmpty() && loading !is LoadingState.Loading
        }
    }

    LaunchedEffect(Unit) {
        if (uiState.items.isEmpty()) {
            viewModel.loadNotes()
        }
    }

    BaseScreen(viewModel) {
        NoteScreenContent(
            modifier = modifier,
            uiState = uiState,
            showEmpty = showEmpty,
            onDelete = { viewModel.onDeleteNote(it.id) },
            onClick = { navigator.invoke(it) },
            onAdd = { viewModel.onAddNote(it) },
            onQueryChanged = { viewModel.onQueryChanged(it) },
            onQueryClear = { viewModel.onQueryChanged("") },
            onLoadMore = { viewModel.loadNotes() }
        )
    }
}

@Composable
internal fun NoteScreenContent(
    uiState: NoteUiState,
    showEmpty: Boolean = false,
    onLoadMore: () -> Unit = {},
    onDelete: (NoteUiModel) -> Unit = {},
    onClick: (NoteUiModel) -> Unit = {},
    onAdd: (String) -> Unit = {},
    onQueryChanged: (String) -> Unit = {},
    onQueryClear: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        NoteAppBar()
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp),
            query = uiState.query,
            onQueryChanged = onQueryChanged,
            onClear = onQueryClear
        )
        AddNoteInput(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            onAdd = onAdd
        )
        if (uiState.items.isNotEmpty()) {
            NotesGrid(
                modifier = Modifier.weight(1f),
                notes = uiState.items,
                onLoadMore = onLoadMore,
                onDelete = onDelete,
                onClick = onClick,
            )
        } else if (showEmpty) {
            NoteEmpty(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@PreviewScreenSizes
@Composable
@Suppress("MagicNumber")
private fun PreviewNotesScreen() {
    val uiModel = NoteUiState(
        items = persistentListOf(
            NoteUiModel(id = 1, content = "First note", date = "2024-01-22 10:30:00"),
            NoteUiModel(
                id = 2,
                content = "Second note: " + randomString(50),
                date = "2024-01-22 11:00:00"
            ),
            NoteUiModel(id = 3, content = "Third note", date = "2024-01-22 11:30:00")
        )
    )

    ComposeTheme {
        NoteScreenContent(uiModel)
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_TABLET
)
@Composable
@Suppress("MagicNumber")
private fun PreviewLargeDeviceNotesScreen() {
    val uiModel = NoteUiState(
        items = persistentListOf(
            NoteUiModel(id = 1, content = "First note", date = "2024-01-22 10:30:00"),
            NoteUiModel(
                id = 2,
                content = "Second note: " + randomString(50),
                date = "2024-01-22 11:00:00"
            ),
            NoteUiModel(id = 3, content = "Third note", date = "2024-01-22 11:30:00")
        )
    )

    ComposeTheme {
        NoteScreenContent(uiModel)
    }
}
