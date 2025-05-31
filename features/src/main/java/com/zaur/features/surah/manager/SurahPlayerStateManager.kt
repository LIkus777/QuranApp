package com.zaur.features.surah.manager

import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.observables.SurahPlayerStateObservable
import com.zaur.presentation.ui.ui_state.SurahPlayerState
import kotlinx.coroutines.flow.StateFlow


/**
 * @author Zaur
 * @since 31.05.2025
 */

interface SurahPlayerStateManager : SurahPlayerStateObservable.Read {

    fun updateState(state: SurahPlayerState.Base)

    fun setAudioSurahNumber(surahNumber: Int)
    fun setAudioSurahName(name: String)
    fun setAudioSurahAyah(ayah: Int)
    fun setOfflineMode(isOffline: Boolean)

    fun updateAyahAndSurah(ayah: Int, surah: Int)
    fun updatePlaybackPosition(position: Long, duration: Long)

    fun clear()

    class Base(
        private val initial: SurahPlayerState.Base = SurahPlayerState.Base(),
        private val observable: SurahPlayerStateObservable.Mutable = SurahPlayerStateObservable.Base(
            initial
        ),
    ) : Observable.Abstract<SurahPlayerState.Base>(initial), SurahPlayerStateManager {
        internal inline fun update(block: SurahPlayerState.Base.() -> SurahPlayerState.Base) {
            val current = observable.surahPlayerState().value
            observable.update(current.block())
        }

        override fun surahPlayerState(): StateFlow<SurahPlayerState.Base> =
            observable.surahPlayerState()

        override fun updateState(state: SurahPlayerState.Base) = with(state) {
            observable.update(
                state
            )
        }

        override fun setAudioSurahNumber(surahNumber: Int) = update {
            copy(currentSurahNumber = surahNumber)
        }

        override fun setAudioSurahName(name: String) = update {
            copy(surahName = name)
        }

        override fun setAudioSurahAyah(ayahInSurah: Int) = update {
            copy(currentAyah = ayahInSurah)
        }

        override fun setOfflineMode(isOffline: Boolean) = update {
            copy(isOfflineMode = isOffline)
        }

        override fun updatePlaybackPosition(position: Long, duration: Long) = update {
            copy(
                position = position, duration = duration
            )
        }

        override fun updateAyahAndSurah(ayah: Int, surah: Int) = update {
            copy(
                currentAyah = ayah, currentSurahNumber = surah
            )
        }

        override fun clear() {
            val base = SurahPlayerState.Base()
            update {
                base
            }
        }
    }

}