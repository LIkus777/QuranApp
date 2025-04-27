package com.zaur.features.surah.fakes

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc
import com.zaur.domain.apiV4.models.audiofile.ChapterAudioFileV4
import com.zaur.domain.apiV4.models.audiofile.VerseAudioFileV4
import com.zaur.domain.apiV4.models.chapter.ChapterV4
import com.zaur.domain.apiV4.models.juz.JuzV4
import com.zaur.domain.apiV4.models.recitations.RecitationsV4
import com.zaur.domain.apiV4.models.tafsir.SingleTafsirsV4
import com.zaur.domain.apiV4.models.tafsir.TafsirV4
import com.zaur.domain.apiV4.models.tajweed.VerseUthmanTajweedV4
import com.zaur.domain.apiV4.models.translate.SingleTranslationsV4
import com.zaur.domain.apiV4.models.translate.TranslationV4
import com.zaur.domain.apiV4.repository.QuranAudioRepositoryV4
import com.zaur.domain.apiV4.repository.QuranTafsirRepositoryV4
import com.zaur.domain.apiV4.repository.QuranTajweedRepositoryV4
import com.zaur.domain.apiV4.repository.QuranTextRepositoryV4
import com.zaur.domain.apiV4.repository.QuranTranslationRepositoryV4
import com.zaur.domain.storage.QuranStorage
import com.zaur.domain.storage.ReciterStorage
import com.zaur.domain.storage.theme.ThemeStorage
import kotlinx.coroutines.flow.Flow

class FakeThemeStorage() : ThemeStorage {
    override fun getIsDarkTheme(): Boolean {
        TODO("Not yet implemented")
    }

    override fun saveTheme(isDark: Boolean) {
        TODO("Not yet implemented")
    }
}

class FakeReciterStorage() : ReciterStorage {
    override fun saveSelectedReciter(identifier: String) {
        TODO("Not yet implemented")
    }

    override fun getSelectedReciter(): String? {
        TODO("Not yet implemented")
    }
}

class FakeQuranStorage() : QuranStorage {
    override fun getFontSizeArabic(): Float {
        TODO("Not yet implemented")
    }

    override fun getFontSizeRussian(): Float {
        TODO("Not yet implemented")
    }

    override fun saveFontSizeArabic(size: Float) {
        TODO("Not yet implemented")
    }

    override fun saveFontSizeRussian(size: Float) {
        TODO("Not yet implemented")
    }

    override fun saveLastRead(chapterNumber: Int, ayahNumber: Int) {
        TODO("Not yet implemented")
    }

    override fun getLastRead(): Pair<Int, Int> {
        TODO("Not yet implemented")
    }

    override fun setSurahScreenOpened() {
        TODO("Not yet implemented")
    }

    override fun isSurahScreenOpened(): Boolean {
        TODO("Not yet implemented")
    }
}

class FakeQTextRAqc : QuranTextRepositoryAqc {
    override suspend fun getAllChapters(): List<ChapterAqc> {
        TODO("Not yet implemented")
    }

    override suspend fun getArabicChapter(chapterNumber: Int): ArabicChapter {
        TODO("Not yet implemented")
    }

}

class FakeQAudioRAqc : QuranAudioRepositoryAqc {
    override suspend fun getChapterAudioOfReciter(
        chapterNumber: Int,
        reciter: String,
    ): ChapterAudioFile {
        TODO("Not yet implemented")
    }

    override suspend fun getAyahAudioByKey(
        verseKey: String,
        reciter: String,
    ): Ayah {
        TODO("Not yet implemented")
    }
}

class FakeQTranslationRAqc : QuranTranslationRepositoryAqc {
    override suspend fun getTranslationForChapter(
        chapterNumber: Int,
        translator: String,
    ): TranslationAqc {
        TODO("Not yet implemented")
    }
}

class FakeQTextRV4 : QuranTextRepositoryV4 {
    override suspend fun getAllChapters(language: String): List<ChapterV4> {
        TODO("Not yet implemented")
    }

    override suspend fun getChapter(
        chapterNumber: Int, language: String,
    ): ChapterV4 {
        TODO("Not yet implemented")
    }

    override suspend fun getAllJuzs(): List<JuzV4> {
        TODO("Not yet implemented")
    }
}

class FakeQAudioRV4 : QuranAudioRepositoryV4 {
    override suspend fun getRecitations(language: String): List<RecitationsV4> {
        TODO("Not yet implemented")
    }

    override suspend fun getChapterAudioOfReciter(
        reciterId: Int, chapterNumber: Int,
    ): ChapterAudioFileV4 {
        TODO("Not yet implemented")
    }

    override suspend fun getVerseAudioFile(
        reciterId: Int, verseKey: String,
    ): VerseAudioFileV4 {
        TODO("Not yet implemented")
    }
}

class FakeQTajweedRV4 : QuranTajweedRepositoryV4 {
    override suspend fun getUthmanTajweedsForChapter(chapterNumber: Int): List<VerseUthmanTajweedV4> {
        TODO("Not yet implemented")
    }
}

class FakeQTafsirRV4 : QuranTafsirRepositoryV4 {
    override suspend fun getTafsirForChapter(
        tafsirId: Int, chapterNumber: Int,
    ): SingleTafsirsV4 {
        TODO("Not yet implemented")
    }

    override suspend fun getAvailableTafsirs(language: String): List<TafsirV4> {
        TODO("Not yet implemented")
    }
}

class FakeQTranslationRV4 : QuranTranslationRepositoryV4 {
    override suspend fun getTranslationForChapter(translationId: Int): SingleTranslationsV4 {
        TODO("Not yet implemented")
    }

    override suspend fun getAvailableTranslations(language: String): List<TranslationV4> {
        TODO("Not yet implemented")
    }
}