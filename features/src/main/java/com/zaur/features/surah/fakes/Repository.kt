package com.zaur.features.surah.fakes

import com.zaur.domain.models.audiofile.ChaptersAudioFile
import com.zaur.domain.models.audiofile.VerseAudioFile
import com.zaur.domain.models.chapter.Chapter
import com.zaur.domain.models.juz.Juz
import com.zaur.domain.models.recitations.Recitations
import com.zaur.domain.models.tafsir.SingleTafsirs
import com.zaur.domain.models.tafsir.Tafsir
import com.zaur.domain.models.tajweed.VerseUthmanTajweed
import com.zaur.domain.models.translate.SingleTranslations
import com.zaur.domain.models.translate.Translation
import com.zaur.domain.repository.QuranAudioRepository
import com.zaur.domain.repository.QuranTafsirRepository
import com.zaur.domain.repository.QuranTajweedRepository
import com.zaur.domain.repository.QuranTextRepository
import com.zaur.domain.repository.QuranTranslationRepository

class FakeQTextR : QuranTextRepository {
    override suspend fun getAllChapters(language: String): List<Chapter> {
        TODO("Not yet implemented")
    }

    override suspend fun getChapter(
        chapterNumber: Int,
        language: String
    ): Chapter {
        TODO("Not yet implemented")
    }

    override suspend fun getAllJuzs(): List<Juz> {
        TODO("Not yet implemented")
    }
}

class FakeQAudioR : QuranAudioRepository {
    override suspend fun getRecitations(language: String): List<Recitations> {
        TODO("Not yet implemented")
    }

    override suspend fun getChaptersAudioOfReciter(
        reciterId: Int,
        chapterNumber: Int
    ): ChaptersAudioFile {
        TODO("Not yet implemented")
    }

    override suspend fun getVerseAudioFile(
        reciterId: Int,
        verseKey: String
    ): VerseAudioFile {
        TODO("Not yet implemented")
    }
}

class FakeQTajweedR: QuranTajweedRepository {
    override suspend fun getUthmanTajweedsForChapter(chapterNumber: Int): List<VerseUthmanTajweed> {
        TODO("Not yet implemented")
    }
}

class FakeQTafsirR : QuranTafsirRepository {
    override suspend fun getTafsirForChapter(
        tafsirId: Int,
        chapterNumber: Int
    ): SingleTafsirs {
        TODO("Not yet implemented")
    }

    override suspend fun getAvailableTafsirs(language: String): List<Tafsir> {
        TODO("Not yet implemented")
    }
}

class FakeQTranslationR : QuranTranslationRepository {
    override suspend fun getTranslationForChapter(translationId: Int): SingleTranslations {
        TODO("Not yet implemented")
    }

    override suspend fun getAvailableTranslations(language: String): List<Translation> {
        TODO("Not yet implemented")
    }
}