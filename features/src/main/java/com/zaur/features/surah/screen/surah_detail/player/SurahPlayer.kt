package com.zaur.features.surah.screen.surah_detail.player

import android.util.Log
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.base.AudioPlayerCallback
import com.zaur.features.surah.screen.SurahDetailStateManager
import com.zaur.features.surah.viewmodel.QuranAudioViewModel

// Интерфейс для управления плеером
interface SurahPlayer {

    fun onPlayVerse(verse: Ayah)
    fun onPlayWholeClicked()
    fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int)
    fun onAudioEnded()
    fun onPauseClicked()
    fun onStopClicked()

    fun clear()

    fun setQuranAudioVmCallback(callback: QuranAudioViewModel.QuranAudioVmCallback)

    fun setAyahs(ayahs: List<com.zaur.domain.al_quran_cloud.models.arabic.Ayah>)

    // Реализация плеера с разделением на управление состоянием и воспроизведением
    class Base(
        private val playlistBuilder: PlaylistBuilder,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater,
        private val audioPlaybackHelper: AudioPlaybackHelper,
        private val audioPlayer: AudioPlayer,  // Интерфейс для работы с плеером
        private val surahDetailStateManager: SurahDetailStateManager, // Менеджер состояния плеера
    ) : SurahPlayer {

        private var quranAudioVmCallback: QuranAudioViewModel.QuranAudioVmCallback? = null
        private var ayahs: List<com.zaur.domain.al_quran_cloud.models.arabic.Ayah>? = null
        private var currentAyahIndex = 0

        private val state = surahDetailStateManager.getState()

        init {
            Log.i("TAG", "SurahPlayer: state $state ")
            audioPlayer.setAudioPlayerCallback(object : AudioPlayerCallback {
                override fun audioEnded() {
                    onAudioEnded()
                }

                override fun onAyahChanged(mediaId: String?) {
                    val ayahNumberInSurah = mediaId?.toIntOrNull() ?: return
                    val ayah =
                        ayahs?.find { it.numberInSurah.toInt() == ayahNumberInSurah } ?: return
                    Log.d("TAG", "onAyahChanged: ayahNumberInSurah=$ayahNumberInSurah")
                    Log.d("TAG", "onAyahChanged: number=$ayah.number.toInt()")
                    Log.d("TAG", "onAyahChanged: numberInSurah=${ayah.numberInSurah.toInt()}")
                    audioPlayerStateUpdater.updateCurrentAyah(
                        ayah.number.toInt(), ayah.numberInSurah.toInt()
                    )
                }
            })
        }

        override fun setAyahs(ayahs: List<com.zaur.domain.al_quran_cloud.models.arabic.Ayah>) {
            this.ayahs = ayahs
        }

        override fun onPlayVerse(verse: Ayah) {
            if (state.value.audioPlayerState.restartAudio) {
                audioPlayer.restartAudio()
            } else {
                audioPlaybackHelper.play(verse)
            }
            audioPlayerStateUpdater.setRestartAudio(false, true)
        }

        override fun onPlayWholeClicked() {
            if (!audioPlayer.isPlaying() && !audioPlayer.isPaused()) {
                val items =
                    playlistBuilder.build(ayahs!!, state.value.audioPlayerState.currentSurahNumber)
                audioPlayer.playPlaylist(items)
                audioPlayerStateUpdater.markWholeChapterPlaying(true, true)
            } else {
                if (audioPlayer.isPlaying()) {
                    audioPlayer.pauseAudio()
                    audioPlayerStateUpdater.setPlaying(false)
                } else {
                    audioPlayer.resume()
                    audioPlayerStateUpdater.setPlaying(true)
                }
            }
        }

        override fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int) {
            audioPlayerStateUpdater.setCurrentAyahAndSurah(ayahNumber, surahNumber)
            quranAudioVmCallback?.callVerseAudioFile(ayahNumber)
        }

        fun playCurrentAyah() {
            val currentAyah = ayahs?.getOrNull(currentAyahIndex)
            if (currentAyah != null) {
                audioPlayerStateUpdater.updateCurrentAyahInSurah(currentAyah.numberInSurah.toInt())
                quranAudioVmCallback?.callVerseAudioFile(currentAyah.number.toInt())
            } else {
                audioPlayerStateUpdater.markWholeChapterPlaying(false, true)
            }
        }

        override fun onAudioEnded() {
            if (!state.value.audioPlayerState.playWholeChapter) {
                audioPlayerStateUpdater.stop()
                return
            }

            // Если воспроизведение главы — переходим к следующему аяту
            currentAyahIndex++
            playCurrentAyah()
        }

        override fun onPauseClicked() {
            audioPlayer.pauseAudio()
            audioPlayerStateUpdater.stop()
        }

        override fun onStopClicked() {
            audioPlayer.stopAudio()
            audioPlayerStateUpdater.stop()
        }

        override fun clear() {
            audioPlayer.clear()
            ayahs = null
            quranAudioVmCallback = null
        }

        override fun setQuranAudioVmCallback(callback: QuranAudioViewModel.QuranAudioVmCallback) {
            this.quranAudioVmCallback = callback
        }
    }
}