package com.zaur.presentation.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.roundToInt


/**
 * @author Zaur
 * @since 13.06.2025
 */

@Preview(showBackground = true)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CustomProgressBarWithBuffer(
    colors: QuranColors = LightThemeColors,
    startProgress: Float = 0.5f,          // played: 0f..1f
    endProgress: Float = 0.7f,    // buffered: 0f..1f
    onRangeChange: (Float, Float) -> Unit = { _, _ ->},
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier.height(24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        val density = LocalDensity.current
        val widthPx = with(density) { maxWidth.toPx() }
        val thumbRadius = with(density) { 6.dp.toPx() }

        // mutable state for drag offsets
        var startPx by remember { mutableStateOf(startProgress.coerceIn(0f, 1f) * widthPx) }
        var endPx by remember { mutableStateOf(endProgress.coerceIn(0f, 1f) * widthPx) }
        var draggingStart by remember { mutableStateOf(false) }
        var draggingEnd by remember { mutableStateOf(false) }

        // Gesture detector over full width
        Box(
            Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            val x = offset.x
                            // decide which thumb to drag based on proximity
                            if (abs(x - startPx) < abs(x - endPx)) {
                                draggingStart = true
                            } else {
                                draggingEnd = true
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consumeAllChanges()
                            if (draggingStart) {
                                startPx = (startPx + dragAmount.x).coerceIn(0f, endPx).coerceIn(0f, widthPx)
                            } else if (draggingEnd) {
                                endPx = (endPx + dragAmount.x).coerceIn(startPx, widthPx)
                            }
                            onRangeChange(startPx / widthPx, endPx / widthPx)
                        },
                        onDragEnd = {
                            draggingStart = false
                            draggingEnd = false
                        },
                        onDragCancel = {
                            draggingStart = false
                            draggingEnd = false
                        }
                    )
                }
        )

        // background track
        Box(
            Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color.LightGray)
        )
        // selected range fill
        Box(
            Modifier
                .offset { IntOffset(startPx.roundToInt(), 0) }
                .width((endPx - startPx).dp / density.density)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(colors.buttonPrimary)
        )
        // start thumb
        Box(
            Modifier
                .offset { IntOffset((startPx - thumbRadius).roundToInt(), 0) }
                .size(12.dp)
                .background(colors.buttonTertiary, shape = CircleShape)
                .border(0.5.dp, colors.cardBackground, CircleShape)
        )
        // end thumb
        Box(
            Modifier
                .offset { IntOffset((endPx - thumbRadius).roundToInt(), 0) }
                .size(12.dp)
                .background(colors.buttonTertiary, shape = CircleShape)
                .border(0.5.dp, colors.cardBackground, CircleShape)
        )
    }
}