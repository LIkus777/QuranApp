package com.zaur.features.surah.screen.surah_choose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaur.presentation.ui.WheelPicker

/**
 * @author Zaur
 * @since 27.05.2025
 */

@Composable
fun DualWheelPickerView(
    leftItems: List<String>,
    rightItems: List<Int>,
    selectedLeftIndex: Int,
    selectedRightIndex: Int,
    onLeftSelected: (Int) -> Unit,
    onRightSelected: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        WheelPicker(
            items = leftItems,
            selectedIndex = selectedLeftIndex,
            modifier = Modifier.weight(1f),
            visibleCount = 6,
            itemHeight = 48.dp,
            itemContent = { item, isSelected ->
                val scale = if (isSelected) 1.25f else 1f
                val alpha = if (isSelected) 1f else 0.4f

                Text(
                    text = item,
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
                onLeftSelected(index)
            }
        )

        Spacer(modifier = Modifier.width(16.dp))

        WheelPicker(
            items = rightItems,
            selectedIndex = selectedRightIndex,
            modifier = Modifier.width(80.dp),
            visibleCount = 6,
            itemHeight = 48.dp,
            itemContent = { item, isSelected ->
                val scale = if (isSelected) 1.25f else 1f
                val alpha = if (isSelected) 1f else 0.4f

                Text(
                    text = item.toString(),
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
                onRightSelected(index)
            }
        )
    }
}