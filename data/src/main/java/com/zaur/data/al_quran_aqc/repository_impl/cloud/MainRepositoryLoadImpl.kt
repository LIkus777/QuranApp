package com.zaur.data.al_quran_aqc.repository_impl.cloud

import android.util.Log
import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.network.retryWithBackoff
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

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
        return CoroutineScope(Dispatchers.IO).async {
            val result = mutableListOf<ChapterAudioFile>()
            Log.i("TAG", "loadChaptersAudio: reciter $reciter")
            chaptersNumbers.forEach { chapterNumber ->
                var item = retryWithBackoff {
                    quranApiAqc.getChapterAudioOfReciter(chapterNumber, reciter).chapterAudio
                }
                val newAyahs = mutableListOf<VerseAudioAqc>()
                item.ayahs.forEach {
                    newAyahs.add(it.copy(reciter = reciter, chapterNumber = chapterNumber.toLong()))
                }
                item = item.copy(
                    ayahs = newAyahs, reciter = reciter
                ) // Исправленная строка: указываем reciter!
                Log.i("TAG", "loadChaptersAudio: item $item")
                result.add(item)
                Log.i("TAG", "loadChaptersAudio:  added")
                Log.i("TAG", "loadChaptersAudio:  result $result")
            }
            return@async result
        }.await()
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