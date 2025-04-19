package com.zaur.features.surah.screen.surah_detail.player

import com.zaur.data.downloader.AudioDownloader
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.features.surah.base.AudioPlayer

interface AudioPlaybackHelper {

    fun play(verse: Ayah)

    class Base(
        private val audioDownloader: AudioDownloader,
        private val audioPlayer: AudioPlayer,
        private val playFromLocal: Boolean = true,
    ) : AudioPlaybackHelper {
        override fun play(verse: Ayah) {
            val localFile = audioDownloader.getAudioFile(
                verse.chapterNumber, verse.numberInSurah, "ar.alafasy"
            )
            if (playFromLocal && localFile?.exists() == true) {
                audioPlayer.playAudio(localFile.absolutePath, playFromCache = true)
            } else {
                audioPlayer.playAudio(verse.audio, playFromCache = false)
            }
        }
    }
}