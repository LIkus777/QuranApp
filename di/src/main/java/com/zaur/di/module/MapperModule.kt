package com.zaur.di.module

import com.zaur.data.room.models.mappers.arabic.ArabicAyahMapper
import com.zaur.data.room.models.mappers.arabic.ArabicMapper
import com.zaur.data.room.models.mappers.arabic.EditionArabicMapper
import com.zaur.data.room.models.mappers.audiofile.AyahAudioMapper
import com.zaur.data.room.models.mappers.audiofile.ChapterAudioMapper
import com.zaur.data.room.models.mappers.audiofile.EditionAudioMapper
import com.zaur.data.room.models.mappers.audiofile.EditionVerseMapper
import com.zaur.data.room.models.mappers.audiofile.SurahMapper
import com.zaur.data.room.models.mappers.audiofile.VerseAudioMapper
import com.zaur.data.room.models.mappers.audiofile.VerseAudioWithSurahMapper
import com.zaur.data.room.models.mappers.chapter.ChapterMapper
import com.zaur.data.room.models.mappers.translate.EditionTranslationMapper
import com.zaur.data.room.models.mappers.translate.TranslationAyahMapper
import com.zaur.data.room.models.mappers.translate.TranslationMapper
import com.zaur.di.provides.ProvideArabicAyahMapper
import com.zaur.di.provides.ProvideArabicMapper
import com.zaur.di.provides.ProvideAyahAudioMapper
import com.zaur.di.provides.ProvideChapterAudioMapper
import com.zaur.di.provides.ProvideChapterMapper
import com.zaur.di.provides.ProvideEditionArabicMapper
import com.zaur.di.provides.ProvideEditionAudioMapper
import com.zaur.di.provides.ProvideEditionTranslationMapper
import com.zaur.di.provides.ProvideEditionVerseMapper
import com.zaur.di.provides.ProvideSurahMapper
import com.zaur.di.provides.ProvideTranslationAyahMapper
import com.zaur.di.provides.ProvideTranslationMapper
import com.zaur.di.provides.ProvideVerseAudioMapper
import com.zaur.di.provides.ProvideVerseAudioWithSurahMapper

interface MapperModule : ProvideTranslationMapper, ProvideTranslationAyahMapper,
    ProvideEditionTranslationMapper, ProvideChapterMapper, ProvideVerseAudioWithSurahMapper,
    ProvideVerseAudioMapper, ProvideSurahMapper, ProvideEditionVerseMapper,
    ProvideEditionAudioMapper, ProvideAyahAudioMapper, ProvideEditionArabicMapper,
    ProvideArabicMapper, ProvideArabicAyahMapper, ProvideChapterAudioMapper {

    class Base : MapperModule {
        private val translationMapper by lazy { TranslationMapper.Base(translationAyahMapper, editionTranslationMapper) }
        private val translationAyahMapper by lazy { TranslationAyahMapper.Base() }
        private val editionTranslationMapper by lazy { EditionTranslationMapper.Base() }
        private val chapterMapper by lazy { ChapterMapper.Base() }
        private val verseAudioWithSurahMapper by lazy { VerseAudioWithSurahMapper.Base(verseAudioMapper, surahMapper) }
        private val verseAudioMapper by lazy { VerseAudioMapper.Base(surahMapper, editionVerseMapper) }
        private val surahMapper by lazy { SurahMapper.Base() }
        private val editionVerseMapper by lazy { EditionVerseMapper.Base() }
        private val editionAudioMapper by lazy { EditionAudioMapper.Base() }
        private val ayahAudioMapper by lazy { AyahAudioMapper.Base() }
        private val editionArabicMapper by lazy { EditionArabicMapper.Base() }
        private val arabicMapper by lazy { ArabicMapper.Base(arabicAyahMapper, editionArabicMapper) }
        private val arabicAyahMapper by lazy { ArabicAyahMapper.Base() }
        private val chapterAudioMapper by lazy { ChapterAudioMapper.Base(ayahAudioMapper, editionAudioMapper) }

        override fun provideTranslationMapper(): TranslationMapper = translationMapper
        override fun provideTranslationAyahMapper(): TranslationAyahMapper = translationAyahMapper
        override fun provideEditionTranslationMapper(): EditionTranslationMapper = editionTranslationMapper
        override fun provideChapterMapper(): ChapterMapper = chapterMapper
        override fun provideVerseAudioWithSurahMapper(): VerseAudioWithSurahMapper = verseAudioWithSurahMapper
        override fun provideVerseAudioMapper(): VerseAudioMapper = verseAudioMapper
        override fun provideSurahMapper(): SurahMapper = surahMapper
        override fun provideEditionVerseMapper(): EditionVerseMapper = editionVerseMapper
        override fun provideEditionAudioMapper(): EditionAudioMapper = editionAudioMapper
        override fun provideAyahAudioMapper(): AyahAudioMapper = ayahAudioMapper
        override fun provideEditionArabicMapper(): EditionArabicMapper = editionArabicMapper
        override fun provideArabicMapper(): ArabicMapper = arabicMapper
        override fun provideArabicAyahMapper(): ArabicAyahMapper = arabicAyahMapper
        override fun provideChapterAudioMapper(): ChapterAudioMapper = chapterAudioMapper
    }
}