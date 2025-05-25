package com.zaur.presentation.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.ContentObserver
import android.media.AudioManager
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import android.provider.Settings
import androidx.compose.runtime.LaunchedEffect

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
        mutableStateOf(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) / maxVol.toFloat())
    }
    var isMuted by remember { mutableStateOf(sliderPos == 0f) }
    var prevVolume by remember { mutableStateOf(sliderPos) }

    // BroadcastReceiver для отслеживания аппаратных кнопок громкости
    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                if (intent.action == "android.media.VOLUME_CHANGED_ACTION") {
                    val streamType = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1)
                    if (streamType == AudioManager.STREAM_MUSIC) {
                        val newVol = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0)
                        val newPos = newVol / maxVol.toFloat()
                        sliderPos = newPos.coerceIn(0f, 1f)
                        isMuted = sliderPos == 0f
                    }
                }
            }
        }
        val filter = IntentFilter("android.media.VOLUME_CHANGED_ACTION")
        context.registerReceiver(receiver, filter)
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // Иконка
        val iconRes = volumeIcon(sliderPos, isMuted)
        Icon(
            painter = painterResource(iconRes),
            contentDescription = if (isMuted) "Unmute" else "Volume",
            modifier = Modifier
                .size(20.dp)
                .rippleClickable {
                    if (!isMuted) {
                        prevVolume = sliderPos
                        audioManager.setStreamVolume(
                            AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI
                        )
                        sliderPos = 0f
                    } else {
                        sliderPos = prevVolume.coerceIn(0f, 1f)
                        audioManager.setStreamVolume(
                            AudioManager.STREAM_MUSIC,
                            (sliderPos * maxVol).roundToInt(),
                            AudioManager.FLAG_SHOW_UI
                        )
                    }
                    isMuted = !isMuted
                }
        )

        Spacer(Modifier.width(6.dp))

        // Ваш слайдер
        CustomProgressBarSound(
            colors = colors,
            progress = sliderPos,
            onProgressChanged = { newProgress ->
                isMuted = false
                sliderPos = newProgress
                val newVol = (newProgress * maxVol).roundToInt()
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC, newVol, AudioManager.FLAG_SHOW_UI
                )
            },
            modifier = Modifier
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