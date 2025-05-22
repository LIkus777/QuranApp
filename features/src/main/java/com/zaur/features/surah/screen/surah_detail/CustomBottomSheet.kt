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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.zaur.presentation.ui.CustomBottomSheetDefaultPreview
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
    content: @Composable ColumnScope.() -> Unit = { CustomBottomSheetDefaultPreview() },
) {
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }

    val offsetY = remember { Animatable(screenHeightPx) }
    var internalVisible by remember { mutableStateOf(isVisible) }
    remember { MutableInteractionSource() }

    // Плавная iOS-подобная анимация
    val smoothEasing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1.0f)
    val animationSpec = tween<Float>(durationMillis = 400, easing = smoothEasing)

    LaunchedEffect(isVisible) {
        if (isVisible) {
            internalVisible = true
            offsetY.snapTo(screenHeightPx)
            offsetY.animateTo(
                targetValue = 0f, animationSpec = animationSpec
            )
        } else {
            offsetY.animateTo(
                targetValue = screenHeightPx, animationSpec = animationSpec
            )
            internalVisible = false
            onDismiss()
        }
    }

    if (internalVisible) {
        val overlayAlpha = (1f - offsetY.value / screenHeightPx).coerceIn(0f, 1f) * 0.5f

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.shadowForActiveBars.copy(alpha = overlayAlpha))
                .clickable( // Блокируем клики под затемнением
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    scope.launch {
                        offsetY.animateTo(
                            targetValue = screenHeightPx, animationSpec = animationSpec
                        )
                        internalVisible = false
                        onDismiss()
                    }
                }) {
            // Сам BottomSheet
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset { IntOffset(x = 0, y = offsetY.value.toInt()) }
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = colors.boxBackground,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .navigationBarsPadding() // Учитываем системные кнопки снизу
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    content = content
                )
            }
        }
    }
}