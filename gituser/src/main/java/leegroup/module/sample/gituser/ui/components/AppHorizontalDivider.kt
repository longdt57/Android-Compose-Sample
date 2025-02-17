package leegroup.module.sample.gituser.ui.components

import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import leegroup.module.compose.ui.theme.GreySoft800

@Composable
internal fun AppHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = GreySoft800,
) {
    HorizontalDivider(modifier, thickness, color)
}