package com.zaur.features.surah.screen.surah_detail.player

import com.zaur.data.downloader.AudioDownloader
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface AudioPlaybackHelper {

    fun play(verse: VerseAudio)

    class Base(
        private val surahDetailScreenState: StateFlow<SurahDetailScreenState.Base>,
        private val audioDownloader: AudioDownloader,
        private val audioPlayer: AudioPlayer,
        private val playFromLocal: Boolean = true,
    ) : AudioPlaybackHelper {
        override fun play(verse: VerseAudio) {
            val localFile = audioDownloader.getAudioFile(
                verse.surah().number(),
                verse.numberInSurah(),
                surahDetailScreenState.value.reciterState().currentReciter()
            )
            if (playFromLocal && localFile?.exists() == true) {
                audioPlayer.playAudio(localFile.absolutePath, playFromCache = true)
            } else {
                audioPlayer.playAudio(verse.audio(), playFromCache = false)
            }
        }
    }
}