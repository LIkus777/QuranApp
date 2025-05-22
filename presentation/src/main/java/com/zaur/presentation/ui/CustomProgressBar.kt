package com.zaur.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

/**
 * @author Zaur
 * @since 22.05.2025
 */

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CustomProgressBar(
    colors: QuranColors,
    progress: Float,
    onProgressChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp), contentAlignment = Alignment.CenterStart
    ) {
        val barWidth = constraints.maxWidth.toFloat()
        var dragOffset by remember { mutableStateOf(0f) }

        val actualProgress = progress.coerceIn(0f, 1f)
        val thumbOffsetPx = actualProgress * barWidth

        // Трек
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color.LightGray)
        )

        // Заполненная часть
        Box(
            modifier = Modifier
                .fillMaxWidth(actualProgress)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(colors.buttonPrimary)
        )

        // Ползунок
        Box(modifier = Modifier
            .offset {
                IntOffset((thumbOffsetPx - 6).toInt(), 0)
            }
            .size(12.dp)
            .pointerInput(Unit) {
                detectDragGestures(onDragStart = {
                    dragOffset = thumbOffsetPx
                }, onDrag = { change, dragAmount ->
                    change.consume()
                    dragOffset += dragAmount.x
                    val newProgress = (dragOffset / barWidth).coerceIn(0f, 1f)
                    onProgressChanged(newProgress)
                })
            }
            .background(colors.buttonTertiary, shape = CircleShape)
            .border(0.5.dp, colors.cardBackground, CircleShape))
    }
}