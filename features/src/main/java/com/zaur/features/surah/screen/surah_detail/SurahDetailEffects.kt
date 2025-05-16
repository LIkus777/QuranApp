package com.zaur.features.surah.screen.surah_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun SurahDetailEffects(
    chapterNumber: Int,
    deps: SurahDetailDependencies,
    uiData: SurahDetailUiData,
) {
    remember(chapterNumber, deps, uiData) {
        SurahDetailEffectHandler.Base(deps, uiData, chapterNumber)
    }.Handle()
}