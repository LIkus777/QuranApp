package com.zaur.data.room.models.mappers.translate

import com.zaur.data.room.models.TranslationAyahEntity
import com.zaur.domain.al_quran_cloud.models.translate.Ayah

interface TranslationAyahMapper {

    fun toData(ayah: Ayah.Base): TranslationAyahEntity.Base
    fun fromData(entity: TranslationAyahEntity.Base): Ayah.Base

    class Base : TranslationAyahMapper {
        override fun toData(ayah: Ayah.Base): TranslationAyahEntity.Base = TranslationAyahEntity.Base(
            ayah.number(),
            ayah.text(),
            ayah.numberInSurah(),
            ayah.juz(),
            ayah.manzil(),
            ayah.page(),
            ayah.ruku(),
            ayah.hizbQuarter(),
            ayah.sajda()
        )

        override fun fromData(entity: TranslationAyahEntity.Base): Ayah.Base = Ayah.Base(
            entity.number,
            entity.text,
            entity.numberInSurah,
            entity.juz,
            entity.manzil,
            entity.page,
            entity.ruku,
            entity.hizbQuarter,
            entity.sajda
        )
    }

}