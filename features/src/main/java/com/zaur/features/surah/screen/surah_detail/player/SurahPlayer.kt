package com.zaur.features.surah.screen.surah_detail.player


import android.util.Log
import androidx.media3.common.MediaItem
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.base.AudioPlayerCallback
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.screen.surah_detail.player.AyahList.EmptyAyahList
import com.zaur.features.surah.screen.surah_detail.player.AyahList.RealAyahList
import com.zaur.features.surah.screen.surah_detail.player.CacheAyahList.EmptyCacheAyahList
import com.zaur.features.surah.screen.surah_detail.player.CacheAyahList.RealCacheAyahList
import com.zaur.features.surah.screen.surah_detail.player.PlayerCommand.PauseCommand
import com.zaur.features.surah.screen.surah_detail.player.PlayerCommand.PlayWholeChapterCommand
import com.zaur.features.surah.screen.surah_detail.player.PlayerCommand.ResumeCommand
import com.zaur.features.surah.viewmodel.QuranAudioViewModel

/**
 * @author Zaur
 * @since 2025-05-12
 */

// Интерфейс для управления плеером
interface SurahPlayer {

    fun onPlayVerse(verse: VerseAudioAqc)
    fun onPlayWholeClicked()
    fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int)
    fun onAudioEnded()
    fun onPauseClicked()
    fun onStopClicked()

    fun clear()

    fun setQuranAudioVmCallback(callback: QuranAudioViewModel.QuranAudioVmCallback)

    suspend fun setAyahs(ayahs: List<Ayah.Base>)
    suspend fun setCacheAudios(ayahs: List<CacheAudio.Base>)

    // Реализация плеера с разделением на управление состоянием и воспроизведением
    class Base(
        private val playlistBuilder: PlaylistBuilder,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater,
        private val audioPlaybackHelper: AudioPlaybackHelper,
        private val audioPlayer: AudioPlayer,  // Интерфейс для работы с плеером
        private val surahDetailStateManager: SurahDetailStateManager, // Менеджер состояния плеера
    ) : SurahPlayer {

        private var localMediaItems: List<MediaItem> = emptyList()
        private var cacheMediaItems: List<MediaItem> = emptyList()
        private var quranAudioVmCallback: QuranAudioViewModel.QuranAudioVmCallback? = null
        private var ayahs: AyahList = EmptyAyahList
        private var cacheAyahs: CacheAyahList = EmptyCacheAyahList
        private var currentAyahIndex = 0

        private val state = surahDetailStateManager.surahDetailState()

        init {
            audioPlayer.setAudioPlayerCallback(object : AudioPlayerCallback {
                override fun audioEnded() {
                    onAudioEnded()
                }

                override fun onAyahChanged(mediaId: String?) {
                    val ayahNumberInSurah = mediaId?.toIntOrNull() ?: return
                    Log.d("TAG", "onAyahChanged: $ayahNumberInSurah")
                    Log.d("TAG", "onAyahChanged: ayahs - $ayahs")
                    val ayah = ayahs.findByNumberInSurah(ayahNumberInSurah)
                    if (ayah != null) {
                        audioPlayerStateUpdater.updateCurrentAyah(ayah.numberInSurah().toInt())
                    } else {
                        Log.w("SurahPlayer", "onAyahChanged: Ayah not found for number $ayahNumberInSurah")
                    }
                }
            })
        }

        override suspend fun setAyahs(ayahs: List<Ayah.Base>) {
            this.ayahs = RealAyahList(ayahs)
            localMediaItems = playlistBuilder.buildLocalPlaylistAsync(
                this.ayahs, state.value.audioPlayerState.currentSurahNumber()
            )
        }

        override suspend fun setCacheAudios(ayahs: List<CacheAudio.Base>) {
            this.cacheAyahs = RealCacheAyahList(ayahs)
            cacheMediaItems = playlistBuilder.buildCachePlaylistAsync(this.cacheAyahs)
        }

        override fun onPlayVerse(verse: VerseAudioAqc) {
            if (state.value.audioPlayerState.restartAudio()) {
                audioPlayer.restartAudio()
            } else {
                audioPlaybackHelper.play(verse)
            }
            audioPlayerStateUpdater.setRestartAudio(false, true)
        }

        override fun onPlayWholeClicked() {
            val isOffline = state.value.audioPlayerState.isOfflineMode()
            val items = if (isOffline) localMediaItems else cacheMediaItems

            if (items.isNotEmpty()) {
                val command = when {
                    !audioPlayer.isPlaying() && !audioPlayer.isPaused() -> PlayWholeChapterCommand(
                        audioPlayer, audioPlayerStateUpdater, playlistBuilder, items
                    )

                    audioPlayer.isPlaying() -> PauseCommand(audioPlayer, audioPlayerStateUpdater)
                    else -> ResumeCommand(audioPlayer, audioPlayerStateUpdater)
                }

                command.execute()
            }
        }

        override fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int) {
            audioPlayerStateUpdater.setCurrentAyahAndSurah(ayahNumber, surahNumber)
            Log.i("TAGGG", "C: aya - $ayahNumber, surah - $surahNumber")
            quranAudioVmCallback?.callVerseAudioFile(ayahNumber)
        }

        fun playCurrentAyah() {
            if (ayahs.isEmpty() == false) {
                val currentAyah = ayahs.get(currentAyahIndex)
                audioPlayerStateUpdater.updateCurrentAyahInSurah(
                    currentAyah.numberInSurah().toInt()
                )
                Log.i("TAGGG", "playCurrentAyah: CALLED currentAyah - $currentAyah")
                quranAudioVmCallback?.callVerseAudioFile(currentAyah.numberInSurah().toInt())
            } else {
                audioPlayerStateUpdater.markWholeChapterPlaying(false, true)
            }
        }

        override fun onAudioEnded() {
            Log.i("TAG", "onAudioEnded: STARTED")
            if (!state.value.audioPlayerState.playWholeChapter()) {
                audioPlayerStateUpdater.stop()
                return
            }

            // Если воспроизведение главы — переходим к следующему аяту
            currentAyahIndex++
            if (currentAyahIndex >= ayahs.getList().size) {
                audioPlayerStateUpdater.markWholeChapterPlaying(false, true)
                return
            }
            playCurrentAyah()
        }

        override fun onPauseClicked() {
            audioPlayer.pauseAudio()
            audioPlayerStateUpdater.stop()
        }

        override fun onStopClicked() {
            audioPlayer.stopAudio()
            audioPlayerStateUpdater.stop()
        }

        override fun clear() {
            audioPlayer.clear()
            currentAyahIndex = 0
            ayahs = EmptyAyahList
            cacheAyahs = EmptyCacheAyahList
            localMediaItems = emptyList()
            cacheMediaItems = emptyList()
            quranAudioVmCallback = null
        }

        override fun setQuranAudioVmCallback(callback: QuranAudioViewModel.QuranAudioVmCallback) {
            this.quranAudioVmCallback = callback
        }
    }
}