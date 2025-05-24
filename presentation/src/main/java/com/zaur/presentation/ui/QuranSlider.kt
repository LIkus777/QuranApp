package com.zaur.presentation.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * @author Zaur
 * @since 24.05.2025
 */

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranSlider(
    progress: Float = 1f,
    onProgressChanged: (Float) -> Unit = {},
    modifier: Modifier = Modifier,
    colors: QuranColors = LightThemeColors,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isDragging by interactionSource.collectIsDraggedAsState()

    val thumbRadius = if (isDragging) 8.dp else 6.dp

    val activeColor = colors.progressCircle
    val inactiveColor = colors.progressCircle.copy(alpha = 0.24f)
    val thumbColorAnimated by animateColorAsState(
        targetValue = if (isDragging) activeColor else White, label = "ThumbColor"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val width = size.width.toFloat()
                    val newProgress = (offset.x / width).coerceIn(0f, 1f)
                    onProgressChanged(newProgress)
                }
            }) {
        // Custom Track
        val trackHeight = 4.dp
        Box(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(trackHeight)
                .background(inactiveColor, RoundedCornerShape(trackHeight))
        )

        Box(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth(fraction = progress)
                .height(trackHeight)
                .background(activeColor, RoundedCornerShape(trackHeight))
        )

        // Slider with transparent track (we're drawing it ourselves)
        Slider(
            value = progress,
            onValueChange = onProgressChanged,
            valueRange = 0f..1f,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            colors = SliderDefaults.colors(
                thumbColor = thumbColorAnimated,
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent,
            ),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(thumbRadius * 2)
                        .background(thumbColorAnimated, CircleShape)
                        .border(
                            width = 1.dp, color = White.copy(alpha = 0.6f), shape = CircleShape
                        )
                )
            })
    }
}