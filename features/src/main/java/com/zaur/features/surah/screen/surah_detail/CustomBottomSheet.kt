package com.zaur.features.surah.screen.surah_detail

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors
import kotlinx.coroutines.launch


/**
 * @author Zaur
 * @since 22.05.2025
 */

@Preview(showBackground = true)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CustomBottomSheet(
    isVisible: Boolean = true,
    colors: QuranColors = LightThemeColors,
    onDismiss: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }
    val offsetY = remember { Animatable(screenHeightPx) }
    var internalVisible by remember { mutableStateOf(isVisible) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            internalVisible = true
            offsetY.snapTo(screenHeightPx)
            offsetY.animateTo(0f, tween(400, easing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f)))
        } else {
            offsetY.animateTo(
                screenHeightPx,
                tween(400, easing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f))
            )
            internalVisible = false
            onDismiss()
        }
    }

    if (!internalVisible) return

    // Overlay alpha based on sheet position
    val overlayAlpha = (offsetY.value / screenHeightPx).let { 1f - it }.coerceIn(0f, 1f) * 0.5f

    Box(modifier = Modifier.fillMaxSize()) {
        // Background overlay with click-to-dismiss
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.shadowForActiveBars.copy(alpha = overlayAlpha))
                .pointerInput(Unit) {
                    detectTapGestures { tap ->
                        // only dismiss if tapped above sheet
                        val sheetTop = screenHeightPx - offsetY.value
                        if (tap.y < sheetTop) {
                            scope.launch {
                                offsetY.animateTo(
                                    screenHeightPx,
                                    tween(400, easing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f))
                                )
                                internalVisible = false
                                onDismiss()
                            }
                        }
                    }
                })
        // Sheet content, intercept clicks
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset { IntOffset(0, offsetY.value.toInt()) }
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    colors.boxBackground, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .navigationBarsPadding()
                .pointerInput(Unit) { /* consume all touches inside sheet */ }) {
            Column(modifier = Modifier.padding(16.dp), content = content)
        }
    }
}