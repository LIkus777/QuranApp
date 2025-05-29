package com.zaur.data.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zaur.data.room.models.ArabicAyahEntity
import com.zaur.data.room.models.AyahAudioEntity
import com.zaur.data.room.models.EditionArabicEntity
import com.zaur.data.room.models.EditionAudioEntity
import com.zaur.data.room.models.EditionTranslationEntity
import com.zaur.data.room.models.EditionVerseEntity
import com.zaur.data.room.models.SurahEntity
import com.zaur.data.room.models.TranslationAyahEntity
import com.zaur.domain.al_quran_cloud.models.chapter.RevelationType

/**
* @author Zaur
* @since 2025-05-12
*/

class GenericConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(list: List<String>?): String? = gson.toJson(list)

    @TypeConverter
    fun toStringList(json: String?): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromArabicAyahList(list: List<ArabicAyahEntity.Base>?): String? = gson.toJson(list)

    @TypeConverter
    fun toArabicAyahList(json: String?): List<ArabicAyahEntity.Base>? {
        val type = object : TypeToken<List<ArabicAyahEntity.Base>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromEdition(edition: EditionArabicEntity.Base): String {
        return gson.toJson(edition)
    }

    @TypeConverter
    fun toEdition(json: String): EditionArabicEntity.Base {
        return gson.fromJson(json, EditionArabicEntity.Base::class.java)
    }

    @TypeConverter
    fun fromEditionArabic(edition: EditionArabicEntity?): String? = gson.toJson(edition)

    @TypeConverter
    fun toEditionArabic(json: String?): EditionArabicEntity? =
        gson.fromJson(json, EditionArabicEntity::class.java)

    @TypeConverter
    fun fromEditionVerse(edition: EditionVerseEntity?): String? = gson.toJson(edition)

    @TypeConverter
    fun toEditionVerse(json: String?): EditionVerseEntity? =
        gson.fromJson(json, EditionVerseEntity::class.java)

    @TypeConverter
    fun fromEditionAudio(edition: EditionAudioEntity?): String? = gson.toJson(edition)

    @TypeConverter
    fun toEditionAudio(json: String?): EditionAudioEntity? =
        gson.fromJson(json, EditionAudioEntity::class.java)

    @TypeConverter
    fun fromSurah(surah: SurahEntity?): String? = gson.toJson(surah)

    @TypeConverter
    fun toSurah(json: String?): SurahEntity? = gson.fromJson(json, SurahEntity::class.java)

    @TypeConverter
    fun fromType(type: RevelationType): String = type.value

    @TypeConverter
    fun toType(value: String): RevelationType {
        return when (value) {
            "Meccan" -> RevelationType.Meccan
            "Medinan" -> RevelationType.Medinan
            else -> throw IllegalArgumentException("Unknown RevelationType: $value")
        }
    }

    // Non-nullable version for TranslationAyahEntity
    @TypeConverter
    fun fromTranslationAyahList(list: List<TranslationAyahEntity.Base>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toTranslationAyahList(json: String): List<TranslationAyahEntity.Base> {
        val type = object : TypeToken<List<TranslationAyahEntity.Base>>() {}.type
        return gson.fromJson(json, type)
    }

    // Nullable version for TranslationAyahEntity (added to avoid signature clash)
    @TypeConverter
    fun fromNullableTranslationAyahList(list: List<TranslationAyahEntity.Base>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toNullableTranslationAyahList(json: String?): List<TranslationAyahEntity.Base>? {
        val type = object : TypeToken<List<TranslationAyahEntity.Base>>() {}.type
        return gson.fromJson(json, type)
    }

    // EditionTranslationEntity converter methods
    @TypeConverter
    fun fromEditionTranslation(edition: EditionTranslationEntity?): String? = gson.toJson(edition)

    @TypeConverter
    fun toEditionTranslation(json: String?): EditionTranslationEntity? =
        gson.fromJson(json, EditionTranslationEntity::class.java)

    // EditionVerseEntity.Base converter methods
    @TypeConverter
    fun fromEditionVerseBase(edition: EditionVerseEntity.Base?): String? = gson.toJson(edition)

    @TypeConverter
    fun toEditionVerseBase(json: String?): EditionVerseEntity.Base? =
        gson.fromJson(json, EditionVerseEntity.Base::class.java)

    // Additional methods for EditionTranslationEntity.Base
    @TypeConverter
    fun fromEditionTranslationEntity(value: EditionTranslationEntity.Base): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toEditionTranslationEntity(value: String): EditionTranslationEntity.Base {
        return gson.fromJson(value, EditionTranslationEntity.Base::class.java)
    }

    // Конвертер для списка AyahAudioEntity.Base
    @TypeConverter
    fun fromAyahAudioList(list: List<AyahAudioEntity.Base>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toAyahAudioList(json: String?): List<AyahAudioEntity.Base>? {
        val type = object : TypeToken<List<AyahAudioEntity.Base>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromAudioEdition(edition: EditionAudioEntity.Base): String {
        return gson.toJson(edition)
    }

    @TypeConverter
    fun toAudioEdition(json: String): EditionAudioEntity.Base {
        return gson.fromJson(json, EditionAudioEntity.Base::class.java)
    }
}