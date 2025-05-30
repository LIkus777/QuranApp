package com.zaur.features.surah.screen.surah_detail.player

import androidx.media3.common.MediaItem
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.screen.surah_detail.player.AyahList.RealAyahList
import com.zaur.features.surah.screen.surah_detail.player.CacheAyahList.RealCacheAyahList

/**
 * @author Zaur
 * @since 22.05.2025
 */

interface PlaylistManager {

    suspend fun setAyahs(ayahs: List<Ayah.Base>)
    suspend fun setCacheAudios(ayahs: List<CacheAudio.Base>)
    fun currentPlaylist(): List<MediaItem>
    fun clear()

    class Base(
        private val playlistBuilder: PlaylistBuilder,
        private val surahDetailStateManager: SurahDetailStateManager,
        private val onPlaylistChanged: (List<MediaItem>) -> Unit,
    ) : PlaylistManager {

        private var cacheMediaItems: List<MediaItem> = emptyList()
        private var cloudMediaItems: List<MediaItem> = emptyList()

        private var lastReciterId: String? = null
        private var lastPlaylist: List<MediaItem> = emptyList()
        private var initialized = false

        private val state get() = surahDetailStateManager.surahDetailState().value

        override suspend fun setAyahs(ayahs: List<Ayah.Base>) {
            val reciterId = state.reciterState().currentReciter()
            // Если первый вызов или чтец сменился — пересобираем облачный плейлист
            if (!initialized || reciterId != lastReciterId) {
                val list = RealAyahList(ayahs)
                cloudMediaItems = playlistBuilder.buildCloudPlaylistAsync(
                    list, state.audioPlayerState().currentSurahNumber()
                )
                lastReciterId = reciterId
                maybeEmitChangedPlaylist()
                initialized = true
            }
        }

        override suspend fun setCacheAudios(ayahs: List<CacheAudio.Base>) {
            // Кэш всегда пересобираем
            cacheMediaItems = playlistBuilder.buildCachePlaylistAsync(
                RealCacheAyahList(ayahs)
            )
            maybeEmitChangedPlaylist()
        }

        private fun maybeEmitChangedPlaylist() {
            val current = currentPlaylist()
            if (!initialized) {
                // На случай, если кто-то вызовет вручную до setAyahs
                lastPlaylist = current
                initialized = true
                return
            }
            if (current != lastPlaylist) {
                lastPlaylist = current
                onPlaylistChanged(current)
            }
        }

        override fun currentPlaylist(): List<MediaItem> =
            if (state.audioPlayerState().isOfflineMode()) cacheMediaItems else cloudMediaItems

        override fun clear() {
            cacheMediaItems = emptyList()
            cloudMediaItems = emptyList()
            lastPlaylist = emptyList()
            lastReciterId = null
            initialized = false
        }
    }
}