package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCase
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.observables.QuranAudioObservable
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.screen.surah_detail.player.SurahPlayer
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranAudioViewModel : QuranAudioObservable.Read {

    fun getReciter(): String?
    fun saveReciter(identifier: String)
    fun getReciterName(): String?

    fun downloadToCache(chapterNumber: Int, reciter: String)
    fun getAyahAudioByKey(verseKey: String, reciter: String)
    fun getChaptersAudioOfReciter(chapterNumber: Int, reciter: String)

    fun setAyahs(ayahs: List<Ayah.Base>)
    fun setCacheAudios(ayahs: List<CacheAudio.Base>)

    fun onPlayWholeClicked()
    fun onPlayVerse(verse: VerseAudioAqc)
    fun onPlaySingleClicked(ayahNumber: Int, chapterNumber: Int)

    fun clear()

    interface QuranAudioVmCallback {
        fun callVerseAudioFile(ayah: Int)
    }

    class Base(
        private val surahPlayer: SurahPlayer,
        private val reciterManager: ReciterManager,
        private val stateManager: SurahDetailStateManager,
        private val observable: QuranAudioObservable.Mutable,
        private val quranAudioUseCase: QuranAudioUseCase,
    ) : BaseViewModel(), QuranAudioViewModel {

        override fun getReciter(): String? = reciterManager.getReciter()

        override fun saveReciter(identifier: String) = reciterManager.saveReciter(identifier)

        override fun getReciterName(): String? = reciterManager.getReciterName()

        override fun downloadToCache(chapterNumber: Int, reciter: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    quranAudioUseCase.downloadToCache(chapterNumber, reciter)
                }
                result.handle(object : HandleResult<List<CacheAudio.Base>> {
                    override fun handleSuccess(data: List<CacheAudio.Base>) {
                        viewModelScope.launch {
                            observable.update(observable.audioState().value.copy(cacheAudios = data))
                        }
                    }
                })
            }
        }

        init {
            surahPlayer.setQuranAudioVmCallback(object : QuranAudioVmCallback {
                override fun callVerseAudioFile(ayah: Int) {
                    Log.i("TAGGG", "callVerseAudioFile CALLED} ayah $ayah")
                    getAyahAudioByKey(
                        "${stateManager.surahDetailState().value.audioPlayerState.currentSurahNumber()}:$ayah",
                        reciterManager.getReciter().toString()
                    )
                }
            })
        }

        override fun audioState(): StateFlow<QuranAudioAqcUIState.Base> = observable.audioState()

        override fun onPlayWholeClicked() {
            surahPlayer.onPlayWholeClicked()
        }

        override fun onPlayVerse(verse: VerseAudioAqc) {
            surahPlayer.onPlayVerse(verse)
        }

        override fun onPlaySingleClicked(ayahNumber: Int, chapterNumber: Int) {
            surahPlayer.onPlaySingleClicked(ayahNumber, chapterNumber)
        }

        override fun clear() {
            surahPlayer.clear()
        }

        override fun getChaptersAudioOfReciter(chapterNumber: Int, reciter: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    quranAudioUseCase.getChapterAudioOfReciter(
                        chapterNumber, reciter
                    )
                }
                result.handle(object : HandleResult<ChapterAudioFile> {
                    override fun handleSuccess(data: ChapterAudioFile) {
                        viewModelScope.launch {
                            data.ayahs().forEach {
                                Log.d("TAG", "getChaptersAudioOfReciter: data - $it")
                            }
                            observable.update(observable.audioState().value.copy(chaptersAudioFile = data))
                        }
                    }
                })
            }
        }

        override fun setAyahs(ayahs: List<Ayah.Base>) {
            surahPlayer.setAyahs(ayahs)
        }

        override fun setCacheAudios(ayahs: List<CacheAudio.Base>) {
            surahPlayer.setCacheAudios(ayahs)
        }

        override fun getAyahAudioByKey(verseKey: String, reciter: String) {
            //todo
            viewModelScope.launch(Dispatchers.IO) {
                val result =
                    launchSafely { quranAudioUseCase.getAyahAudioByKey(verseKey, reciter) }
                result.handle(object : HandleResult<VerseAudioAqc> {
                    override fun handleSuccess(data: VerseAudioAqc) {
                        Log.i("TAGGG", "getVerseAudioFile: data ${data.audio()} ")
                        viewModelScope.launch {
                            if (data.audio() == observable.audioState().value.verseAudioFile().audio()) {
                                val newState = stateManager.surahDetailState().value.copy(
                                    audioPlayerState = stateManager.surahDetailState().value.audioPlayerState.copy(
                                        restartAudio = true
                                    )
                                )
                                stateManager.updateState(newState)
                            } else observable.update(observable.audioState().value.copy(verseAudioFile = data))
                        }
                    }

                    override fun handleError(e: Exception) {
                        Log.i("TAGGG", "getVerseAudioFile: result ${e}")
                        super.handleError(e)
                    }
                })
            }
        }
    }
}