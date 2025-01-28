package leegroup.module.phototopdf.ui.screens.main

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import leegroup.module.compose.support.extensions.collectAsEffect
import leegroup.module.compose.support.extensions.launchImage
import leegroup.module.compose.support.extensions.rememberCameraCaptureState
import leegroup.module.compose.ui.components.BaseScreen
import leegroup.module.compose.ui.models.LoadingState
import leegroup.module.compose.ui.theme.GreenBold800
import leegroup.module.coreimage.permission.MediaPermissionChecker
import leegroup.module.coreimage.ui.permission.PermissionView
import leegroup.module.phototopdf.R
import leegroup.module.phototopdf.ui.models.PdfUiModel
import leegroup.module.phototopdf.ui.models.PhotoToPdfScreenUiModel
import leegroup.module.phototopdf.ui.screens.main.components.FabOption
import leegroup.module.phototopdf.ui.screens.main.components.PhotoToPdfAppBar
import leegroup.module.phototopdf.ui.screens.main.components.PhotoToPdfContent
import leegroup.module.phototopdf.ui.screens.main.components.PhotoToPdfFAB
import leegroup.module.phototopdf.ui.screens.main.components.PhotoToPdfFabModalBottomSheet
import leegroup.module.phototopdf.ui.screens.main.components.PhotoToPdfPreviewDialog

@Composable
internal fun PhotoToPdfScreen(
    navigator: (Any) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<PhotoToPdfViewModel>()
    val uiState: PhotoToPdfScreenUiModel by viewModel.uiState.collectAsStateWithLifecycle()

    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val showRefresh by remember {
        derivedStateOf {
            uiState.items.isEmpty() && loading !is LoadingState.Loading
        }
    }

    val context = LocalContext.current
    val permissionChecker = remember { MediaPermissionChecker(context) }

    BaseScreen(viewModel) {
        PermissionView(
            permissionChecker,
            grantAccessTitle = stringResource(R.string.no_media_found_title),
            grantAccessText = stringResource(R.string.no_media_found_message),
            settingText = stringResource(id = R.string.setting_permission_media),
            onGranted = {
                PhotoToPdfScreenLayout(
                    uiState = uiState,
                    onUriSelected = { viewModel.convertToPdf(it) },
                    showRefresh = showRefresh,
                    modifier = modifier,
                    onRefresh = { viewModel.getPdfFiles() },
                    onSave = { viewModel.saveToDocuments(it) },
                    onDeleted = { viewModel.delete(it) }
                )
                SuccessSnackBar(
                    viewModel,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(top = 16.dp)
                )
            }
        )
    }
}

@Composable
internal fun SuccessSnackBar(viewModel: PhotoToPdfViewModel, modifier: Modifier = Modifier) {
    val snackBarHostState = remember { SnackbarHostState() }
    val message = stringResource(R.string.saved_to_documents)
    viewModel.saveSnackBarFlow.collectAsEffect {
        snackBarHostState.showSnackbar(
            message = message,
            withDismissAction = true
        )
    }
    SnackbarHost(
        hostState = snackBarHostState,
        modifier = modifier
    ) { snackBarData ->
        Snackbar(
            snackBarData,
            containerColor = GreenBold800, // Green background
            contentColor = Color.White // White text color
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PhotoToPdfScreenLayout(
    uiState: PhotoToPdfScreenUiModel,
    onUriSelected: (Uri) -> Unit = {},
    showRefresh: Boolean = false,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    onSave: (PdfUiModel) -> Unit = {},
    onDeleted: (PdfUiModel) -> Unit = {},
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var previewItem by remember { mutableStateOf<PdfUiModel?>(null) }

    val pickPhotoLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                onUriSelected(uri)
            }
        }

    val cameraCaptureState = rememberCameraCaptureState()

    fun onFabOptionSelected(option: FabOption) {
        when (option) {
            FabOption.PhotoLibrary -> pickPhotoLauncher.launchImage()
            FabOption.Camera -> {}
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {},
        topBar = {
            PhotoToPdfAppBar()
        }, floatingActionButton = {
            PhotoToPdfFAB {
                showBottomSheet = true
            }
        }, content = { paddingValues ->
            PhotoToPdfContent(
                uiState = uiState,
                showRefresh = showRefresh,
                modifier = Modifier.padding(paddingValues),
                onRefresh = onRefresh,
                onItemSelected = { previewItem = it }
            )

            if (showBottomSheet) {
                PhotoToPdfFabModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false },
                    onOptionSelected = {
                        showBottomSheet = false
                        onFabOptionSelected(it)
                    })
            }
            previewItem?.let {
                PhotoToPdfPreviewDialog(
                    item = it,
                    onDismiss = { previewItem = null },
                    onSave = onSave,
                    onDelete = onDeleted,
                )
            }
        })
}
