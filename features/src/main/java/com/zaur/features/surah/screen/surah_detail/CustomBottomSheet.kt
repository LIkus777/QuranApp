package com.zaur.features.surah.screen.surah_detail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors
import kotlinx.coroutines.launch


/**
 * @author Zaur
 * @since 22.05.2025
 */

@Preview(showBackground = true)
@Composable
fun CustomBottomSheet(
    isVisible: Boolean = true,
    colors: QuranColors = LightThemeColors,
    onDismiss: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = { /* ... */ },
) {
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }

    val offsetY = remember { Animatable(screenHeightPx) }
    var internalVisible by remember { mutableStateOf(isVisible) }

    // SystemUiController из accompanist
    val systemUiController = rememberSystemUiController()
    val overlayAlpha by remember {
        derivedStateOf {
            if (!internalVisible) 0f
            else (1f - offsetY.value / screenHeightPx).coerceIn(0f, 1f) * 0.5f
        }
    }

    // При появлении/скрытии меняем цвет и видимость панелей
    DisposableEffect(internalVisible, overlayAlpha) {
        if (internalVisible) {
            // фон — чёрный с прозрачностью overlayAlpha
            val bgColor = Color.Black.copy(alpha = overlayAlpha)

            // иконки сделаем светлыми (white), то есть darkIcons = false,
            // когда фон тёмный (alpha > 0.5)
            val darkIcons = overlayAlpha < 0.5f

            systemUiController.setStatusBarColor(
                color = bgColor, darkIcons = darkIcons
            )
            systemUiController.setNavigationBarColor(
                color = bgColor, darkIcons = darkIcons
            )
        } else {
            // возвращаем прозрачный фон + тёмные иконки
            systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = true)
            systemUiController.setNavigationBarColor(color = Color.Transparent, darkIcons = true)
        }
        onDispose { }
    }

    // анимация самого BottomSheet-а — без изменений
    val smoothEasing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1.0f)
    val animationSpec = tween<Float>(durationMillis = 400, easing = smoothEasing)

    LaunchedEffect(isVisible) {
        if (isVisible) {
            internalVisible = true
            offsetY.snapTo(screenHeightPx)
            offsetY.animateTo(0f, animationSpec)
        } else {
            offsetY.animateTo(screenHeightPx, animationSpec)
            internalVisible = false
            onDismiss()
        }
    }

    if (internalVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
                // основное затемнение контента
            .background(colors.shadowForActiveBars.copy(alpha = overlayAlpha))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    // клик по фону — скрываем
                    scope.launch {
                        offsetY.animateTo(screenHeightPx, animationSpec)
                        internalVisible = false
                        onDismiss()
                    }
                }) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset { IntOffset(0, offsetY.value.toInt()) }
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        colors.boxBackground, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .navigationBarsPadding()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), content = content
                )
            }
        }
    }
}