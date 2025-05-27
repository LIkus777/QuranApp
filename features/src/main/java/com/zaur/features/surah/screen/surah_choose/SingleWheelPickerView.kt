package com.zaur.features.surah.screen.surah_choose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaur.presentation.ui.WheelPicker

/**
 * @author Zaur
 * @since 27.05.2025
 */

@Composable
fun SingleWheelPickerView(
    itemName: String = "",
    items: List<Int> = emptyList(),
    modifier: Modifier = Modifier,
    visibleCount: Int = 6,
    itemHeight: Dp = 48.dp,
    onItemSelected: (Int) -> Unit = {},
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    WheelPicker(
        items = items,
        selectedIndex = selectedIndex,
        modifier = modifier,
        visibleCount = visibleCount,
        itemHeight = itemHeight,
        itemContent = { item, isSelected ->
            val scale = if (isSelected) 1.25f else 1f
            val alpha = if (isSelected) 1f else 0.4f

            Text(
                text = "$itemName $item",
                modifier = Modifier.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                },
                fontSize = 18.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = Color.Black
            )
        },
        onItemSelected = { index ->
            selectedIndex = index
            onItemSelected(items[index])
        }
    )
}