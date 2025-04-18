package com.zaur.features.surah.screen

import com.zaur.domain.al_quran_cloud.models.arabic.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.base.AudioPlayerCallback
import com.zaur.features.surah.viewmodel.QuranAudioViewModel.QuranAudioVmCallback

// Интерфейс для управления плеером
interface SurahPlayer {

    fun onPlayVerse(verse: VersesAudioFileAqc)
    fun onPlayWholeClicked()
    fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int)
    fun onAudioEnded()
    fun onPauseClicked()
    fun onStopClicked()

    fun clear()

    fun setQuranAudioVmCallback(callback: QuranAudioVmCallback)

    fun setAyahs(ayahs: List<Ayah>)

    // Реализация плеера с разделением на управление состоянием и воспроизведением
    class Base(
        private val audioPlayer: AudioPlayer,  // Интерфейс для работы с плеером
        private val surahDetailStateManager: SurahDetailStateManager, // Менеджер состояния плеера
    ) : SurahPlayer {

        private var quranAudioVmCallback: QuranAudioVmCallback? = null
        private var ayahs: List<Ayah>? = null

        private val state = surahDetailStateManager.getState()

        override fun setAyahs(ayahs: List<Ayah>) {
            this.ayahs = ayahs
        }

        override fun onPlayVerse(verse: VersesAudioFileAqc) {
            if (state.value.audioPlayerState.restartAudio) {
                audioPlayer.restartAudio()
            } else {
                audioPlayer.playAudio(verse.versesAudio.audio)
            }
            val updated = state.value.copy(
                audioPlayerState = state.value.audioPlayerState.copy(
                    isAudioPlaying = true, restartAudio = false
                )
            )
            surahDetailStateManager.updateState(updated)
            audioPlayer.setAudioPlayerCallback(object : AudioPlayerCallback {
                override fun audioEnded() {
                    onAudioEnded()
                }
            })
        }

        override fun onPlayWholeClicked() {
            // если ничего не воспроизводится — начинаем сначала
            if (!audioPlayer.isPlaying() && !audioPlayer.isPaused()) {
                val newState = state.value.copy(
                    audioPlayerState = state.value.audioPlayerState.copy(
                        currentAyah = 1, isAudioPlaying = true, playWholeChapter = true
                    )
                )
                surahDetailStateManager.updateState(newState)
                quranAudioVmCallback?.callVerseAudioFile(1)
            } else {
                // Пауза / Продолжение
                if (audioPlayer.isPlaying()) {
                    audioPlayer.pauseAudio()
                    val paused = state.value.copy(
                        audioPlayerState = state.value.audioPlayerState.copy(
                            isAudioPlaying = false
                        )
                    )
                    surahDetailStateManager.updateState(paused)
                } else {
                    audioPlayer.playAudio("")
                    val resumed = state.value.copy(
                        audioPlayerState = state.value.audioPlayerState.copy(
                            isAudioPlaying = true
                        )
                    )
                    surahDetailStateManager.updateState(resumed)
                }
            }
        }

        override fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int) {
            val updated = state.value.copy(
                audioPlayerState = state.value.audioPlayerState.copy(
                    currentAyah = ayahNumber,
                    currentSurahNumber = surahNumber,
                    isAudioPlaying = true,
                    playWholeChapter = false,
                )
            )
            surahDetailStateManager.updateState(updated)
            quranAudioVmCallback?.callVerseAudioFile(ayahNumber)
        }

        override fun onAudioEnded() {
            if (!state.value.audioPlayerState.playWholeChapter) {
                val stopped = state.value.copy(
                    audioPlayerState = state.value.audioPlayerState.copy(
                        isAudioPlaying = false
                    )
                )
                surahDetailStateManager.updateState(stopped)
                return
            }

            // Если воспроизведение главы — переходим к следующему аяту
            val ayahs = ayahs!!
            val currentIndex =
                ayahs.indexOfFirst { it.numberInSurah.toInt() == state.value.audioPlayerState.currentAyah }

            if (currentIndex + 1 < ayahs.size) {
                val nextAyah = ayahs[currentIndex + 1]
                val newState = state.value.copy(
                    audioPlayerState = state.value.audioPlayerState.copy(
                        currentAyah = nextAyah.numberInSurah.toInt(), isAudioPlaying = true
                    )
                )
                surahDetailStateManager.updateState(newState)
                quranAudioVmCallback?.callVerseAudioFile(nextAyah.numberInSurah.toInt())
            } else {
                val finished = state.value.copy(
                    audioPlayerState = state.value.audioPlayerState.copy(
                        playWholeChapter = true, isAudioPlaying = false
                    )
                )
                surahDetailStateManager.updateState(finished)
            }
        }

        override fun onPauseClicked() {
            audioPlayer.pauseAudio()
            val updated = state.value.copy(
                audioPlayerState = state.value.audioPlayerState.copy(
                    isAudioPlaying = false
                )
            )
            surahDetailStateManager.updateState(updated)
        }

        override fun onStopClicked() {
            audioPlayer.stopAudio()
            val updated = state.value.copy(
                audioPlayerState = state.value.audioPlayerState.copy(
                    isAudioPlaying = false
                )
            )
            surahDetailStateManager.updateState(updated)
        }

        override fun clear() {
            audioPlayer.clear()
            ayahs = null
            quranAudioVmCallback = null
        }

        override fun setQuranAudioVmCallback(callback: QuranAudioVmCallback) {
            this.quranAudioVmCallback = callback
        }
    }
}