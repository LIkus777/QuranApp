package com.zaur.features.surah.ui_state.main

data class MainState(
    val showLoadingQuran: Boolean = false,
    val isChaptersLoaded: Boolean = false,
    val isChaptersAudiosLoaded: Boolean = false,
    val isChaptersArabicsLoaded: Boolean = false,
    val isChaptersTranslationsLoaded: Boolean = false,
)