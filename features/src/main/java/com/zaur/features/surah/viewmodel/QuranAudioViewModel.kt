package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.data.al_quran_aqc.constans.ReciterList
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCaseAqc
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.observables.QuranAudioObservable
import com.zaur.features.surah.screen.Player
import com.zaur.features.surah.screen.SurahDetailStateManager
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.get

interface QuranAudioViewModel : QuranAudioObservable.Read {

    fun getReciter(): String?
    fun saveReciter(identifier: String)
    fun getReciterName(): String?

    fun getVerseAudioFile(verseKey: String, reciter: String)
    fun getChaptersAudioOfReciter(chapterNumber: Int, reciter: String)

    fun setAyahs(ayahs: ArabicChaptersAqc)

    fun onPlayWholeClicked()
    fun onPlaySingleClicked(ayahNumber: Int, chapterNumber: Int)

    fun clear()

    interface QuranAudioVmCallback {
        fun callVerseAudioFile(ayah: Int)
    }

    class Base(
        private val audioPlayer: AudioPlayer,
        private val stateManager: SurahDetailStateManager,
        private val observable: QuranAudioObservable.Mutable,
        private val quranAudioUseCaseAqc: QuranAudioUseCaseAqc
    ) : BaseViewModel(), QuranAudioViewModel, QuranAudioObservable.Read {

        private val player = Player.Base(audioPlayer, stateManager)

        init {
            player.setQuranAudioVmCallback(object : QuranAudioVmCallback {
                override fun callVerseAudioFile(ayah: Int) {
                    Log.i("TAGG", "callVerseAudioFile CALLED}")
                    getVerseAudioFile(
                        "${stateManager.getState().value.audioPlayerState.currentSurahNumber}:$ayah",
                        stateManager.getState().value.reciterState.currentReciter.toString()
                    )
                }
            })
        }

        override fun audioState(): State<QuranAudioAqcUIState> = observable.audioState()

        override fun saveReciter(identifier: String) {
            quranAudioUseCaseAqc.saveReciter(identifier)
        }

        override fun getReciter(): String? = quranAudioUseCaseAqc.getReciter()

        override fun getReciterName(): String? {
            val identifier = getReciter()
            Log.i("TAGGG", "getReciterName: ИМЯ ЧТЕЦА = ${ReciterList.reciters[identifier]}")
            return ReciterList.reciters[identifier] // Берем имя чтеца из списка
        }

        override fun onPlayWholeClicked() {
            player.onPlayWholeClicked()
        }

        override fun onPlaySingleClicked(ayahNumber: Int, chapterNumber: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    Log.i("TAGGG", "getReciterName: verseKey = $chapterNumber:$ayahNumber")
                    quranAudioUseCaseAqc.getVerseAudioFile(
                        "$chapterNumber:$ayahNumber",
                        getReciter().toString()
                    )
                }
                result.handle(object : HandleResult<VersesAudioFileAqc> {
                    override fun handleSuccess(data: VersesAudioFileAqc) {
                        viewModelScope.launch {
                            Log.i("TAGGG", "getReciterName: data.versesAudio = ${data.versesAudio}")
                            player.onPlaySingleClicked(ayahNumber, chapterNumber, data.versesAudio.audio)
                        }
                    }

                    override fun handleError(e: Exception) {
                        super.handleError(e)
                    }
                })
            }
        }

        override fun clear() {
            player.clear()
        }

        override fun getChaptersAudioOfReciter(chapterNumber: Int, reciter: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    quranAudioUseCaseAqc.getChapterAudioOfReciter(
                        chapterNumber, reciter
                    )
                }
                result.handle(object : HandleResult<ChapterAudiosFileAqc> {
                    override fun handleSuccess(data: ChapterAudiosFileAqc) {
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

        override fun setAyahs(ayahs: ArabicChaptersAqc) {
            player.setAyahs(ayahs)
        }

        override fun getVerseAudioFile(verseKey: String, reciter: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val result =
                    launchSafely { quranAudioUseCaseAqc.getVerseAudioFile(verseKey, reciter) }
                result.handle(object : HandleResult<VersesAudioFileAqc> {
                    override fun handleSuccess(data: VersesAudioFileAqc) {
                        viewModelScope.launch {
                            observable.update(observable.state().value.copy(verseAudioFile = data))
                        }
                    }

                    override fun handleError(e: Exception) {
                        super.handleError(e)
                    }
                })
            }
        }
    }
}