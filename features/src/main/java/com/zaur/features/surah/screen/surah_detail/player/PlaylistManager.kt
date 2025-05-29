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
        private val surahDetailStateManager: SurahDetailStateManager, // Менеджер состояния плеера
    ) : PlaylistManager {
        private var cacheMediaItems: List<MediaItem> = emptyList()
        private var cloudMediaItems: List<MediaItem> = emptyList()
        private val state = surahDetailStateManager.surahDetailState()

        override suspend fun setAyahs(ayahs: List<Ayah.Base>) {
            val list = RealAyahList(ayahs)
            // строим cloud-плейлист сразу
            cloudMediaItems = playlistBuilder.buildCloudPlaylistAsync(
                list, state.value.audioPlayerState().currentSurahNumber()
            )
        }

        override suspend fun setCacheAudios(ayahs: List<CacheAudio.Base>) {
            cacheMediaItems = playlistBuilder.buildCachePlaylistAsync(
                RealCacheAyahList(ayahs)
            )
        }

        override fun currentPlaylist(): List<MediaItem> =
            if (/*state.value.audioPlayerState().isOfflineMode()*/true) cloudMediaItems else cacheMediaItems

        override fun clear() {
            cloudMediaItems = emptyList()
            cacheMediaItems = emptyList()
        }
    }


}