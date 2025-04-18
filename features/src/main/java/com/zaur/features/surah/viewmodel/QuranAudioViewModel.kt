package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.data.al_quran_aqc.constans.ReciterList
import com.zaur.domain.al_quran_cloud.models.arabic.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCaseAqc
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.observables.QuranAudioObservable
import com.zaur.features.surah.screen.SurahDetailStateManager
import com.zaur.features.surah.screen.SurahPlayer
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface QuranAudioViewModel : QuranAudioObservable.Read {

    fun getReciter(): String?
    fun saveReciter(identifier: String)
    fun getReciterName(): String?

    fun getVerseAudioFile(verseKey: String, reciter: String)
    fun getChaptersAudioOfReciter(chapterNumber: Int, reciter: String)

    fun setAyahs(ayahs: List<Ayah>)

    fun onPlayWholeClicked()
    fun onPlayVerse(verse: VersesAudioFileAqc)
    fun onPlaySingleClicked(ayahNumber: Int, chapterNumber: Int)

    fun clear()

    interface QuranAudioVmCallback {
        fun callVerseAudioFile(ayah: Int)
    }

    class Base(
        private val audioPlayer: AudioPlayer,
        private val stateManager: SurahDetailStateManager,
        private val observable: QuranAudioObservable.Mutable,
        private val quranAudioUseCaseAqc: QuranAudioUseCaseAqc,
    ) : BaseViewModel(), QuranAudioViewModel {

        private val surahPlayer = SurahPlayer.Base(audioPlayer, stateManager)

        init {
            surahPlayer.setQuranAudioVmCallback(object : QuranAudioVmCallback {
                override fun callVerseAudioFile(ayah: Int) {
                    Log.i("TAGGG", "callVerseAudioFile CALLED}")
                    getVerseAudioFile(
                        "${stateManager.getState().value.audioPlayerState.currentSurahNumber}:$ayah",
                        getReciter().toString()
                    )
                }
            })
        }

        override fun audioState(): StateFlow<QuranAudioAqcUIState> = observable.audioState()

        override fun saveReciter(identifier: String) {
            quranAudioUseCaseAqc.saveReciter(identifier)
        }

        override fun getReciter(): String? = quranAudioUseCaseAqc.getReciter()

        override fun getReciterName(): String? {
            val identifier = getReciter()
            return ReciterList.reciters[identifier] // Берем имя чтеца из списка
        }

        override fun onPlayWholeClicked() {
            surahPlayer.onPlayWholeClicked()
        }

        override fun onPlayVerse(verse: VersesAudioFileAqc) {
            surahPlayer.onPlayVerse(verse)
        }

        override fun onPlaySingleClicked(ayahNumber: Int, chapterNumber: Int) {
            surahPlayer.onPlaySingleClicked(ayahNumber, chapterNumber)
        }

        override fun clear() {
            surahPlayer.clear()
        }

        override fun getChaptersAudioOfReciter(chapterNumber: Int, reciter: String) {
            //TODO ПЕРЕДЕЛАТЬ ЧТОБЫ ССЫЛКИ НА АУДИО БРАЛИСЬ ОТСЮДА ДЛЯ СУРЫ, А НЕ ДЕРГАЛИСЬ ПО 1
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    quranAudioUseCaseAqc.getChapterAudioOfReciter(
                        chapterNumber, reciter
                    )
                }
                result.handle(object : HandleResult<ChapterAudioFile> {
                    override fun handleSuccess(data: ChapterAudioFile) {
                        viewModelScope.launch {
                            observable.update(observable.state().value.copy(chaptersAudioFile = data))
                        }
                    }

                    override fun handleError(e: Exception) {
                        super.handleError(e)
                    }
                })
            }
        }

        override fun setAyahs(ayahs: List<Ayah>) {
            surahPlayer.setAyahs(ayahs)
        }

        override fun getVerseAudioFile(verseKey: String, reciter: String) {
            //todo
            /*
                        viewModelScope.launch(Dispatchers.IO) {
                            val result =
                                launchSafely { quranAudioUseCaseAqc.getVerseAudioFile(verseKey, reciter) }
                            result.handle(object : HandleResult<VerseAudioAqc> {
                                override fun handleSuccess(data: VerseAudioAqc) {
                                    viewModelScope.launch {
                                        if (data == observable.state().value.verseAudioFile) {
                                            val newState = stateManager.getState().value.copy(
                                                audioPlayerState = stateManager.getState().value.audioPlayerState.copy(
                                                    restartAudio = true
                                                )
                                            )
                                            stateManager.updateState(newState)
                                        } else observable.update(observable.state().value.copy(verseAudioFile = data))
                                    }
                                }

                                override fun handleError(e: Exception) {
                                    super.handleError(e)
                                }
                            })
                        }
            */
        }
    }
}