package com.zaur.features.surah.screen.surah_detail.player

import com.zaur.data.downloader.AudioDownloader
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.features.surah.base.AudioPlayer

interface AudioPlaybackHelper {

    fun play(verse: VerseAudioAqc)

    class Base(
        private val audioDownloader: AudioDownloader,
        private val audioPlayer: AudioPlayer,
        private val playFromLocal: Boolean = true,
    ) : AudioPlaybackHelper {
        override fun play(verse: VerseAudioAqc) {
            val localFile = audioDownloader.getAudioFile(
                verse.surah.number, verse.numberInSurah, "ar.alafasy"
            )
            if (playFromLocal && localFile?.exists() == true) {
                audioPlayer.playAudio(localFile.absolutePath, playFromCache = true)
            } else {
                audioPlayer.playAudio(verse.audio, playFromCache = false)
            }
        }
    }
}