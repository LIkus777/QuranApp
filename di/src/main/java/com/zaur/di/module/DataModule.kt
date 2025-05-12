package com.zaur.di.module

import android.content.Context
import androidx.room.Room
import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.downloader.AudioDownloader
import com.zaur.data.network.ApiFactory
import com.zaur.data.preferences.OfflinePreferences
import com.zaur.data.preferences.QuranPreferences
import com.zaur.data.preferences.ReciterPreferences
import com.zaur.data.preferences.ThemePreferences
import com.zaur.data.room.constans.DATABASE_NAME
import com.zaur.data.room.dao.ArabicChapterDao
import com.zaur.data.room.dao.ChapterAudioDao
import com.zaur.data.room.dao.ChapterDao
import com.zaur.data.room.dao.TranslationChapterDao
import com.zaur.data.room.dao.VerseAudioDao
import com.zaur.data.room.database.AppDatabase
import com.zaur.di.provides.ProvideAppDatabase
import com.zaur.di.provides.ProvideArabicChapterDao
import com.zaur.di.provides.ProvideAudioDownloader
import com.zaur.di.provides.ProvideChapterAudioDao
import com.zaur.di.provides.ProvideChapterDao
import com.zaur.di.provides.ProvideOfflineRepository
import com.zaur.di.provides.ProvideOfflineUseCase
import com.zaur.di.provides.ProvideQuranApiAqc
import com.zaur.di.provides.ProvideQuranStorage
import com.zaur.di.provides.ProvideReciterStorage
import com.zaur.di.provides.ProvideThemeStorage
import com.zaur.di.provides.ProvideTranslationChapterDao
import com.zaur.di.provides.ProvideVerseAudioDao
import com.zaur.domain.al_quran_cloud.repository.OfflineRepository
import com.zaur.domain.al_quran_cloud.use_case.OfflineUseCase
import com.zaur.domain.storage.QuranStorage
import com.zaur.domain.storage.ReciterStorage
import com.zaur.domain.storage.theme.ThemeStorage

/**
* @author Zaur
* @since 2025-05-12
*/

interface DataModule : ProvideOfflineUseCase, ProvideQuranApiAqc, ProvideAudioDownloader,
    ProvideAppDatabase, ProvideChapterDao, ProvideArabicChapterDao, ProvideVerseAudioDao,
    ProvideChapterAudioDao, ProvideTranslationChapterDao, ProvideReciterStorage,
    ProvideQuranStorage, ProvideThemeStorage, ProvideOfflineRepository {

    class Base(private val context: Context) : DataModule {

        private val database by lazy {
            Room.databaseBuilder(
                context, AppDatabase::class.java, DATABASE_NAME
            ).fallbackToDestructiveMigration() // TODO: Убрать в проде
                .build()
        }

        override fun provideQuranApiAqc(): QuranApiAqc =
            ApiFactory.retrofit.create(QuranApiAqc::class.java)

        override fun provideThemeStorage(): ThemeStorage = ThemePreferences(context)

        override fun provideAudioDownloader(): AudioDownloader = AudioDownloader.Base(context)

        override fun provideAppDatabase(): AppDatabase = database

        override fun provideChapterDao(): ChapterDao = database.chapterDao()

        override fun provideArabicChapterDao(): ArabicChapterDao = database.arabicChapterDao()

        override fun provideVerseAudioDao(): VerseAudioDao = database.verseAudioDao()

        override fun provideChapterAudioDao(): ChapterAudioDao = database.chapterAudioDao()

        override fun provideTranslationChapterDao(): TranslationChapterDao =
            database.translationChapterDao()

        override fun provideQuranStorage(): QuranStorage = QuranPreferences(context)

        override fun provideReciterStorage(): ReciterStorage = ReciterPreferences(context)

        override fun provideOfflineRepository(): OfflineRepository = OfflinePreferences(context)

        override fun provideOfflineUseCase(): OfflineUseCase =
            OfflineUseCase.Base(provideOfflineRepository())
    }
}
