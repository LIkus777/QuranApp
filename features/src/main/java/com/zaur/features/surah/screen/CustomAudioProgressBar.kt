package com.zaur.features.surah.screen

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zaur.features.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun CustomAudioProgressBarWidget(
    audioUrl: String,
    restartAudio: Boolean,
    onComplete: () -> Unit
) {
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    // Перезапускаем при изменении url или флага перезапуска
    LaunchedEffect(audioUrl to restartAudio) {
        withContext(Dispatchers.IO) {
            mediaPlayer?.let { player ->
                if (player.isPlaying) {
                    player.stop()
                }
                player.release()
            }
            mediaPlayer = null
            try {
                val player = MediaPlayer().apply {
                    setDataSource(audioUrl)
                    prepareAsync()
                    setOnPreparedListener {
                        it.start() // Автозапуск
                        isPlaying = true
                    }
                    setOnCompletionListener {
                        isPlaying = false
                        onComplete()
                    }
                }
                mediaPlayer = player
            } catch (e: Exception) {
                Log.e("TAG", "CustomAudioProgressBarWidget: $e")
            }
        }
    }

    // Кнопка Play/Pause
    IconButton(onClick = {
        mediaPlayer?.let { player ->
            if (isPlaying) {
                player.pause()
            } else {
                player.start()
            }
        }
        isPlaying = !isPlaying
    }) {
        Icon(
            painter = if (isPlaying) painterResource(R.drawable.pause) else painterResource(R.drawable.play),
            contentDescription = "Play/Pause"
        )
    }

    // Освобождаем ресурсы при выходе
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}

@Composable
fun CustomAudioProgressBar(
    duration: Float, progress: Float
) {
    // Полоса прогресса
    Box(
        modifier = Modifier
            .height(4.dp)
            .fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.3f)) // Фон
    ) {
        Box(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth(fraction = if (duration > 0) progress / duration else 0f) // Прогресс
                .background(Color.Green)
        )
    }
}
