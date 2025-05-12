package com.zaur.domain.al_quran_cloud.models.audiofile

/**
* @author Zaur
* @since 2025-05-12
*/

interface CacheAudio {

    fun chapterNumber(): Int
    fun verseNumber(): Int
    fun path(): String

    data class Base(
        private val chapterNumber: Int,
        private val verseNumber: Int,
        private val path: String,
    ) : CacheAudio {
        override fun chapterNumber(): Int = chapterNumber
        override fun verseNumber(): Int = verseNumber
        override fun path(): String = path
    }

}