package leegroup.module.phototopdf.ui.screens.main.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
internal fun PhotoToPdfFAB(onFabClick: () -> Unit = {}) {
    FloatingActionButton(
        onClick = onFabClick,
        content = { Icon(Icons.Filled.Add, contentDescription = "Add") },
        shape = CircleShape,
    )
}