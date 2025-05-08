package com.zaur.data.room.models.mappers.arabic

import com.zaur.data.room.models.ArabicAyahEntity
import com.zaur.domain.al_quran_cloud.models.arabic.Ayah

interface ArabicAyahMapper {

    fun toData(ayah: Ayah.Base): ArabicAyahEntity
    fun fromData(entity: ArabicAyahEntity): Ayah.Base

    class Base : ArabicAyahMapper {
        override fun toData(ayah: Ayah.Base): ArabicAyahEntity = ArabicAyahEntity.Base(
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

        override fun fromData(entity: ArabicAyahEntity): Ayah.Base = Ayah.Base(
            entity.number(),
            entity.text(),
            entity.numberInSurah(),
            entity.juz(),
            entity.manzil(),
            entity.page(),
            entity.ruku(),
            entity.hizbQuarter(),
            entity.sajda()
        )
    }

}