package com.zaur.features.surah.screen

/*
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
    fun clear()
    fun setIsPlaying(boolean: Boolean)
    fun onPlayWholeClicked()
    fun onPlaySingleClicked(ayahNumber: Int)
    fun onAudioEnded(surahDetailStateData: SurahDetailState, audioState: QuranAudioAqcUIState)
    fun setSurahDetailStateCallback(callback: SurahDetailStateCallback)
    fun setQuranAudioVmCallback(callback: QuranAudioVmCallback)
    fun updateSurahDetailState(newSurahDetailStateData: SurahDetailState)
    fun updateAudioState(newAudioState: QuranAudioAqcUIState)
}

class PlayerImpl(
    private val context: Context,
    private var surahDetailStateData: SurahDetailState,
    private var audioState: QuranAudioAqcUIState
) : Player {

    private var exoPlayer: ExoPlayer? = null
    private var isPlaying = mutableStateOf(false)

    private var stateCallback: SurahDetailStateCallback? = null
    private var vmCallback: QuranAudioVmCallback? = null

    override fun setIsPlaying(boolean: Boolean) {
        isPlaying.value = boolean
    }

    override fun setSurahDetailStateCallback(callback: SurahDetailStateCallback) {
        stateCallback = callback
    }

    override fun setQuranAudioVmCallback(callback: QuranAudioVmCallback) {
        vmCallback = callback
    }

    override fun updateSurahDetailState(newSurahDetailStateData: SurahDetailState) {
        surahDetailStateData = newSurahDetailStateData
    }

    override fun updateAudioState(newAudioState: QuranAudioAqcUIState) {
        audioState = newAudioState
    }

    fun onPlayWholeClick() {
        val chaptersAudio = audioState.chaptersAudioFile ?: return
        val ayahs = chaptersAudio.chapterAudio.ayahs
        if (ayahs.isEmpty()) return

        val firstAyah = ayahs.first()
        stateCallback?.update(
            surahDetailStateData.copy(
                playWholeChapter = true,
                currentAyah = firstAyah.numberInSurah.toInt(),
                isAudioPlaying = true
            )
        )
        vmCallback?.callVerseAudioFile(1)
    }

    override fun onPlayWholeClicked() {
        if (exoPlayer == null) {
            onPlayWholeClick()
        } else {
            if (isPlaying.value) {
                exoPlayer?.pause()
                setIsPlaying(false)
                stateCallback?.update(surahDetailStateData.copy(isAudioPlaying = false))
            } else {
                exoPlayer?.play()
                setIsPlaying(true)
                stateCallback?.update(surahDetailStateData.copy(isAudioPlaying = true))
            }
        }
    }

    override fun onPlaySingleClicked(ayahNumber: Int) {
        onPlaySingleClick(ayahNumber)
        */
/*if (exoPlayer == null || ayahNumber == surahDetailStateData.currentAyah) {
            Log.i("TAGG", "onPlaySingleClickED NULL")
            onPlaySingleClick(ayahNumber)
        } else {
            if (isPlaying.value) {
                Log.i("TAGG", "onPlaySingleClickED IF")
                exoPlayer?.pause()
                setIsPlaying(false)
                stateCallback?.update(surahDetailStateData.copy(isAudioPlaying = false))
            } else {
                Log.i("TAGG", "onPlaySingleClickED ELSE")
                exoPlayer?.play()
                setIsPlaying(true)
                stateCallback?.update(surahDetailStateData.copy(isAudioPlaying = true))
            }
        }*//*

    }

    fun onPlaySingleClick(ayahNumber: Int) {
        if (surahDetailStateData.currentAyah == ayahNumber) {
            Log.i("TAGG", "onPlaySingleClick IF")
            stateCallback?.update(surahDetailStateData.copy(playWholeChapter = false, restartAudio = !surahDetailStateData.restartAudio))
            vmCallback?.callVerseAudioFile(ayahNumber)
        } else {
            Log.i("TAGG", "onPlaySingleClick ELSE")
            stateCallback?.update(surahDetailStateData.copy(currentAyah = ayahNumber, playWholeChapter = false, restartAudio = !surahDetailStateData.restartAudio))
            vmCallback?.callVerseAudioFile(ayahNumber)
        }
    }

    override fun player(audioUrl: String): ExoPlayer {
        val mediaItem = MediaItem.fromUri(audioUrl.toUri())
        val current = exoPlayer?.currentMediaItem == mediaItem

        if (exoPlayer != null && !current) {
            exoPlayer!!.setMediaItem(mediaItem)
            setIsPlaying(true)
            return exoPlayer!!
        }

        if (exoPlayer == null) {
            val player = ExoPlayer.Builder(context).build().apply {
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
            exoPlayer = player
        }

        return exoPlayer!!
    }

    override fun onAudioEnded(
        surahDetailStateData: SurahDetailState,
        audioState: QuranAudioAqcUIState,
    ) {
        if (!surahDetailStateData.playWholeChapter) {
            stateCallback?.update(
                surahDetailStateData.copy(isAudioPlaying = false, playWholeChapter = true)
            )
            return
        }

        val current = surahDetailStateData.currentAyah
        val ayahs = audioState.chaptersAudioFile?.chapterAudio?.ayahs ?: return
        val currentIndex = ayahs.indexOfFirst { it.numberInSurah.toInt() == current }

        if (currentIndex != -1 && currentIndex + 1 < ayahs.size) {
            Log.i("TAGG", "onAudioEnded: IF $surahDetailStateData")
            val nextAyah = ayahs[currentIndex + 1]
            val newState = surahDetailStateData.copy(
                currentAyah = nextAyah.numberInSurah.toInt(), isAudioPlaying = true
            )
            stateCallback?.update(newState)
            vmCallback?.callVerseAudioFile(nextAyah.numberInSurah.toInt())
        } else {
            Log.i("TAGG", "onAudioEnded: ELSE $surahDetailStateData")
            stateCallback?.update(
                surahDetailStateData.copy(
                    playWholeChapter = true,
                    isAudioPlaying = false
                )
            )
        }
    }

    override fun clear() {
        exoPlayer?.release()
        exoPlayer = null
        stateCallback = null
        vmCallback = null
    }
}

class AyahPlaybackHandler {
    fun handlePlayPauseForSingleAyah(isPlaying: Boolean, player: ExoPlayer?) {
        if (isPlaying) {
            player?.pause()
        } else {
            player?.play()
        }
    }
}


*/
/*interface Player {

    class Base(
        private val context: Context,
        private var surahDetailStateData: SurahDetailState,
        private var audioState: QuranAudioAqcUIState,
    ) : Player {

        init {
            Log.i("TAGG", "Player BASE ПЕРЕСОЗДАЕТСЯ")
        }

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
                                isAudioPlaying = true
                            )
                        )
                        quranAudioVmCallback?.callVerseAudioFile()
                    }
                } else {
                    Log.e("TAGG", "chaptersAudioFile is null!")
                }
            }
        }

        // Метод обновления SurahDetailState
        override fun updateSurahDetailState(newSurahDetailStateData: SurahDetailState) {
            this.surahDetailStateData = newSurahDetailStateData
        }

        // Метод обновления QuranAudioState
        override fun updateAudioState(newAudioState: QuranAudioAqcUIState) {
            this.audioState = newAudioState
        }

        override fun onPlayClicked() {
            if (surahDetailStateData.playWholeChapter) {
                if (currentExoPlayer == null) {
                    // Первый запуск — начать проигрывание всей суры
                    Log.i("TAGG", "onPlayClicked: ПЕРЕСОЗДАНИЕ ")
                    onPlayClick()
                } else {
                    if (isPlaying.value) {
                        Log.i("TAGG", "isPlaying.value ТРУ")
                        // Сейчас играет — ставим на паузу
                        currentExoPlayer?.pause()
                        setIsPlaying(false)
                        surahDetailStateCallback?.update(
                            surahDetailStateData.copy(isAudioPlaying = false)
                        )
                    } else {
                        // Сейчас на паузе — просто продолжаем
                        Log.i("TAGG", "isPlaying.value ФОЛСЕ")
                        currentExoPlayer?.play()
                        setIsPlaying(true)
                        surahDetailStateCallback?.update(
                            surahDetailStateData.copy(isAudioPlaying = true)
                        )
                    }
                }
            } else {
                handlePlayPauseForChapter(isPlaying.value, currentExoPlayer)
                isPlaying.value = !isPlaying.value
            }
        }

        override fun player(audioUrl: String): ExoPlayer {
            val current = currentExoPlayer?.currentMediaItem == MediaItem.fromUri(audioUrl.toUri())
            return if (currentExoPlayer != null && current == false) {
                currentExoPlayer!!.setMediaItem(MediaItem.fromUri(audioUrl.toUri()))
                setIsPlaying(true)
                currentExoPlayer!!
            } else if (currentExoPlayer == null) {
                val player = ExoPlayer.Builder(context).build().apply {
                    Log.i("TAGG", "player:МОЙ ПЛЕЕР $currentExoPlayer")
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
                currentExoPlayer = player
                Log.i("TAGG", "player:МОЙ ПЛЕЕР $currentExoPlayer")
                currentExoPlayer!!
            } else {
                currentExoPlayer!!
            }
        }

        override fun clear() {
            currentExoPlayer?.release()
            currentExoPlayer = null
            surahDetailStateCallback = null
            quranAudioVmCallback = null
        }

        override fun onAudioEnded(
            surahDetailStateData: SurahDetailState,
            audioState: QuranAudioAqcUIState,
        ) {
            if (!surahDetailStateData.playWholeChapter) {
                surahDetailStateCallback?.update(
                    surahDetailStateData.copy(
                        isAudioPlaying = false, playWholeChapter = true
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
                    currentAyah = nextAyah.numberInSurah.toInt(), isAudioPlaying = true
                )
                surahDetailStateCallback?.update(newState)
                quranAudioVmCallback?.callVerseAudioFile()
            } else {
                // Конец суры
                surahDetailStateCallback?.update(
                    surahDetailStateData.copy(
                        isAudioPlaying = false
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
}*/