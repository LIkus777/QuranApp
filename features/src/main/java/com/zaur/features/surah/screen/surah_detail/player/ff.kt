/*
package com.zaur.features.surah.screen.surah_detail.player

interface SurahPlayer2 {

    fun onPlayVerse(verse: Ayah)
    fun onPlayWholeClicked()
    fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int)
    fun onAudioEnded()
    fun onPauseClicked()
    fun onStopClicked()

    fun clear()

    fun setQuranAudioVmCallback(callback: QuranAudioViewModel.QuranAudioVmCallback)

    fun setAyahs(ayahs: List<com.zaur.domain.al_quran_cloud.models.arabic.Ayah>)

    // Реализация плеера с разделением на управление состоянием и воспроизведением
    class Base(
        private val audioDownloader: AudioDownloader,
        private val audioPlayer: AudioPlayer,  // Интерфейс для работы с плеером
        private val surahDetailStateManager: SurahDetailStateManager, // Менеджер состояния плеера
    ) : SurahPlayer {

        private var quranAudioVmCallback: QuranAudioViewModel.QuranAudioVmCallback? = null
        private var ayahs: List<com.zaur.domain.al_quran_cloud.models.arabic.Ayah>? = null
        private var currentAyahIndex = 0

        private val playFromLocal = true

        private val state = surahDetailStateManager.getState()

        init {
            audioPlayer.setAudioPlayerCallback(object : AudioPlayerCallback {
                override fun audioEnded() {
                    onAudioEnded()
                }

                override fun onAyahChanged(mediaId: String?) {
                    val ayahNumberInSurah = mediaId?.toIntOrNull() ?: return
                    val ayah =
                        ayahs?.find { it.numberInSurah.toInt() == ayahNumberInSurah } ?: return

                    Log.d("TAG", "onAyahChanged: ayahNumberInSurah=$ayahNumberInSurah")

                    val updated = state.value.copy(
                        audioPlayerState = state.value.audioPlayerState.copy(
                            currentAyahInSurah = ayah.numberInSurah.toInt(),
                            currentAyah = ayah.number.toInt()
                        )
                    )
                    surahDetailStateManager.updateState(updated)
                }
            })
        }

        override fun setAyahs(ayahs: List<com.zaur.domain.al_quran_cloud.models.arabic.Ayah>) {
            this.ayahs = ayahs
        }

        fun playLocalOrRemoteAudio(verse: Ayah) {
            val localFile = audioDownloader.getAudioFile(
                verse.chapterNumber, verse.numberInSurah, "ar.alafasy"
            )
            if (playFromLocal && localFile?.exists() == true) {
                audioPlayer.playAudio(localFile.absolutePath, playFromCache = true)
            } else {
                audioPlayer.playAudio(verse.audio, playFromCache = false)
            }
        }

        override fun onPlayVerse(verse: Ayah) {
            if (state.value.audioPlayerState.restartAudio) {
                audioPlayer.restartAudio()
            } else {
                playLocalOrRemoteAudio(verse)
            }
            val updated = state.value.copy(
                audioPlayerState = state.value.audioPlayerState.copy(
                    isAudioPlaying = true, restartAudio = false
                )
            )
            surahDetailStateManager.updateState(updated)
        }

        override fun onPlayWholeClicked() {
            if (!audioPlayer.isPlaying() && !audioPlayer.isPaused()) {
                val items = buildPlaylist()
                audioPlayer.playPlaylist(items)
                val newState = state.value.copy(
                    audioPlayerState = state.value.audioPlayerState.copy(
                        isAudioPlaying = true, playWholeChapter = true
                    )
                )
                surahDetailStateManager.updateState(newState)
            } else {
                if (audioPlayer.isPlaying()) {
                    audioPlayer.pauseAudio()
                    surahDetailStateManager.updateState(
                        state.value.copy(
                            audioPlayerState = state.value.audioPlayerState.copy(
                                isAudioPlaying = false
                            )
                        )
                    )
                } else {
                    audioPlayer.resume()
                    surahDetailStateManager.updateState(
                        state.value.copy(
                            audioPlayerState = state.value.audioPlayerState.copy(
                                isAudioPlaying = true
                            )
                        )
                    )
                }
            }
        }

        override fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int) {
            val updated = state.value.copy(
                audioPlayerState = state.value.audioPlayerState.copy(
                    currentAyah = ayahNumber,
                    currentSurahNumber = surahNumber,
                    isAudioPlaying = true,
                    playWholeChapter = false,
                )
            )
            surahDetailStateManager.updateState(updated)
            quranAudioVmCallback?.callVerseAudioFile(ayahNumber)
        }

        fun buildPlaylist(): List<MediaItem> {
            return ayahs?.map { ayah ->
                val localFile = audioDownloader.getAudioFile(
                    state.value.audioPlayerState.currentSurahNumber.toLong(),
                    ayah.numberInSurah.toLong(),
                    "ar.alafasy"
                )
                val uri = Uri.fromFile(localFile)
                MediaItem.Builder()
                    .setUri(uri)
                    .setMediaId(ayah.numberInSurah.toString())
                    .build()
            }.orEmpty()
        }

        fun playCurrentAyah() {
            val currentAyah = ayahs?.getOrNull(currentAyahIndex)
            if (currentAyah != null) {
                val updated = state.value.copy(
                    audioPlayerState = state.value.audioPlayerState.copy(
                        currentAyahInSurah = currentAyah.numberInSurah.toInt()
                    )
                )
                surahDetailStateManager.updateState(updated)
                quranAudioVmCallback?.callVerseAudioFile(currentAyah.number.toInt())
            } else {
                val finished = state.value.copy(
                    audioPlayerState = state.value.audioPlayerState.copy(
                        playWholeChapter = true, isAudioPlaying = false
                    )
                )
                surahDetailStateManager.updateState(finished)
            }
        }

        override fun onAudioEnded() {
            if (!state.value.audioPlayerState.playWholeChapter) {
                val stopped = state.value.copy(
                    audioPlayerState = state.value.audioPlayerState.copy(
                        isAudioPlaying = false
                    )
                )
                surahDetailStateManager.updateState(stopped)
                return
            }

            // Если воспроизведение главы — переходим к следующему аяту
            currentAyahIndex++
            playCurrentAyah()
        }

        override fun onPauseClicked() {
            audioPlayer.pauseAudio()
            val updated = state.value.copy(
                audioPlayerState = state.value.audioPlayerState.copy(
                    isAudioPlaying = false
                )
            )
            surahDetailStateManager.updateState(updated)
        }

        override fun onStopClicked() {
            audioPlayer.stopAudio()
            val updated = state.value.copy(
                audioPlayerState = state.value.audioPlayerState.copy(
                    isAudioPlaying = false
                )
            )
            surahDetailStateManager.updateState(updated)
        }

        override fun clear() {
            audioPlayer.clear()
            ayahs = null
            quranAudioVmCallback = null
        }

        override fun setQuranAudioVmCallback(callback: QuranAudioViewModel.QuranAudioVmCallback) {
            this.quranAudioVmCallback = callback
        }
    }
}
*/
