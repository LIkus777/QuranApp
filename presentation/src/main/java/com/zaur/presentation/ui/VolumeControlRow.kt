package com.zaur.presentation.ui

import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zaur.presentation.R
import kotlin.math.roundToInt

/**
 * @author Zaur
 * @since 22.05.2025
 */

@Composable
fun VolumeControlRow(
    colors: QuranColors,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val audioManager = remember {
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }
    val maxVol = remember { audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) }

    var sliderPos by remember {
        mutableFloatStateOf(
            audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat() / maxVol
        )
    }
    var isMuted by remember { mutableStateOf(false) }
    var prevVolume by remember { mutableFloatStateOf(sliderPos) }

    // Функция для выбора иконки по уровню

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier
    ) {
        // 1) Иконка
        val iconRes = volumeIcon(sliderPos, isMuted)
        Icon(
            painter = painterResource(iconRes),
            contentDescription = if (isMuted) "Unmute" else "Volume",
            modifier = Modifier
                .size(20.dp)
                .rippleClickable {
                    if (!isMuted) {
                        prevVolume = sliderPos
                        sliderPos = 0f
                        audioManager.setStreamVolume(
                            AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI
                        )
                    } else {
                        sliderPos = prevVolume.coerceIn(0f, 1f)
                        audioManager.setStreamVolume(
                            AudioManager.STREAM_MUSIC,
                            (sliderPos * maxVol).roundToInt(),
                            AudioManager.FLAG_SHOW_UI
                        )
                    }
                    isMuted = !isMuted
                })

        Spacer(modifier = Modifier.width(6.dp))

        // 2) Ползунок
        CustomProgressBarSound(
            colors = colors, progress = sliderPos, onProgressChanged = { newProgress ->
                isMuted = false
                sliderPos = newProgress
                val newVol = (newProgress * maxVol).roundToInt()
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC, newVol, AudioManager.FLAG_SHOW_UI
                )
            }, modifier = Modifier
                .height(20.dp)
                .width(150.dp)
        )
    }
}

@Composable
fun volumeIcon(sliderPos: Float, isMuted: Boolean): Int {
    return if (isMuted || sliderPos == 0f) {
        R.drawable.volume_mute_line
    } else {
        when {
            sliderPos <= 0.33f -> R.drawable.volume_down_line
            sliderPos <= 0.66f -> R.drawable.volume_up_line    // средний уровень
            else -> R.drawable.volume_up_line    // высокий тоже
        }
    }
}