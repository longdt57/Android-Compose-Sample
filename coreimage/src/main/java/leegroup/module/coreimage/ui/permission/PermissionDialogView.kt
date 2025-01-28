package leegroup.module.coreimage.ui.permission

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import leegroup.module.compose.support.extensions.openAppSetting
import leegroup.module.coreimage.R

@Composable
fun PermissionDialogView(
    title: String = "",
    onDismissRequest: () -> Unit = {},
) {
    val context = LocalContext.current
    AlertDialog(
        text = {
            Text(text = title, textAlign = TextAlign.Center)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                    context.openAppSetting()
                }
            ) {
                Text(stringResource(R.string.go_to_setting))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.close))
            }
        }
    )
}

@Preview
@Composable
private fun PermissionDialogViewPreview() {
    MaterialTheme {
        PermissionDialogView("Go to setting")
    }
}