package com.zaur.features.surah.screen.surah_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun SurahDetailEffects(
    chapterNumber: Int,
    deps: SurahDetailDependencies,
    uiData: SurahDetailUiData,
    controller: NavHostController,
) {
    remember(chapterNumber, deps, uiData, controller) {
        SurahDetailEffectHandler.Base(chapterNumber, uiData, deps, controller)
    }.Handle()
}