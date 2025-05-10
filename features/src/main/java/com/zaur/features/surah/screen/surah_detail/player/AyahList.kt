package com.zaur.features.surah.screen.surah_detail.player

import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah

interface AyahList {
    fun isEmpty(): Boolean
    fun get(index: Int): Ayah.Base
    fun getList(): List<Ayah.Base>
    fun findByNumberInSurah(number: Int): Ayah.Base

    class RealAyahList(private val ayahs: List<Ayah.Base>) : AyahList {
        override fun isEmpty() = ayahs.isEmpty()
        override fun get(index: Int): Ayah.Base = ayahs[index]
        override fun getList(): List<Ayah.Base> = ayahs
        override fun findByNumberInSurah(number: Int): Ayah.Base =
            ayahs.find { it.numberInSurah().toInt() == number }
                ?: throw IllegalStateException("Ayah with number $number not found")
    }

    object EmptyAyahList : AyahList {
        override fun isEmpty(): Boolean = true
        override fun get(index: Int): Ayah.Base = throw IllegalStateException("get")
        override fun getList(): List<Ayah.Base> = emptyList()
        override fun findByNumberInSurah(number: Int): Ayah.Base = throw IllegalStateException("findByNumberInSurah")
    }
}
