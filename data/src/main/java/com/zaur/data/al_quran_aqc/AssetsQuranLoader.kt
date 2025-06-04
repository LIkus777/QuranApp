package com.zaur.data.al_quran_aqc

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import java.io.InputStreamReader


/**
 * @author Zaur
 * @since 04.06.2025
 */

interface AssetsQuranLoader {

    fun getAllChapters(): List<ArabicChapter.Base>
    fun getArabicChapter(chapterNumber: Int): ArabicChapter.Base

    class Base(
        private val context: Context,
    ) : AssetsQuranLoader {

        private val gson = Gson()

        // Кэшируем загруженные суры
        private val cachedSurahs: List<ArabicChapter.Base> by lazy {
            context.assets.open("quran-uthmani.json").use { inputStream ->
                InputStreamReader(inputStream, Charsets.UTF_8).use { reader ->
                    val response = gson.fromJson(reader, ArabicChaptersAqc.Base::class.java)
                    // теперь data() вернёт DataWithSurahs, у которого есть поле surahs
                    response.arabicChapters().surahs()
                }
            }
        }

        override fun getAllChapters(): List<ArabicChapter.Base> {
            Log.i("TAG", "getAllChapters: cachedSurahs $cachedSurahs")
            return cachedSurahs
        }

        override fun getArabicChapter(chapterNumber: Int): ArabicChapter.Base {
            return cachedSurahs.first { it.number().toInt() == chapterNumber }
        }
    }
}