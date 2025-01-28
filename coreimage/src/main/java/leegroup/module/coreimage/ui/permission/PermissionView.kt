package leegroup.module.coreimage.ui.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.launch
import leegroup.module.compose.support.extensions.ObserveLifecycleEvent
import leegroup.module.coreimage.permission.PermissionChecker

@Composable
fun PermissionView(
    permissionChecker: PermissionChecker,
    grantAccessTitle: String,
    grantAccessText: String,
    settingText: String,
    onGranted: @Composable () -> Unit,
) {
    var isPermissionGranted: Boolean by rememberSaveable { mutableStateOf(permissionChecker.isPermissionGranted()) }
    var showPermissionSettingDialog by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val recheckPermission = {
        isPermissionGranted = permissionChecker.isPermissionGranted()
    }

    val requestPermission = {
        permissionChecker.requestPermission(
            arePermissionsGranted = {
                coroutineScope.launch {
                    isPermissionGranted = it
                }
            },
            onPermissionPermanentlyDenied = {
                coroutineScope.launch {
                    showPermissionSettingDialog = true
                }
            })
    }

    if (isPermissionGranted) {
        onGranted()
    } else {
        GrantAccessScreen(
            title = grantAccessTitle,
            message = grantAccessText,
        ) {
            requestPermission()
        }
    }

    if (showPermissionSettingDialog) {
        PermissionDialogView(settingText) {
            showPermissionSettingDialog = false
        }
    }

    ObserveLifecycleEvent { event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> recheckPermission()
            else -> {}
        }
    }
}