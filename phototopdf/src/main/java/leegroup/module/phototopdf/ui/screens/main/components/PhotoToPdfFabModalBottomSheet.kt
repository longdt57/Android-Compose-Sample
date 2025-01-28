package leegroup.module.phototopdf.ui.screens.main.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import leegroup.module.phototopdf.R

internal enum class FabOption(@StringRes val label: Int, @DrawableRes val icon: Int) {
    Camera(
        R.string.fab_take_photo,
        R.drawable.ic_camera_outline_32dp
    ),
    PhotoLibrary(R.string.photo_library, R.drawable.ic_photo_outline_32dp),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PhotoToPdfFabModalBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissRequest: () -> Unit = {},
    onOptionSelected: (FabOption) -> Unit = {},
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column(modifier = Modifier.padding(bottom = 24.dp)) {
            FabOption.entries.forEach {
                PhotoToPdfFabMenuItem(it, onOptionSelected)
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@Composable
internal fun PhotoToPdfFabMenuItem(item: FabOption, onOptionSelected: (FabOption) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onOptionSelected(item) }
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(item.icon),
            contentDescription = "",
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = stringResource(item.label),
            modifier = Modifier.padding(start = 8.dp),
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewPhotoToPdfFabMenu() {
    PhotoToPdfFabModalBottomSheet()
}
