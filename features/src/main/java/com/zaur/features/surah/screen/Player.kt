package com.zaur.features.surah.screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import com.zaur.features.surah.ui_state.aqc.SurahDetailState
import com.zaur.features.surah.ui_state.aqc.SurahDetailStateCallback
import com.zaur.features.surah.viewmodel.QuranAudioVmCallback

interface Player {

    fun player(audioUrl: String): ExoPlayer
    fun setIsPlaying(boolean: Boolean)
    fun onPlayClicked()
    fun onAudioEnded(surahDetailStateData: SurahDetailState, audioState: QuranAudioAqcUIState)

    fun setSurahDetailStateCallback(callback: SurahDetailStateCallback)
    fun setQuranAudioVmCallback(callback: QuranAudioVmCallback)

    class Base(
        private val context: Context,
        private val surahDetailStateData: SurahDetailState,
        private val audioState: QuranAudioAqcUIState,
    ) : Player {

        private var currentExoPlayer: ExoPlayer? = null

        private var surahDetailStateCallback: SurahDetailStateCallback? = null
        private var quranAudioVmCallback: QuranAudioVmCallback? = null

        private var isPlaying = mutableStateOf(false)

        private val onPlayClick = {
            if (!surahDetailStateData.isAudioPlaying) {
                val chaptersAudio = audioState.chaptersAudioFile
                if (chaptersAudio != null) {
                    val ayahs = chaptersAudio.chapterAudio.ayahs
                    if (ayahs.isNotEmpty()) {
                        Log.i("TAGG", "onPlayClick: ayahs $ayahs")
                        val firstAyah = ayahs.first()
                        surahDetailStateCallback?.update(
                            surahDetailStateData.copy(
                                playWholeChapter = true,
                                currentAyah = firstAyah.numberInSurah.toInt(),
                                runAudio = true
                            )
                        )
                        quranAudioVmCallback?.callVerseAudioFile()
                    }
                } else {
                    Log.e("TAGG", "chaptersAudioFile is null!")
                }
            }
        }

        override fun onPlayClicked() {
            if (surahDetailStateData.playWholeChapter) {
                if (currentExoPlayer == null || surahDetailStateData.runAudio == false) {
                    // Воспроизведение всей суры
                    onPlayClick()
                } else {
                    if (isPlaying.value) {
                        currentExoPlayer?.pause()
                        isPlaying.value = false //todo пофиксить баг с несменой иконки
                        //surahDetailStateCallback?.update(surahDetailStateData.copy(isAudioPlaying = false))
                    } else {
                        currentExoPlayer?.play()
                        isPlaying.value = true
                        //surahDetailStateCallback?.update(surahDetailStateData.copy(isAudioPlaying = true))
                    }
                }
            } else {
                handlePlayPauseForChapter(isPlaying.value, currentExoPlayer)  // Воспроизведение одного файла
                isPlaying.value = !isPlaying.value
            }
        }

        override fun player(audioUrl: String): ExoPlayer {
            return (if (currentExoPlayer != null) {
                currentExoPlayer!!
            } else {
                try {
                    ExoPlayer.Builder(context).build().apply {
                        currentExoPlayer = this
                        val mediaItem = MediaItem.fromUri(audioUrl.toUri())
                        setMediaItem(mediaItem)
                        setAudioAttributes(
                            AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
                                .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).build(), true
                        )
                        setHandleAudioBecomingNoisy(true)
                        addListener(object : androidx.media3.common.Player.Listener {
                            override fun onPlaybackStateChanged(state: Int) {
                                if (state == ExoPlayer.STATE_ENDED) {
                                    onAudioEnded(surahDetailStateData, audioState)
                                    setIsPlaying(false)
                                }
                            }
                        })
                        prepare()
                        playWhenReady = true
                        setIsPlaying(true)
                    }
                } catch (e: Exception) {
                    Log.e("TAGG", "player: $e")
                }
            }) as ExoPlayer
        }

        override fun onAudioEnded(
            surahDetailStateData: SurahDetailState,
            audioState: QuranAudioAqcUIState,
        ) {
            if (!surahDetailStateData.playWholeChapter) {
                surahDetailStateCallback?.update(
                    surahDetailStateData.copy(
                        runAudio = false, isAudioPlaying = false, playWholeChapter = true
                    )
                )
                return
            }

            val current = surahDetailStateData.currentAyah
            val ayahs = audioState.chaptersAudioFile?.chapterAudio?.ayahs ?: return
            val currentIndex = ayahs.indexOfFirst { it.numberInSurah.toInt() == current }

            if (currentIndex != -1 && currentIndex + 1 < ayahs.size) {
                val nextAyah = ayahs[currentIndex + 1]
                val newState = surahDetailStateData.copy(
                    currentAyah = nextAyah.numberInSurah.toInt(), runAudio = true
                )
                surahDetailStateCallback?.update(newState)
                quranAudioVmCallback?.callVerseAudioFile()
            } else {
                // Конец суры
                surahDetailStateCallback?.update(
                    surahDetailStateData.copy(
                        runAudio = false, isAudioPlaying = false
                    )
                )
            }
        }

        override fun setIsPlaying(boolean: Boolean) {
            isPlaying.value = boolean
        }

        override fun setSurahDetailStateCallback(callback: SurahDetailStateCallback) {
            this.surahDetailStateCallback = callback
        }


        override fun setQuranAudioVmCallback(callback: QuranAudioVmCallback) {
            this.quranAudioVmCallback = callback
        }
    }
}