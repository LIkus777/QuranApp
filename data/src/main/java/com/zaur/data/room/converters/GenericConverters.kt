package com.zaur.data.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zaur.data.room.models.ArabicAyahEntity
import com.zaur.data.room.models.AyahAudioEntity
import com.zaur.data.room.models.EditionArabicEntity
import com.zaur.data.room.models.EditionAudioEntity
import com.zaur.data.room.models.EditionVerseEntity
import com.zaur.data.room.models.RevelationType
import com.zaur.data.room.models.SurahEntity

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
    fun fromArabicAyahList(list: List<ArabicAyahEntity>?): String? = gson.toJson(list)

    @TypeConverter
    fun toArabicAyahList(json: String?): List<ArabicAyahEntity>? {
        val type = object : TypeToken<List<ArabicAyahEntity>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromAyahAudioList(list: List<AyahAudioEntity>?): String? = gson.toJson(list)

    @TypeConverter
    fun toAyahAudioList(json: String?): List<AyahAudioEntity>? {
        val type = object : TypeToken<List<AyahAudioEntity>>() {}.type
        return gson.fromJson(json, type)
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
            "Medinan" -> RevelationType.Meccan
            else -> throw IllegalArgumentException()
        }
    }
}
