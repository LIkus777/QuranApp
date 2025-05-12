package com.zaur.features.surah.screen.surah_detail.player

import android.net.Uri
import androidx.media3.common.MediaItem
import com.zaur.data.downloader.AudioDownloader

/**
* @author Zaur
* @since 2025-05-12
*/

interface PlaylistBuilder {

    fun buildLocalPlaylist(
        ayahs: AyahList,
        surahNumber: Int,
    ): List<MediaItem>

    fun buildCachePlaylist(
        ayahs: CacheAyahList,
    ): List<MediaItem>

    class Base(
        private val audioDownloader: AudioDownloader,
    ) : PlaylistBuilder {
        override fun buildLocalPlaylist(
            ayahs: AyahList,
            surahNumber: Int,
        ): List<MediaItem> {
            return ayahs.getList().map { ayah ->
                val localFile = audioDownloader.getAudioFile(
                    surahNumber.toLong(), ayah.numberInSurah().toLong(), "ar.alafasy"
                )
                val uri = Uri.fromFile(localFile)
                MediaItem.Builder().setUri(uri).setMediaId(ayah.numberInSurah().toString()).build()
            }
        }

        override fun buildCachePlaylist(ayahs: CacheAyahList): List<MediaItem> {
            return ayahs.getList().map { cacheAudio ->
                val uri = Uri.parse("file://${cacheAudio.path()}")
                MediaItem.Builder().setUri(uri).setMediaId(cacheAudio.verseNumber().toString())
                    .build()
            }
        }
    }
}