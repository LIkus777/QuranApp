package com.zaur.features.surah.screen.surah_detail.player

import androidx.media3.common.MediaItem
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.screen.surah_detail.player.AyahList.EmptyAyahList
import com.zaur.features.surah.screen.surah_detail.player.AyahList.RealAyahList
import com.zaur.features.surah.screen.surah_detail.player.CacheAyahList.EmptyCacheAyahList
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
        private var ayahs: AyahList = EmptyAyahList
        private var cacheAyahs: CacheAyahList = EmptyCacheAyahList
        private var localMediaItems: List<MediaItem> = emptyList()
        private var cacheMediaItems: List<MediaItem> = emptyList()

        private val state = surahDetailStateManager.surahDetailState()

        override suspend fun setAyahs(ayahs: List<Ayah.Base>) {
            this.ayahs = RealAyahList(ayahs)
            localMediaItems = playlistBuilder.buildLocalPlaylistAsync(
                this.ayahs, state.value.audioPlayerState().currentSurahNumber()
            )
        }

        override suspend fun setCacheAudios(ayahs: List<CacheAudio.Base>) {
            this.cacheAyahs = RealCacheAyahList(ayahs)
            cacheMediaItems = playlistBuilder.buildCachePlaylistAsync(this.cacheAyahs)
        }

        override fun currentPlaylist(): List<MediaItem> =
            if (state.value.audioPlayerState().isOfflineMode()) localMediaItems else cacheMediaItems

        override fun clear() {
            ayahs = EmptyAyahList
            cacheAyahs = EmptyCacheAyahList
            localMediaItems = emptyList()
            cacheMediaItems = emptyList()
        }
    }


}