package com.zaur.features.surah.ui_state.aqc

data class SurahDetailScreenState(
    val audioPlayerState: AudioPlayerState = AudioPlayerState(),
    val reciterState: ReciterState = ReciterState(),
    val uiPreferences: UiPreferencesState = UiPreferencesState(),
    val bottomSheetState: BottomSheetState = BottomSheetState()
)

data class UiPreferencesState(
    val showArabic: Boolean = true,
    val showRussian: Boolean = true,
    val fontSizeArabic: Float = 24f,
    val fontSizeRussian: Float = 18f
)

data class ReciterState(
    val currentReciter: String? = null,
    val currentReciterSimple: String? = null,
    val showReciterDialog: Boolean = false,
    val isFirstSelection: Boolean = true
)

data class AudioPlayerState(
    val firstAyahNumber: Int = 0,
    val currentAyahInSurah: Int = 0,
    val currentSurahNumber: Int = 0,
    val isAudioPlaying: Boolean = false,
    val playWholeChapter: Boolean = true,
    val restartAudio: Boolean = false,
    val isScrollToAyah: Boolean = false,
    val isOfflineMode: Boolean = false
)

data class BottomSheetState(
    val showTextBottomSheet: Boolean = false, val showSettingsBottomSheet: Boolean = false
)


