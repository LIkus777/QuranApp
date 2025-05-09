package com.zaur.features.surah.screen.surah_detail.player

import android.net.Uri
import androidx.media3.common.MediaItem
import com.zaur.data.downloader.AudioDownloader
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio

interface PlaylistBuilder {

    fun buildLocalPlaylist(
        ayahs: List<Ayah.Base>,
        surahNumber: Int,
    ): List<MediaItem>

    fun buildCachePlaylist(
        ayahs: List<CacheAudio>,
    ): List<MediaItem>

    class Base(
        private val audioDownloader: AudioDownloader,
    ) : PlaylistBuilder {
        override fun buildLocalPlaylist(
            ayahs: List<Ayah.Base>,
            surahNumber: Int,
        ): List<MediaItem> {
            return ayahs.map { ayah ->
                val localFile = audioDownloader.getAudioFile(
                    surahNumber.toLong(), ayah.numberInSurah().toLong(), "ar.alafasy"
                )
                val uri = Uri.fromFile(localFile)
                MediaItem.Builder().setUri(uri).setMediaId(ayah.numberInSurah().toString()).build()
            }
        }

        override fun buildCachePlaylist(ayahs: List<CacheAudio>): List<MediaItem> {
            return ayahs.map { cacheAudio ->
                val uri = Uri.parse("file://${cacheAudio.path()}")
                MediaItem.Builder().setUri(uri)
                    .setMediaId(cacheAudio.verseNumber().toString()).build()
            }
        }
    }
}