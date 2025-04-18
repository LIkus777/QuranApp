package com.zaur.data.room.repository

import android.util.Log
import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.network.retryWithBackoff
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.repository.MainRepository

class MainRepositoryLoadImpl(
    private val quranApiAqc: QuranApiAqc,
) : MainRepository.Load {

    override suspend fun loadChapters(): List<ChapterAqc> = quranApiAqc.getAllChapters().chapters

    override suspend fun loadChaptersArabic(chaptersNumbers: IntRange): List<ArabicChapter> {
        val result = mutableListOf<ArabicChapter>()
        chaptersNumbers.forEach {
            val item = retryWithBackoff {
                quranApiAqc.getArabicChapter(it).arabicChapters
            }
            result.add(item)
        }
        return result
    }

    override suspend fun loadChaptersAudio(
        chaptersNumbers: IntRange,
        reciter: String,
    ): List<ChapterAudioFile> {
        val result = mutableListOf<ChapterAudioFile>()
        chaptersNumbers.forEach {
            val item = retryWithBackoff {
                quranApiAqc.getChapterAudioOfReciter(it, reciter).chapterAudio
            }
            result.add(item.copy(reciter = reciter))
            Log.i("TAG", "loadChaptersAudio:  added")
        }
        return result
    }

    override suspend fun loadChaptersTranslate(
        chaptersNumbers: IntRange,
        translator: String,
    ): List<TranslationAqc> {
        val result = mutableListOf<TranslationAqc>()
        chaptersNumbers.forEach {
            val item = retryWithBackoff {
                quranApiAqc.getTranslationForChapter(it, translator).translations
            }
            result.add(item.copy(translator = translator))
        }
        return result
    }
}