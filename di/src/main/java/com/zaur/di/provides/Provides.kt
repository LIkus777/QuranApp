package com.zaur.di.provides

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.downloader.AudioDownloader
import com.zaur.data.room.dao.ArabicChapterDao
import com.zaur.data.room.dao.ChapterAudioDao
import com.zaur.data.room.dao.ChapterDao
import com.zaur.data.room.dao.TranslationChapterDao
import com.zaur.data.room.dao.VerseAudioDao
import com.zaur.data.room.database.AppDatabase
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
import com.zaur.domain.al_quran_cloud.repository.MainRepository
import com.zaur.domain.al_quran_cloud.repository.OfflineRepository
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepository
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepository
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepository
import com.zaur.domain.al_quran_cloud.use_case.MainUseCase
import com.zaur.domain.al_quran_cloud.use_case.OfflineUseCase
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCase
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCase
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCase
import com.zaur.domain.al_quran_cloud.use_case.ReciterUseCase
import com.zaur.domain.storage.QuranStorage
import com.zaur.domain.storage.ReciterStorage
import com.zaur.domain.storage.theme.ThemeStorage
import com.zaur.domain.storage.theme.ThemeUseCase
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.screen.surah_detail.SurahDetailStateManager
import com.zaur.features.surah.screen.surah_detail.player.AudioPlaybackHelper
import com.zaur.features.surah.screen.surah_detail.player.AudioPlayerStateUpdater
import com.zaur.features.surah.screen.surah_detail.player.PlaylistBuilder
import com.zaur.features.surah.screen.surah_detail.player.SurahPlayer
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.factory.QuranAudioViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTextViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTranslationViewModelFactory
import com.zaur.features.surah.viewmodel.factory.SurahChooseViewModelFactory

/**
* @author Zaur
* @since 2025-05-12
*/

interface ProvideChapterAudioMapper {
    fun provideChapterAudioMapper(): ChapterAudioMapper
}

interface ProvideArabicAyahMapper {
    fun provideArabicAyahMapper(): ArabicAyahMapper
}

interface ProvideArabicMapper {
    fun provideArabicMapper(): ArabicMapper
}

interface ProvideEditionArabicMapper {
    fun provideEditionArabicMapper(): EditionArabicMapper
}

interface ProvideAyahAudioMapper {
    fun provideAyahAudioMapper(): AyahAudioMapper
}

interface ProvideEditionAudioMapper {
    fun provideEditionAudioMapper(): EditionAudioMapper
}

interface ProvideEditionVerseMapper {
    fun provideEditionVerseMapper(): EditionVerseMapper
}

interface ProvideSurahMapper {
    fun provideSurahMapper(): SurahMapper
}

interface ProvideVerseAudioMapper {
    fun provideVerseAudioMapper(): VerseAudioMapper
}

interface ProvideVerseAudioWithSurahMapper {
    fun provideVerseAudioWithSurahMapper(): VerseAudioWithSurahMapper
}

interface ProvideChapterMapper {
    fun provideChapterMapper(): ChapterMapper
}

interface ProvideEditionTranslationMapper {
    fun provideEditionTranslationMapper(): EditionTranslationMapper
}

interface ProvideTranslationAyahMapper {
    fun provideTranslationAyahMapper(): TranslationAyahMapper
}

interface ProvideTranslationMapper {
    fun provideTranslationMapper(): TranslationMapper
}

interface ProvideQuranTextViewModelFactory {
    fun provideQuranTextViewModelFactory(): QuranTextViewModelFactory
}

interface ProvideQuranAudioViewModelFactory {
    fun provideQuranAudioViewModelFactory(): QuranAudioViewModelFactory
}

interface ProvideTranslationViewModelFactory {
    fun provideQuranTranslationViewModelFactory(): QuranTranslationViewModelFactory
}

interface ProvideSurahChooseViewModelFactory {
    fun provideSurahChooseViewModelFactory(): SurahChooseViewModelFactory
}

interface ProvideSurahDetailViewModel {
    fun provideSurahDetailViewModel(): SurahDetailViewModel
}

interface ProvideSurahPlayer {
    fun provideSurahPlayer(): SurahPlayer
}

interface ProvideSurahDetailStateManager {
    fun provideSurahDetailStateManager(): SurahDetailStateManager
}

interface ProvideAudioPlayer {
    fun provideAudioPlayer(): AudioPlayer
}

interface ProvidePlaylistBuilder {
    fun providePlaylistBuilder(): PlaylistBuilder
}

interface ProvideAudioPlayerStateUpdater {
    fun provideAudioPlayerStateUpdater(): AudioPlayerStateUpdater
}

interface ProvideAudioPlaybackHelper {
    fun provideAudioPlaybackHelper(): AudioPlaybackHelper
}

interface ProvideAudioDownloader {
    fun provideAudioDownloader(): AudioDownloader
}

interface ProvideMainRepositorySave {
    fun provideMainRepositorySave(): MainRepository.Save
}

interface ProvideMainRepositoryLoad {
    fun provideMainRepositoryLoad(): MainRepository.Load
}

interface ProvideTranslationChapterDao {
    fun provideTranslationChapterDao(): TranslationChapterDao
}

interface ProvideArabicChapterDao {
    fun provideArabicChapterDao(): ArabicChapterDao
}

interface ProvideVerseAudioDao {
    fun provideVerseAudioDao(): VerseAudioDao
}

interface ProvideChapterAudioDao {
    fun provideChapterAudioDao(): ChapterAudioDao
}

interface ProvideChapterDao {
    fun provideChapterDao(): ChapterDao
}

interface ProvideAppDatabase {
    fun provideAppDatabase(): AppDatabase
}

interface ProvideThemeStorage {
    fun provideThemeStorage(): ThemeStorage
}

interface ProvideQuranStorage {
    fun provideQuranStorage(): QuranStorage
}

interface ProvideReciterStorage {
    fun provideReciterStorage(): ReciterStorage
}

interface ProvideReciterManager {
    fun provideReciterManager(): ReciterManager
}

interface ProvideOfflineUseCase {
    fun provideOfflineUseCase(): OfflineUseCase
}

interface ProvideReciterUseCase {
    fun provideReciterUseCase(): ReciterUseCase
}

interface ProvideThemeUseCase {
    fun provideThemeUseCase(): ThemeUseCase
}

interface ProvideMainUseCase {
    fun provideMainUseCase(): MainUseCase
}

interface ProvideQuranAudioUseCaseAqc {
    fun provideQuranAudioUseCaseAqc(): QuranAudioUseCase
}

interface ProvideQuranTextUseCaseAqc {
    fun provideQuranTextUseCaseAqc(): QuranTextUseCase
}

interface ProvideQuranTranslationUseCaseAqc {
    fun provideQuranTranslationUseCaseAqc(): QuranTranslationUseCase
}

interface ProvideQuranApiAqc {
    fun provideQuranApiAqc(): QuranApiAqc
}

interface ProvideQuranAudioRepositoryAqcCloud {
    fun provideQuranAudioRepositoryAqcCloud(): QuranAudioRepository.Cloud
}

interface ProvideQuranAudioRepositoryAqcLocal {
    fun provideQuranAudioRepositoryAqcLocal(): QuranAudioRepository.Local
}

interface ProvideOfflineRepository {
    fun provideOfflineRepository(): OfflineRepository
}

interface ProvideQuranTextRepositoryAqcLocal {
    fun provideQuranTextRepositoryAqcLocal(): QuranTextRepository.Local
}

interface ProvideQuranTextRepositoryAqcCloud {
    fun provideQuranTextRepositoryAqcCloud(): QuranTextRepository.Cloud
}

interface ProvideQuranTranslationRepositoryAqcLocal {
    fun provideQuranTranslationRepositoryAqcLocal(): QuranTranslationRepository.Local
}

interface ProvideQuranTranslationRepositoryAqcCloud {
    fun provideQuranTranslationRepositoryAqcCloud(): QuranTranslationRepository.Cloud
}