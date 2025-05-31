package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCase
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.manager.SurahPlayerStateManager
import com.zaur.features.surah.observables.SurahPlayerObservable
import com.zaur.features.surah.screen.surah_detail.SurahNavigationCallback
import com.zaur.features.surah.screen.surah_detail.player.SurahPlayer
import com.zaur.features.surah.viewmodel.handlers.AudioResultHandler
import com.zaur.presentation.ui.ui_state.SurahPlayerState
import com.zaur.presentation.ui.ui_state.aqc.SurahPlayerUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface SurahPlayerViewModel : SurahPlayerObservable.Read {

    fun surahPlayerState(): StateFlow<SurahPlayerState>

    fun getReciter(): String?
    fun saveReciter(identifier: String)
    fun getReciterName(): String?

    fun downloadToCache(surahNumber: Int, reciter: String)
    fun getAyahAudioByKey(ayahKey: String, reciter: String)
    fun getChaptersAudioOfReciter(surahNumber: Int, reciter: String)

    fun setAyahs(ayahs: List<Ayah.Base>)
    fun setCacheAudios(ayahs: List<CacheAudio.Base>)

    fun onNextAyahClicked()
    fun onNextSurahClicked()
    fun onPreviousAyahClicked()
    fun onPreviousSurahClicked()
    fun onPlayWholeClicked()
    fun onStopClicked()
    fun playNewSurah(surahNumber: Int, reciter: String)
    fun onPlayVerse(verse: VerseAudio)
    fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int)
    fun seekTo(position: Long)

    fun clear()

    fun getAudioSurahNameSharedPref(): String
    fun setAudioSurahNameSharedPref(surahName: String)
    fun getLastPlayedSurah(): Int
    fun setLastPlayedSurah(surahNumber: Int)
    fun getLastPlayedAyah(): Int
    fun setLastPlayedAyah(ayahNumber: Int)

    fun setAudioSurahName(name: String)
    fun setAudioSurahNumber(surahNumber: Int)
    fun setAudioSurahAyah(ayahInSurah: Int)
    fun setOfflineMode(isOffline: Boolean)

    fun setSurahNavigationCallback(callback: SurahNavigationCallback)

    interface SurahPlayerVmCallback {
        fun callVerseAudioFile(ayah: Int)
    }

    class Base(
        private val surahPlayer: SurahPlayer,
        private val reciterManager: ReciterManager,
        private val surahDetailStateManager: SurahDetailStateManager,
        private val surahPlayerStateManager: SurahPlayerStateManager,
        private val observable: SurahPlayerObservable.Mutable,
        private val quranAudioUseCase: QuranAudioUseCase,
    ) : BaseViewModel(), SurahPlayerViewModel {

        override fun surahPlayerState(): StateFlow<SurahPlayerState> = surahPlayerStateManager.surahPlayerState()

        private val resultHandler = AudioResultHandler.Base(
            surahPlayer = surahPlayer,
            observable = observable,
            surahPlayerStateManager = surahPlayerStateManager,
            scope = viewModelScope,
            reciterManager = reciterManager
        )

        init {
            surahPlayer.setQuranAudioVmCallback(object : SurahPlayerVmCallback {
                override fun callVerseAudioFile(ayah: Int) {
                    val surahNumber =
                        surahDetailStateManager.surahDetailState().value.textState().currentSurahNumber()
                    getAyahAudioByKey("$surahNumber:$ayah", getReciter().orEmpty())
                }
            })
        }

        override fun audioState(): StateFlow<SurahPlayerUIState.Base> = observable.audioState()

        override fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int) {
            surahPlayer.onPlaySingleClicked(ayahNumber, surahNumber)
            quranAudioUseCase.setLastPlayedAyah(ayahNumber)
            quranAudioUseCase.setLastPlayedSurah(surahNumber)
            quranAudioUseCase.setAudioSurahName(
                surahPlayerStateManager.surahPlayerState().value.surahName()
            )
            setLastPlayedSurah(surahNumber)
        }

        override fun onPlayVerse(verse: VerseAudio) {
            surahPlayer.onPlayVerse(verse)
            setLastPlayedAyah(verse.numberInSurah().toInt())
            setLastPlayedSurah(verse.surah().number().toInt())
            quranAudioUseCase.setAudioSurahName(
                surahPlayerStateManager.surahPlayerState().value.surahName()
            )
        }

        override fun onPlayWholeClicked() {
            surahPlayer.onPlayWholeClicked()
            val playerState = surahPlayerStateManager.surahPlayerState().value
            val detailState = surahDetailStateManager.surahDetailState().value
            if (playerState.surahName() != "" && playerState
                    .currentSurahNumber() != 0 && playerState.currentAyah() != 0
            ) {
                setLastPlayedAyah(playerState.currentAyah())
                setLastPlayedSurah(playerState.currentSurahNumber())
                setAudioSurahNameSharedPref(playerState.surahName())
            } else {
                setLastPlayedAyah(detailState.textState().currentAyah())
                setLastPlayedSurah(detailState.textState().currentSurahNumber())
                setAudioSurahNameSharedPref(detailState.textState().surahName())
            }
        }

        override fun onStopClicked() {
            surahPlayer.onStopClicked()
        }

        override fun playNewSurah(surahNumber: Int, reciter: String) {
            viewModelScope.launch(Dispatchers.IO) {
                launchSafely {
                    quranAudioUseCase.getChapterAudioOfReciter(surahNumber, reciter)
                }.handle(object : HandleResult<ChapterAudioFile> {
                    override fun handleSuccess(data: ChapterAudioFile) {
                        Log.d("TAG", "playNewSurah: data $data")
                        resultHandler.handleNewSurah(data)
                    }
                })
            }
        }

        override fun getChaptersAudioOfReciter(surahNumber: Int, reciter: String) {
            viewModelScope.launch(Dispatchers.IO) {
                launchSafely {
                    quranAudioUseCase.getChapterAudioOfReciter(surahNumber, reciter)
                }.handle(object : HandleResult<ChapterAudioFile> {
                    override fun handleSuccess(data: ChapterAudioFile) {
                        Log.d("TAG", "getChaptersAudioOfReciter: data $data")
                        resultHandler.handleChapterAudio(data)
                    }
                })
            }
        }

        override fun downloadToCache(surahNumber: Int, reciter: String) {
            viewModelScope.launch(Dispatchers.IO) {
                launchSafely {
                    quranAudioUseCase.downloadToCache(surahNumber, reciter)
                }.handle(object : HandleResult<List<CacheAudio.Base>> {
                    override fun handleSuccess(data: List<CacheAudio.Base>) {
                        resultHandler.handleCacheAudio(data)
                    }
                })
            }
        }

        override fun getAyahAudioByKey(ayahKey: String, reciter: String) {
            viewModelScope.launch(Dispatchers.IO) {
                launchSafely {
                    quranAudioUseCase.getAyahAudioByKey(ayahKey, reciter)
                }.handle(object : HandleResult<VerseAudio> {
                    override fun handleSuccess(data: VerseAudio) {
                        resultHandler.handleVerseAudio(data)
                    }
                })
            }
        }

        override fun seekTo(position: Long) = surahPlayer.seekTo(position)

        override fun clear() = surahPlayer.clear()

        override fun getAudioSurahNameSharedPref(): String = quranAudioUseCase.getAudioSurahName()
        override fun setAudioSurahNameSharedPref(surahName: String) =
            quranAudioUseCase.setAudioSurahName(surahName)

        override fun getLastPlayedSurah(): Int = quranAudioUseCase.getLastPlayedSurah()
        override fun setLastPlayedSurah(surahNumber: Int) =
            quranAudioUseCase.setLastPlayedSurah(surahNumber)

        override fun getLastPlayedAyah(): Int = quranAudioUseCase.getLastPlayedAyah()
        override fun setLastPlayedAyah(ayahNumber: Int) =
            quranAudioUseCase.setLastPlayedAyah(ayahNumber)

        override fun setAudioSurahName(name: String) {
            surahPlayerStateManager.setAudioSurahName(name)
        }
        
        override fun setAudioSurahNumber(surahNumber: Int) {
            surahPlayerStateManager.setAudioSurahNumber(surahNumber)
        }

        override fun setAudioSurahAyah(ayahInSurah: Int) {
            surahPlayerStateManager.setAudioSurahAyah(ayahInSurah)
        }

        override fun setOfflineMode(isOffline: Boolean) {
            surahPlayerStateManager.setOfflineMode(isOffline)
        }

        override fun setSurahNavigationCallback(callback: SurahNavigationCallback) {
            surahPlayer.setSurahNavigationCallback(callback)
        }

        override fun getReciter(): String? = reciterManager.getReciter()
        override fun getReciterName(): String? = reciterManager.getReciterName()
        override fun saveReciter(identifier: String) = reciterManager.saveReciter(identifier)

        override fun setAyahs(ayahs: List<Ayah.Base>) {
            viewModelScope.launch { surahPlayer.setAyahs(ayahs) }
        }

        override fun setCacheAudios(ayahs: List<CacheAudio.Base>) {
            viewModelScope.launch { surahPlayer.setCacheAudios(ayahs) }
        }

        override fun onNextAyahClicked() = surahPlayer.onNextAyahClicked()
        override fun onNextSurahClicked() = surahPlayer.onNextSurahClicked()
        override fun onPreviousAyahClicked() = surahPlayer.onPreviousAyahClicked()
        override fun onPreviousSurahClicked() = surahPlayer.onPreviousSurahClicked()
    }
}