package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaur.features.surah.screen.surah_detail.CustomBottomSheet
import com.zaur.presentation.R
import com.zaur.presentation.ui.CustomProgressBar
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors
import com.zaur.presentation.ui.VolumeControlRow
import com.zaur.presentation.ui.rippleClickable


/**
 * @author Zaur
 * @since 22.05.2025
 */

@Composable
fun PlayerDialog(
    soundDuration: Long = 0L,
    soundPosition: Long = 0L,
    showSheet: Boolean = true,
    isPlaying: Boolean = false,
    colors: QuranColors = LightThemeColors,
    surahName: String = "",
    ayahNumber: Int = 0,
    surahNumber: Int = 0,
    reciterName: String = "",
    onPlayClicked: () -> Unit = {},
    onNextAyahClicked: () -> Unit = {},
    onPreviousAyahClicked: () -> Unit = {},
    onNextSurahClicked: () -> Unit = {},
    onPreviousSurahClicked: () -> Unit = {},
    onSeekRequested: (newPositionMs: Long) -> Unit = {},
    onSurahAndAyahClicked: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    // 1) Никогда не делим на ноль
    val safeDuration = soundDuration.coerceAtLeast(1L)

    // 2) Прогресс от плеера 0f..1f
    val rawProgress = (soundPosition / safeDuration.toFloat()).coerceIn(0f,1f)

    // 4) Формат времени
    fun fmt(ms: Long): String {
        val total = ms.coerceIn(0L, safeDuration)
        val s = total / 1000
        return "%02d:%02d".format(s/60, s%60)
    }
    val displayPos = (rawProgress * safeDuration).toLong()
    val remaining = safeDuration - displayPos

    CustomBottomSheet(
        colors = colors, isVisible = showSheet, onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .background(colors.boxBackground),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ————— Прогресс-бар —————
            CustomProgressBar(
                colors = colors,
                progress = rawProgress,
                durationMs = safeDuration,
                onSeekRequested = onSeekRequested,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            // Время
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(fmt(displayPos), color = colors.textSecondary, fontSize = 12.sp)
                Text("-${fmt(remaining)}", color = colors.textSecondary, fontSize = 12.sp)
            }

            // Сура и чтец в одной колонке
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.rippleClickable { /* действие */ }) {
                    Text(
                        text = "$surahNumber. $surahName, аят ${if (ayahNumber != 0) ayahNumber else ""}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = colors.textPrimary,
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "К текущему аяту",
                        tint = colors.textPrimary
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.rippleClickable(onClick = {})
                ) {
                    Text(
                        text = reciterName, color = colors.textSecondary, fontSize = 14.sp
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Выбрать чтеца",
                        tint = colors.textSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Управление
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val iconModifier = Modifier.size(32.dp)

                Icon(
                    painter = painterResource(R.drawable.skip_back_mini_line),
                    contentDescription = null,
                    modifier = iconModifier.rippleClickable(onClick = { onPreviousSurahClicked() }),
                )

                Icon(
                    painter = painterResource(R.drawable.rewind_line),
                    contentDescription = null,
                    modifier = iconModifier.rippleClickable(onClick = { onPreviousAyahClicked() }),
                )

                Icon(
                    painter = if (isPlaying) painterResource(R.drawable.pause_line)
                    else painterResource(R.drawable.play_line),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .rippleClickable(onClick = {
                            onPlayClicked()
                        }),
                )

                Icon(
                    painter = painterResource(R.drawable.speed_line),
                    contentDescription = null,
                    modifier = iconModifier.rippleClickable(onClick = { onNextAyahClicked() }),
                )

                Icon(
                    painter = painterResource(R.drawable.skip_forward_mini_line),
                    contentDescription = null,
                    modifier = iconModifier.rippleClickable(onClick = { onNextSurahClicked() }),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Громкость и повтор/скорость
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Громкость
                VolumeControlRow(colors)

                // Повтор + скорость
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.repeat_2_line),
                        contentDescription = "Repeat",
                        modifier = Modifier
                            .size(20.dp)
                            .rippleClickable(onClick = {})
                    )
                    Text(
                        text = "1.0x",
                        color = colors.textPrimary,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .rippleClickable(onClick = {})
                    )
                }
            }
        }
    }
}