package com.zaur.features.surah.screen.surah_detail.player

import android.net.Uri
import android.util.Log
import androidx.media3.common.MediaItem
import com.zaur.data.downloader.AudioDownloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface PlaylistBuilder {

    suspend fun buildLocalPlaylistAsync(
        ayahs: AyahList,
        surahNumber: Int,
    ): List<MediaItem>

    suspend fun buildCachePlaylistAsync(ayahs: CacheAyahList): List<MediaItem>

    data class Base(
        private val audioDownloader: AudioDownloader,
    ) : PlaylistBuilder {
        override suspend fun buildLocalPlaylistAsync(
            ayahs: AyahList,
            surahNumber: Int,
        ): List<MediaItem> = withContext(Dispatchers.Default) {
            val mediaItems = mutableListOf<MediaItem>()

            for (ayah in ayahs.getList()) {
                val file = audioDownloader.getAudioFile(
                    surahNumber.toLong(), ayah.numberInSurah().toLong(), "ar.alafasy"
                )

                if (file != null) {
                    if (file.exists()) {
                        val uri = Uri.fromFile(file)
                        val mediaItem = MediaItem.Builder().setUri(uri)
                            .setMediaId(ayah.numberInSurah().toString()).build()
                        mediaItems.add(mediaItem)
                    } else {
                        Log.w("PlaylistBuilder", "Missing file for ayah ${ayah.numberInSurah()}")
                    }
                }
            }

            return@withContext mediaItems
        }

        override suspend fun buildCachePlaylistAsync(ayahs: CacheAyahList): List<MediaItem> =
            withContext(Dispatchers.Default) {
                Log.d("TAG", "Building playlist of ${ayahs.getList().size} ayahs")

                ayahs.getList().map { cacheAudio ->
                    val uri = Uri.fromFile(File(cacheAudio.path()))
                    MediaItem.Builder().setUri(uri).setMediaId(cacheAudio.verseNumber().toString())
                        .build()
                }
            }
    }
}