package com.zaur.data.room.models.mappers.audiofile

import com.zaur.data.room.models.AyahAudioEntity
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah

/**
* @author Zaur
* @since 2025-05-12
*/

interface AyahAudioMapper {

    fun toData(ayah: Ayah.Base): AyahAudioEntity.Base
    fun fromData(entity: AyahAudioEntity.Base): Ayah.Base

    class Base : AyahAudioMapper {
        override fun toData(ayah: Ayah.Base): AyahAudioEntity.Base = AyahAudioEntity.Base(
            ayah.audio(),
            ayah.verseNumber(),
            ayah.audio(),
            ayah.audioSecondary(),
            ayah.text(),
            ayah.numberInSurah(),
            ayah.juz(),
            ayah.manzil(),
            ayah.page(),
            ayah.ruku(),
            ayah.hizbQuarter(),
            ayah.sajda(),
        )

        override fun fromData(entity: AyahAudioEntity.Base): Ayah.Base = Ayah.Base(
            entity.audio(),
            entity.verseNumber(),
            entity.audio(),
            entity.audioSecondary(),
            entity.text(),
            entity.numberInSurah(),
            entity.juz(),
            entity.manzil(),
            entity.page(),
            entity.ruku(),
            entity.hizbQuarter(),
            entity.sajda(),
        )
    }

}