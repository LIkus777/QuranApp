package com.zaur.features.surah.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.zaur.features.R
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors

fun getNavBarHeightInPx(context: Context): Int {
    // Получение высоты навигационной панели через ресурсы
    val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    val navBarHeightInPx = if (resourceId > 0) {
        context.resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
    return navBarHeightInPx
}

@Preview(showBackground = true)
@Composable
fun ChapterBottomBar(
    colors: QuranColors = LightThemeColors,
    runAudio: Boolean = false,
    playWholeChapter: Boolean = false,
    audioUrl: String = "",
    restartAudio: Boolean = false,
    showReciterDialog: (Boolean) -> Unit = {},
    showSettings: () -> Unit = {},
    onAudioEnded: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    scrollToAudioElement: () -> Unit = {},
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var player by remember { mutableStateOf<ExoPlayer?>(null) }

    // Навигация и настройка кнопок
    BottomAppBar(
        containerColor = colors.appBarColor,
        contentColor = colors.iconColor,
        modifier = Modifier.height(56.dp)
    ) {
        IconButton(onClick = { showSettings() }) {
            Icon(Icons.Default.Settings, contentDescription = "Настройки")
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { showReciterDialog(true) }) {
            Icon(
                painter = painterResource(R.drawable.letter_case),
                contentDescription = "Размер шрифтов",
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка Play/Pause
        IconButton(onClick = {
            if (playWholeChapter) {
                if (player == null || runAudio == false) {
                    // Воспроизведение всей суры
                    onPlayClick()
                } else {
                    if (isPlaying) {
                        player?.pause()
                        isPlaying = false
                    } else {
                        player?.play()
                        isPlaying = true
                    }
                }
            } else {
                handlePlayPauseForChapter(isPlaying, player)  // Воспроизведение одного файла
                isPlaying = !isPlaying
            }
        }) {
            Icon(
                painter = if (isPlaying) painterResource(R.drawable.pause) else painterResource(R.drawable.play),
                contentDescription = "Play/Pause"
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { /* TODO: Избранное */ }) {
            Icon(Icons.Default.Favorite, contentDescription = "Избранное")
        }
    }

    // LaunchedEffect для обработки состояния воспроизведения
    LaunchedEffect(audioUrl, restartAudio, runAudio) {
        if (runAudio) {
            player?.release()
            try {
                val exoPlayer = ExoPlayer.Builder(context).build().apply {
                    val mediaItem = MediaItem.fromUri(audioUrl.toUri())
                    setMediaItem(mediaItem)
                    setAudioAttributes(
                        AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
                            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).build(), true
                    )
                    setHandleAudioBecomingNoisy(true)

                    addListener(object : Player.Listener {
                        override fun onPlaybackStateChanged(state: Int) {
                            Log.i("TAGG", "Player state: $state")
                            if (state == ExoPlayer.STATE_ENDED) {
                                onAudioEnded()
                                isPlaying = false
                            }
                        }
                    })

                    prepare()
                    playWhenReady = true
                }
                player = exoPlayer
                isPlaying = true

                // Прокручиваем к элементу после начала воспроизведения
                scrollToAudioElement()
            } catch (e: Exception) {
                Log.e("TAGG", "Error in initializing audio: $e")
            }
        }
    }

    // Освобождаем ресурсы при выходе
    DisposableEffect(Unit) {
        onDispose {
            player?.release()
            player = null
        }
    }
}

private fun handlePlayPauseForChapter(isPlaying: Boolean, player: ExoPlayer?) {
    if (player != null) {
        if (isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }
}

@Composable
fun CustomBottomSheetDefaultPreview() {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center) // Выравниваем по центру
                .padding(horizontal = 16.dp) // Дополнительные отступы по бокам
        ) {
            Text("Настройки", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Чтец: ", fontSize = 16.sp, modifier = Modifier
                    //.clickable { showReciterDialog(true) }
                    .padding(top = 8.dp, bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Тема:")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = false, onCheckedChange = { /*onThemeChange(it)*/ })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Размер арабского шрифта")
            Slider(
                value = 1f, onValueChange = { }, valueRange = 14f..48f
            )

            Text("Размер русского шрифта")
            Slider(
                value = 1f, onValueChange = { }, valueRange = 14f..48f
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Button(onClick = { /*onShowArabicChange(!showArabic)*/ }) {
                    Text(if (false) "Скрыть арабский" else "Показать арабский")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { /*onShowRussianChange(!showRussian)*/ }) {
                    Text(if (false) "Скрыть русский" else "Показать русский")
                }
            }
        }
    }
}