package com.zaur.features.surah.fakes

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.repository.OfflineRepository
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc
import com.zaur.domain.storage.QuranStorage
import com.zaur.domain.storage.ReciterStorage
import com.zaur.domain.storage.theme.ThemeStorage
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.screen.surah_detail.player.SurahPlayer
import com.zaur.features.surah.viewmodel.QuranAudioViewModel

/**
* @author Zaur
* @since 2025-05-12
*/

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

class FakeOfflineRepos : OfflineRepository {
    override fun isOffline(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setIsOffline(isOffline: Boolean) {
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

class FakeQTextRAqcLocal : QuranTextRepositoryAqc.Local {
    override suspend fun getAllChaptersLocal(): List<ChapterAqc.Base> {
        TODO("Not yet implemented")
    }

    override suspend fun getArabicChapterLocal(chapterNumber: Int): ArabicChapter.Base {
        TODO("Not yet implemented")
    }
}

class FakeQTextRAqcCloud : QuranTextRepositoryAqc.Cloud {
    override suspend fun getAllChaptersCloud(): List<ChapterAqc.Base> {
        TODO("Not yet implemented")
    }

    override suspend fun getArabicChapterCloud(chapterNumber: Int): ArabicChapter.Base {
        TODO("Not yet implemented")
    }
}

class FakeReciterManager() : ReciterManager {
    override fun getReciter(): String? {
        TODO("Not yet implemented")
    }

    override fun saveReciter(identifier: String) {
        TODO("Not yet implemented")
    }

    override fun getReciterName(): String? {
        TODO("Not yet implemented")
    }
}

class FakeSurahPlayer() : SurahPlayer {
    override fun onPlayVerse(verse: VerseAudioAqc) {
        TODO("Not yet implemented")
    }

    override fun onPlayWholeClicked() {
        TODO("Not yet implemented")
    }

    override fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int) {
        TODO("Not yet implemented")
    }

    override fun onAudioEnded() {
        TODO("Not yet implemented")
    }

    override fun onPauseClicked() {
        TODO("Not yet implemented")
    }

    override fun onStopClicked() {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun setQuranAudioVmCallback(callback: QuranAudioViewModel.QuranAudioVmCallback) {
        TODO("Not yet implemented")
    }

    override fun setAyahs(ayahs: List<Ayah.Base>) {
        TODO("Not yet implemented")
    }

    override fun setCacheAudios(ayahs: List<CacheAudio.Base>) {
        TODO("Not yet implemented")
    }
}

class FakeQAudioRAqcLocal : QuranAudioRepositoryAqc.Local {
    override suspend fun getChapterAudioOfReciterLocal(
        chapterNumber: Int,
        reciter: String,
    ): ChapterAudioFile.Base {
        TODO("Not yet implemented")
    }

    override suspend fun getAyahAudioByKeyLocal(
        verseKey: String,
        reciter: String,
    ): VerseAudioAqc.Base {
        TODO("Not yet implemented")
    }
}

class FakeQAudioRAqcCloud : QuranAudioRepositoryAqc.Cloud {
    override suspend fun downloadToCache(
        chapterNumber: Int,
        reciter: String,
    ): List<CacheAudio.Base> {
        TODO("Not yet implemented")
    }

    override suspend fun getChapterAudioOfReciterCloud(
        chapterNumber: Int,
        reciter: String,
    ): ChapterAudioFile.Base {
        TODO("Not yet implemented")
    }

    override suspend fun getAyahAudioByKeyCloud(
        verseKey: String,
        reciter: String,
    ): VerseAudioAqc.Base {
        TODO("Not yet implemented")
    }
}

class FakeQTranslationRAqcLocal : QuranTranslationRepositoryAqc.Local {
    override suspend fun getTranslationForChapterLocal(
        chapterNumber: Int,
        translator: String,
    ): TranslationAqc.Base {
        TODO("Not yet implemented")
    }
}

class FakeQTranslationRAqcCloud : QuranTranslationRepositoryAqc.Cloud {
    override suspend fun getTranslationForChapterCloud(
        chapterNumber: Int,
        translator: String,
    ): TranslationAqc.Base {
        TODO("Not yet implemented")
    }
}