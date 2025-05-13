package com.zaur.features.surah.ui_state.aqc

/**
* @author Zaur
* @since 2025-05-12
*/

interface SurahDetailScreenState {

    fun audioPlayerState(): AudioPlayerState.Base
    fun reciterState(): ReciterState.Base
    fun uiPreferencesState(): UiPreferencesState.Base
    fun bottomSheetState(): BottomSheetState.Base

    data class Base(
        val audioPlayerState: AudioPlayerState.Base = AudioPlayerState.Base(),
        val reciterState: ReciterState.Base = ReciterState.Base(""),
        val uiPreferencesState: UiPreferencesState.Base = UiPreferencesState.Base(),
        val bottomSheetState: BottomSheetState.Base = BottomSheetState.Base(),
    ) : SurahDetailScreenState {
        override fun audioPlayerState(): AudioPlayerState.Base = audioPlayerState
        override fun reciterState(): ReciterState.Base = reciterState
        override fun uiPreferencesState(): UiPreferencesState.Base = uiPreferencesState
        override fun bottomSheetState(): BottomSheetState.Base = bottomSheetState
    }
}

interface AudioPlayerState {

    fun currentAyahInSurah(): Int
    fun currentSurahNumber(): Int
    fun isAudioPlaying(): Boolean
    fun playWholeChapter(): Boolean
    fun restartAudio(): Boolean
    fun isOfflineMode(): Boolean

    data class Base(
        private val currentAyahInSurah: Int = 0,
        private val currentSurahNumber: Int = 0,
        private val isAudioPlaying: Boolean = false,
        private val playWholeChapter: Boolean = true,
        private val restartAudio: Boolean = false,
        private val isOfflineMode: Boolean = false,
    ) : AudioPlayerState {
        override fun currentAyahInSurah() = currentAyahInSurah
        override fun currentSurahNumber() = currentSurahNumber
        override fun isAudioPlaying() = isAudioPlaying
        override fun playWholeChapter() = playWholeChapter
        override fun restartAudio() = restartAudio
        override fun isOfflineMode() = isOfflineMode
    }

}

interface UiPreferencesState {
    fun showArabic(): Boolean
    fun showRussian(): Boolean
    fun fontSizeArabic(): Float
    fun fontSizeRussian(): Float
    fun showSurahMode(): Boolean
    fun showPageMode(): Boolean

    data class Base(
        private val showArabic: Boolean = true,
        private val showRussian: Boolean = true,
        private val fontSizeArabic: Float = 24f,
        private val fontSizeRussian: Float = 18f,
        private val showSurahMode: Boolean = true,
        private val showPageMode: Boolean = false,
    ) : UiPreferencesState {
        override fun showArabic() = showArabic
        override fun showRussian() = showRussian
        override fun fontSizeArabic() = fontSizeArabic
        override fun fontSizeRussian() = fontSizeRussian
        override fun showSurahMode(): Boolean = showSurahMode
        override fun showPageMode(): Boolean = showPageMode
    }
}

interface ReciterState {
    fun currentReciter(): String
    fun showReciterDialog(): Boolean

    data class Base(
        private val currentReciter: String,
        private val showReciterDialog: Boolean = false,
    ) : ReciterState {
        override fun currentReciter() = currentReciter
        override fun showReciterDialog() = showReciterDialog
    }
}

interface BottomSheetState {
    fun showTextBottomSheet(): Boolean
    fun showSettingsBottomSheet(): Boolean

    data class Base(
        private val showTextBottomSheet: Boolean = false,
        private val showSettingsBottomSheet: Boolean = false,
    ) : BottomSheetState {
        override fun showTextBottomSheet() = showTextBottomSheet
        override fun showSettingsBottomSheet() = showSettingsBottomSheet
    }
}
