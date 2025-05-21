package com.zaur.presentation.ui.ui_state.aqc

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface SurahDetailScreenState {

    fun textState(): TextState.Base
    fun audioPlayerState(): AudioPlayerState.Base
    fun reciterState(): ReciterState.Base
    fun uiPreferencesState(): UiPreferencesState.Base
    fun bottomSheetState(): BottomSheetState.Base

    data class Base(
        private val textState: TextState.Base = TextState.Base(),
        private val audioPlayerState: AudioPlayerState.Base = AudioPlayerState.Base(),
        private val reciterState: ReciterState.Base = ReciterState.Base("", ""),
        private val uiPreferencesState: UiPreferencesState.Base = UiPreferencesState.Base(),
        private val bottomSheetState: BottomSheetState.Base = BottomSheetState.Base(),
    ) : SurahDetailScreenState {
        override fun textState(): TextState.Base = textState
        override fun audioPlayerState(): AudioPlayerState.Base = audioPlayerState
        override fun reciterState(): ReciterState.Base = reciterState
        override fun uiPreferencesState(): UiPreferencesState.Base = uiPreferencesState
        override fun bottomSheetState(): BottomSheetState.Base = bottomSheetState
    }

    object Empty : SurahDetailScreenState {
        override fun textState(): TextState.Base = TextState.Empty
        override fun audioPlayerState(): AudioPlayerState.Base = AudioPlayerState.Empty
        override fun reciterState(): ReciterState.Base = ReciterState.Empty
        override fun uiPreferencesState(): UiPreferencesState.Base = UiPreferencesState.Empty
        override fun bottomSheetState(): BottomSheetState.Base = BottomSheetState.Empty
    }
}

interface TextState {

    fun currentAyah(): Int

    data class Base(
        private val currentAyah: Int = 0
    ) : TextState {
        override fun currentAyah(): Int = currentAyah
    }

    companion object {
        val Empty = Base()
    }
}

interface AudioPlayerState {

    fun currentAyah(): Int
    fun currentSurahNumber(): Int
    fun isAudioPlaying(): Boolean
    fun playWholeChapter(): Boolean
    fun restartAudio(): Boolean
    fun isOfflineMode(): Boolean

    data class Base(
        private val currentAyah: Int = 0,
        private val currentSurahNumber: Int = 0,
        private val isAudioPlaying: Boolean = false,
        private val playWholeChapter: Boolean = true,
        private val restartAudio: Boolean = false,
        private val isOfflineMode: Boolean = false,
    ) : AudioPlayerState {
        override fun currentAyah() = currentAyah
        override fun currentSurahNumber() = currentSurahNumber
        override fun isAudioPlaying() = isAudioPlaying
        override fun playWholeChapter() = playWholeChapter
        override fun restartAudio() = restartAudio
        override fun isOfflineMode() = isOfflineMode
    }

    companion object {
        val Empty = Base()
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

    companion object {
        val Empty = Base()
    }
}

interface ReciterState {
    fun currentReciter(): String
    fun currentReciterName(): String
    fun showReciterDialog(): Boolean

    data class Base(
        private val currentReciter: String,
        private val currentReciterName: String,
        private val showReciterDialog: Boolean = false,
    ) : ReciterState {
        override fun currentReciter(): String = currentReciter
        override fun currentReciterName() = currentReciterName
        override fun showReciterDialog() = showReciterDialog
    }

    companion object {
        val Empty = Base(currentReciter = "", currentReciterName = "")
    }
}

// BottomSheetState

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

    companion object {
        val Empty = Base()
    }
}