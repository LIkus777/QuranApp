package com.zaur.data.al_quran_aqc.repository_impl.cloud

import android.util.Log
import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.network.retryWithBackoff
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import com.zaur.domain.al_quran_cloud.repository.MainRepository

class MainRepositoryLoadImpl(
    private val quranApiAqc: QuranApiAqc,
) : MainRepository.Load {

    override suspend fun loadChapters(): List<ChapterAqc> = quranApiAqc.getAllChapters().chapters()

    override suspend fun loadChaptersArabic(chaptersNumbers: IntRange): List<ArabicChapter> {
        return chaptersNumbers.map { chapterNumber ->
            retryWithBackoff {
                quranApiAqc.getArabicChapter(chapterNumber)
                    .map(ArabicChaptersAqc.Mapper.ArabicChapters())
            }
        }
    }

    override suspend fun loadVersesAudio(
        chaptersNumbers: IntRange,
        reciter: String,
    ): List<VerseAudioAqc> {
        return emptyList<VerseAudioAqc>()
        /*retryWithBackoff { //TODO
            quranApiAqc.getAyahAudioByKey()
        }*/
    }

    override suspend fun loadChaptersAudio(
        chaptersNumbers: IntRange,
        reciter: String,
    ): List<ChapterAudioFile> {
        return chaptersNumbers.map { chapterNumber ->
            Log.i("TAG", "Loading chapter: $chapterNumber for reciter: $reciter")

            retryWithBackoff {
                quranApiAqc.getChapterAudioOfReciter(chapterNumber, reciter)
                    .map(ChapterAudiosFileAqc.Mapper.ChapterAudio())
            }
        }
    }

    override suspend fun loadChaptersTranslate(
        chaptersNumbers: IntRange,
        translator: String,
    ): List<TranslationAqc> {
        val result = mutableListOf<TranslationAqc>()
        chaptersNumbers.forEach { chapterNumber ->
            val item = retryWithBackoff {
                quranApiAqc.getTranslationForChapter(chapterNumber, translator)
                    .map(TranslationsChapterAqc.Mapper.Translations())
            }
            result.add(item.withTranslator(translator))
        }
        return result
    }
}