package com.zaur.features.surah.screen

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.viewmodel.QuranAudioViewModel.QuranAudioVmCallback

// Интерфейс для управления плеером
interface Player {
    fun onPlayWholeClicked()
    fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int, url: String)
    fun onAudioEnded()
    fun onPlayClicked()
    fun onPauseClicked()
    fun onStopClicked()

    fun clear()

    fun setQuranAudioVmCallback(callback: QuranAudioVmCallback)

    fun setAyahs(ayahs: ArabicChaptersAqc)

    // Реализация плеера с разделением на управление состоянием и воспроизведением
    class Base(
        private val audioPlayer: AudioPlayer,  // Интерфейс для работы с плеером
        private val surahDetailStateManager: SurahDetailStateManager, // Менеджер состояния плеера
    ) : Player {

        private var quranAudioVmCallback: QuranAudioVmCallback? = null
        private var ayahs: ArabicChaptersAqc? = null

        private val state = surahDetailStateManager.getState()

        override fun setAyahs(ayahs: ArabicChaptersAqc) {
            this.ayahs = ayahs
        }

        override fun onPlayWholeClicked() {
            // если ничего не воспроизводится — начинаем сначала
            if (!audioPlayer.isPlaying()) {
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
                    audioPlayer.playAudio("") // текущий url должен где-то храниться
                    val resumed = state.value.copy(
                        audioPlayerState = state.value.audioPlayerState.copy(
                            isAudioPlaying = false
                        )
                    )
                    surahDetailStateManager.updateState(resumed)
                }
            }
        }

        override fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int, url: String) {
            val updated = state.value.copy(
                audioPlayerState = state.value.audioPlayerState.copy(
                    currentAyah = ayahNumber,
                    currentSurahNumber = surahNumber,
                    isAudioPlaying = true,
                    playWholeChapter = false,
                    restartAudio = !state.value.audioPlayerState.restartAudio
                )
            )

            surahDetailStateManager.updateState(updated)
            quranAudioVmCallback?.callVerseAudioFile(ayahNumber)
            audioPlayer.playAudio(url)
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
            val ayahs =
                ayahs!!.arabicChapters.ayahs // реализуй сам или передавай список через колбэк
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

        override fun onPlayClicked() {
            // ты можешь тут воспроизвести текущий url
            audioPlayer.playAudio("") // заменить на актуальный URL
            val updated = state.value.copy(
                audioPlayerState = state.value.audioPlayerState.copy(
                    isAudioPlaying = true
                )
            )
            surahDetailStateManager.updateState(updated)
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
            audioPlayer.release()
            quranAudioVmCallback = null
        }

        override fun setQuranAudioVmCallback(callback: QuranAudioVmCallback) {
            this.quranAudioVmCallback = callback
        }
    }
}