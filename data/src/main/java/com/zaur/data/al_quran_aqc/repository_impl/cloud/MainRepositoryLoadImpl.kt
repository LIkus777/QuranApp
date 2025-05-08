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

class MainRepositoryLoadImpl(
    private val quranApiAqc: QuranApiAqc,
) : MainRepository.Load {

    override suspend fun loadChapters(): List<ChapterAqc.Base> =
        quranApiAqc.getAllChapters().chapters()

    override suspend fun loadChaptersArabic(chaptersNumbers: IntRange): List<ArabicChapter.Base> {
        return chaptersNumbers.map { chapterNumber ->
            retryWithBackoff {
                quranApiAqc.getArabicChapter(chapterNumber).arabicChapters()
            }
        }
    }

    override suspend fun loadVersesAudio(
        chaptersNumbers: IntRange,
        reciter: String,
    ): List<VerseAudioAqc.Base> {
        return emptyList<VerseAudioAqc.Base>()/*retryWithBackoff { //TODO
            quranApiAqc.getAyahAudioByKey()
        }*/
    }

    override suspend fun loadChaptersAudio(
        chaptersNumbers: IntRange,
        reciter: String,
    ): List<ChapterAudioFile.Base> {
        return chaptersNumbers.map { chapterNumber ->
            Log.i("TAG", "Loading chapter: $chapterNumber for reciter: $reciter")
            retryWithBackoff {
                quranApiAqc.getChapterAudioOfReciter(chapterNumber, reciter).chapterAudio()
            }
        }
    }

    override suspend fun loadChaptersTranslate(
        chaptersNumbers: IntRange,
        translator: String,
    ): List<TranslationAqc.Base> {
        val result = mutableListOf<TranslationAqc.Base>()
        chaptersNumbers.forEach { chapterNumber ->
            val item = retryWithBackoff {
                quranApiAqc.getTranslationForChapter(chapterNumber, translator).translations()
            }
            result.add(item.withTranslator(translator))
        }
        return result
    }
}