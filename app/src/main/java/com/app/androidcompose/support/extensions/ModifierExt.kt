package com.app.androidcompose.support.extensions

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.widthForFixedItems(numOfItems: Float, totalSpacing: Int): Modifier {
    val itemWidth =
        ((LocalConfiguration.current.screenWidthDp.dp - totalSpacing.dp) / numOfItems.dp)
    return width(itemWidth.dp)
}

@Composable
fun Modifier.verticalScrollBar(
    state: ScrollState,
    scrollbarWidth: Dp = 4.dp,
    color: Color = Color.Gray,
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    return drawWithContent {
        drawContent()

        if (alpha > 0f) {
            val contentHeight = size.height
            val maxScrollOffset = state.maxValue

            if (maxScrollOffset <= 0) return@drawWithContent

            val scrollableHeight =
                contentHeight * (contentHeight / (maxScrollOffset.toFloat() + contentHeight))

            val scrollOffset = state.value
            val scrollbarTop =
                scrollOffset * (contentHeight - scrollableHeight) / maxScrollOffset.toFloat()

            val scrollbarLeft = size.width - scrollbarWidth.toPx()

            drawRect(
                color = color,
                size = Size(scrollbarWidth.toPx(), scrollableHeight),
                topLeft = Offset(scrollbarLeft, scrollbarTop),
                alpha = alpha
            )
        }
    }
}

@Composable
fun Modifier.verticalScrollbar(
    state: LazyGridState,
    scrollbarWidth: Dp = 4.dp,
    color: Color = Color.Gray,
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alphaAnimation: Float by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    val firstVisibleItem by remember {
        derivedStateOf {
            state.layoutInfo.visibleItemsInfo.firstOrNull {
                it.index == state.firstVisibleItemIndex
            }
        }
    }

    val numOfItemsInRow by remember {
        derivedStateOf {
            state.layoutInfo.visibleItemsInfo.let { item ->
                if (item.isEmpty()) 1 else item.maxOf { it.column } + 1
            }
        }
    }

    val totalHeight by remember {
        derivedStateOf {
            firstVisibleItem?.let { firstVisibleItem ->
                val layoutInfo = state.layoutInfo
                val totalRows =
                    (state.layoutInfo.totalItemsCount.toFloat() / numOfItemsInRow.toFloat()).ceilToInt()
                totalRows * firstVisibleItem.size.height + layoutInfo.beforeContentPadding + layoutInfo.afterContentPadding + layoutInfo.mainAxisItemSpacing * (totalRows - 1)
            } ?: run {
                0
            }
        }
    }

    val thumbHeightRatio by remember {
        derivedStateOf {
            if (totalHeight == 0) {
                0f
            } else {
                state.layoutInfo.viewportSize.height.toFloat() / totalHeight.toFloat()
            }
        }
    }

    val scrollPercentage by remember {
        derivedStateOf {
            if (totalHeight == 0) {
                0f
            } else {
                firstVisibleItem?.let { firstVisibleItem ->
                    val scrollOffset =
                        firstVisibleItem.index / numOfItemsInRow * (firstVisibleItem.size.height + state.layoutInfo.mainAxisItemSpacing) + state.firstVisibleItemScrollOffset
                    scrollOffset.toFloat() / (totalHeight - state.layoutInfo.viewportSize.height).toFloat()
                } ?: run { 0f }
            }
        }
    }

    return drawWithContent {
        drawContent()
        if (alphaAnimation > 0.0f) {
            drawRoundRect(
                color = color,
                topLeft = Offset(
                    size.width - scrollbarWidth.toPx(),
                    scrollPercentage * size.height * (1 - thumbHeightRatio)
                ),
                size = Size(scrollbarWidth.toPx(), thumbHeightRatio * size.height),
                alpha = alphaAnimation,
            )
        }
    }
}