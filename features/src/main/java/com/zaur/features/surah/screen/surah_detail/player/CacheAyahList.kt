package com.zaur.features.surah.screen.surah_detail.player

import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio

/**
* @author Zaur
* @since 2025-05-12
*/

interface CacheAyahList {
    fun isEmpty(): Boolean
    fun toPlaylist(): List<String>
    fun getList(): List<CacheAudio.Base>

    data class RealCacheAyahList(private val ayahs: List<CacheAudio.Base>) : CacheAyahList {
        override fun isEmpty() = ayahs.isEmpty()
        override fun toPlaylist(): List<String> = ayahs.map { it.path() }
        override fun getList(): List<CacheAudio.Base> = ayahs
    }

    object EmptyCacheAyahList : CacheAyahList {
        override fun isEmpty() = true
        override fun toPlaylist() = emptyList<String>()
        override fun getList(): List<CacheAudio.Base> = emptyList()
    }
}