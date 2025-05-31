package com.zaur.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.math.abs

/**
 * @author Zaur
 * @since 27.05.2025
 */

@Composable
fun <T> WheelPicker(
    items: List<T>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    visibleCount: Int = 6,
    itemHeight: Dp = 48.dp,
    itemContent: @Composable (item: T, isSelected: Boolean) -> Unit,
    onItemSelected: (index: Int) -> Unit,
) {
    val density = LocalDensity.current
    val itemHeightPx = with(density) { itemHeight.roundToPx() }
    val contentPaddingPx = itemHeightPx * (visibleCount / 2)
    val offsetToCenter = itemHeightPx / 2

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = selectedIndex,
        initialFirstVisibleItemScrollOffset = offsetToCenter
    )

    var currentIndex by remember { mutableStateOf(selectedIndex) }

    LaunchedEffect(selectedIndex) {
        if (selectedIndex != currentIndex) {
            currentIndex = selectedIndex
            listState.scrollToItem(selectedIndex, offsetToCenter)
        }
    }

    Box(
        modifier
            .height(with(density) { (itemHeightPx * visibleCount).toDp() })
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .align(Alignment.Center)
                .padding(horizontal = 8.dp)
                .background(Color(0xFFF0F0F0), RoundedCornerShape(10.dp))
        )
        Box(
            Modifier
                .fillMaxWidth()
                .height(itemHeight * (visibleCount / 2))
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.White, Color.Transparent)
                    )
                )
        )
        Box(
            Modifier
                .fillMaxWidth()
                .height(itemHeight * (visibleCount / 2))
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.White)
                    )
                )
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            flingBehavior = rememberSnapFlingBehavior(
                lazyListState = listState, snapPosition = SnapPosition.Center
            ),
            contentPadding = PaddingValues(vertical = itemHeight * (visibleCount / 2)),
            verticalArrangement = Arrangement.Top
        ) {
            itemsIndexed(items) { index, item ->
                val isSelected = index == currentIndex
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(itemHeight), contentAlignment = Alignment.Center
                ) {
                    itemContent(item, isSelected)
                }
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }.collect { visibleItems ->
                if (visibleItems.isEmpty()) return@collect
                val viewportCenter =
                    (listState.layoutInfo.viewportStartOffset + listState.layoutInfo.viewportEndOffset) / 2
                val closest = visibleItems.minByOrNull { info ->
                    val itemCenter = info.offset + (info.size / 2)
                    kotlin.math.abs(itemCenter - viewportCenter)
                }?.index
                closest?.let { newIndex ->
                    if (newIndex != currentIndex) {
                        currentIndex = newIndex
                        onItemSelected(newIndex)
                    }
                }
            }
    }
}