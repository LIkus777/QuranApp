package com.zaur.features.surah.ui_state.aqc

data class SurahDetailState(
    val isScrollToAyah: Boolean = false,
    val runAudio: Boolean = false,
    val isAudioPlaying: Boolean = false,
    val playWholeChapter: Boolean = true,
    val currentAyah: Int = 0,
    val restartAudio: Boolean = false,
    val showReciterDialog: Boolean = false,
    val showTextBottomSheet: Boolean = false,
    val showSettingsBottomSheet: Boolean = false,
    val selectedReciter: String? = null,
    val isFirstSelection: Boolean = true,
    val showArabic: Boolean = true,
    val showRussian: Boolean = true,
    val fontSizeArabic: Float = 24f,
    val fontSizeRussian: Float = 18f
)