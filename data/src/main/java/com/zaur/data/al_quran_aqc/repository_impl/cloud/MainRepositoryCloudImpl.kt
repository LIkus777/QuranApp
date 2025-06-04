package com.zaur.data.al_quran_aqc.repository_impl.cloud

import android.util.Log
import com.zaur.data.al_quran_aqc.AssetsQuranLoader
import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.network.retryWithBackoff
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.translate.Translation
import com.zaur.domain.al_quran_cloud.repository.MainRepository

/**
 * @author Zaur
 * @since 2025-05-12
 */

class MainRepositoryCloudImpl(
    private val quranApiAqc: QuranApiAqc,
    private val assetsQuranLoader: AssetsQuranLoader,
) : MainRepository.Cloud {

    override suspend fun loadChapters(): List<ChapterAqc.Base> =
        retryWithBackoff { quranApiAqc.getAllChapters().chapters() }

    override suspend fun loadChaptersArabic(chaptersNumbers: IntRange): List<ArabicChapter.Base> {
        return assetsQuranLoader.getAllChapters()
    }

    override suspend fun loadVersesAudio(
        chaptersNumbers: IntRange,
        reciter: String,
    ): List<VerseAudio.Base> {
        // Пока пусто, если появится реализация — обернуть в retryWithBackoff
        return emptyList()
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
    ): List<Translation.Base> {
        val result = mutableListOf<Translation.Base>()
        chaptersNumbers.forEach { chapterNumber ->
            val item = retryWithBackoff {
                quranApiAqc.getTranslationForChapter(chapterNumber, translator).translations()
            }
            result.add(item.withTranslator(translator))
        }
        return result
    }
}