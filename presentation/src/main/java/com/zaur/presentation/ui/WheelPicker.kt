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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
    val half = visibleCount / 2
    val wheelHeight = itemHeight * visibleCount
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex)

    val flingBehavior = rememberSnapFlingBehavior(
        lazyListState = listState,
        snapPosition = SnapPosition.Center
    )

    // Внутреннее состояние текущего выбранного индекса
    var currentIndex by remember { mutableStateOf(selectedIndex) }

    // Если внешний selectedIndex изменился и отличается от внутреннего - прокрутить
    LaunchedEffect(selectedIndex) {
        if (selectedIndex != currentIndex) {
            listState.animateScrollToItem(selectedIndex)
            currentIndex = selectedIndex
        }
    }

    Box(
        modifier
            .height(wheelHeight)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        // Центральная рамка и градиенты - как у вас были
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
                .height(itemHeight * half)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.White, Color.White.copy(alpha = 0f))
                    )
                )
        )
        Box(
            Modifier
                .fillMaxWidth()
                .height(itemHeight * half)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.White.copy(alpha = 0f), Color.White)
                    )
                )
        )

        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            contentPadding = PaddingValues(vertical = itemHeight * half),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            itemsIndexed(items) { index, item ->
                val isSelected = index == currentIndex
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(itemHeight),
                    contentAlignment = Alignment.Center
                ) {
                    itemContent(item, isSelected)
                }
            }
        }
    }

    // Следим за прокруткой, чтобы обновить currentIndex и вызвать onItemSelected
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }.collect { visibleItems ->
            if (visibleItems.isEmpty()) return@collect

            val center = (listState.layoutInfo.viewportStartOffset + listState.layoutInfo.viewportEndOffset) / 2

            val closest = visibleItems.minByOrNull { item ->
                val itemCenter = item.offset + item.size / 2
                kotlin.math.abs(itemCenter - center)
            }

            closest?.index?.let { newIndex ->
                if (newIndex != currentIndex) {
                    currentIndex = newIndex
                    onItemSelected(newIndex) // сообщаем родителю
                }
            }
        }
    }
}