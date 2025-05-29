package com.zaur.features.surah.screen.surah_detail.player

import android.net.Uri
import android.util.Log
import androidx.media3.common.MediaItem
import com.zaur.data.downloader.AudioDownloader
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface PlaylistBuilder {

    suspend fun buildCloudPlaylistAsync(
        ayahs: AyahList,
        surahNumber: Int
    ): List<MediaItem>   // ← новая функция

    suspend fun buildCachePlaylistAsync(ayahs: CacheAyahList): List<MediaItem>

    data class Base(
        private val surahDetailScreenState: StateFlow<SurahDetailScreenState.Base>,
        private val audioDownloader: AudioDownloader,
    ) : PlaylistBuilder {

        override suspend fun buildCloudPlaylistAsync(
            ayahs: AyahList,
            surahNumber: Int
        ): List<MediaItem> = withContext(Dispatchers.Default) {
            ayahs.getList().map { ayah ->
                // Предположим, у Ayah.Base есть метод audioUrl()
                val url = ayah.audio()
                MediaItem.Builder()
                    .setUri(url)
                    .setMediaId(ayah.numberInSurah().toString())
                    .build()
            }
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