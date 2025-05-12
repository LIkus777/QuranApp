package com.zaur.features.surah.screen.surah_detail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.zaur.presentation.ui.CustomBottomSheetDefaultPreview
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors
import com.zaur.presentation.ui.getNavBarHeightInPx
import kotlinx.coroutines.launch

/**
* @author Zaur
* @since 2025-05-12
*/

@Preview(showBackground = true)
@Composable
fun CustomBottomSheet(
    isVisible: Boolean = true,
    alignment: Alignment = Alignment.BottomStart,
    colors: QuranColors = LightThemeColors,
    onDismiss: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = { CustomBottomSheetDefaultPreview() }
) {
    val scale = remember { Animatable(0.7f) }
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    val interactionSource = remember { MutableInteractionSource() }
    val isAnimating = remember { mutableStateOf(false) } // Флаг блокировки клика

    val densityCurrent = LocalDensity.current.density
    val context = LocalContext.current
    val navBarHeightInDp = getNavBarHeightInPx(context) / densityCurrent

    val cornerShape = when (alignment) {
        Alignment.BottomCenter -> RoundedCornerShape(32.dp)
        Alignment.BottomStart -> RoundedCornerShape(
            topStart = 32.dp,
            topEnd = 32.dp,
            bottomStart = 0.dp,
            bottomEnd = 32.dp
        )

        else -> RoundedCornerShape(
            topStart = 32.dp,
            topEnd = 32.dp,
            bottomStart = 32.dp,
            bottomEnd = 0.dp
        )
    }

    // Управляем анимацией высоты BottomSheet
    LaunchedEffect(isVisible) {
        if (isVisible) {
            launch { scale.animateTo(1f, animationSpec = tween(150)) }
        } else {
            launch { scale.animateTo(0.7f, animationSpec = tween(150)) }
            onDismiss() // Закрываем BottomSheet
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isVisible) colors.shadowForActiveBars else Color.Transparent) // Фон при открытом BottomSheet
            .clickable(
                enabled = isVisible && !isAnimating.value,
                interactionSource = interactionSource,
                indication = null // Отключаем анимацию клика
            ) { // Закрытие по клику на темную область
                scope.launch {
                    isAnimating.value = true
                    scale.animateTo(0.7f, animationSpec = tween(150))
                    isAnimating.value = false
                    onDismiss()
                }
            }) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f) // Ограничение ширины
            .heightIn(max = (density.density * 400).dp) // Ограничение по высоте
            .align(BiasAlignment(0f, 0.6f))
                .scale(scale.value)
                .graphicsLayer {
                    scaleX = if (isVisible) 1f else 0.7f
                    scaleY = if (isVisible) 1f else 0.7f
                }
                .offset {
                    // Важно, чтобы позиционировать элемент чуть выше BottomAppBar
                    // Вычисляем смещение, которое будет учитываться относительно высоты панели навигации
                    IntOffset(
                        0,
                        -(navBarHeightInDp.dp.toPx() + 10.dp.toPx()).toInt() // Это сдвиг на 10dp выше
                    )
                }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f) // Ограничение ширины
                    .align(Alignment.Center)
                    .background(colors.boxBackground, shape = cornerShape)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.Center)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    content()
                }
            }
            // Стрелочка снизу
            if (alignment == Alignment.BottomCenter) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = (22).dp)
                        .size(24.dp)
                        .background(
                            color = colors.boxBackground,
                            shape = roundedConcaveTriangle()
                        )
                )
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(y = (23).dp)
                        .padding(start = 18.48.dp)
                        .size(24.dp)
                        .background(
                            color = colors.boxBackground,
                            shape = roundedConcaveTriangle()
                        )
                )
            }

        }
    }
}

// Дополнительный компонент для создания треугольника
@Composable
fun roundedConcaveTriangle(): Shape {
    return object : Shape {
        override fun createOutline(
            size: Size, layoutDirection: LayoutDirection, density: Density
        ): Outline {
            val path = Path().apply {
                moveTo(size.width / 2, size.height) // Вершина треугольника

                // Левая сторона с вогнутой дугой
                quadraticTo(
                    size.width * 0.05f, size.height * 0.5f, // Точка сгиба
                    0f, 0f // Нижний левый угол
                )

                // Верхняя сторона
                quadraticTo(
                    size.width / 2, size.height * -0.1f, // Немного вогнутая вверх
                    size.width, 0f // Нижний правый угол
                )

                // Правая сторона с вогнутой дугой
                quadraticTo(
                    size.width * 0.95f, size.height * 0.5f, // Точка сгиба
                    size.width / 2, size.height // Возвращаемся к вершине
                )

                close()
            }
            return Outline.Generic(path)
        }
    }
}