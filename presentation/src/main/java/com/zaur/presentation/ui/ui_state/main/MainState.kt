package com.zaur.presentation.ui.ui_state.main

/**
* @author Zaur
* @since 2025-05-12
*/

data class MainState(
    val showLoadingQuran: Boolean = false,
    val isChaptersLoaded: Boolean = false,
    val isChaptersAudiosLoaded: Boolean = false,
    val isChaptersArabicsLoaded: Boolean = false,
    val isChaptersTranslationsLoaded: Boolean = false,
)