package com.zaur.features.surah.screen.surah_detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.zaur.features.surah.screen.surah_detail.dialogs.ChooseReciterDialogComponent
import com.zaur.features.surah.screen.surah_detail.dialogs.ChooseTextDialogComponent
import com.zaur.features.surah.screen.surah_detail.dialogs.PlayerDialogComponent
import com.zaur.features.surah.screen.surah_detail.dialogs.SettingsDialogComponent
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun SurahDetailScreenContent(
    surahName: String,
    chapterNumber: Int,
    deps: SurahDetailDependencies,
    controller: NavHostController,
    onMenuClick: () -> Unit,
) {
    val uiData = rememberSurahDetailUiData(surahName, chapterNumber, deps)
    val colors = if (uiData.isDarkTheme()) DarkThemeColors else LightThemeColors

    // Эффекты
    SurahDetailEffects(
        chapterNumber = chapterNumber, deps = deps, uiData = uiData, controller = controller
    )

    // Контент
    ScreenContent(
        chapterNumber = chapterNumber,
        deps = deps,
        uiData = uiData,
        colors = colors,
        surahName = surahName,
        onMenuClick = onMenuClick
    )

    // Диалоги
    SettingsDialogComponent(uiData.surahDetailState(), colors, deps.surahDetailViewModel())
    PlayerDialogComponent(colors, uiData, deps)
    ChooseTextDialogComponent(colors, uiData, deps)
    ChooseReciterDialogComponent(
        uiData.surahDetailState(),
        colors,
        deps.surahDetailViewModel(),
        deps.quranAudioViewModel()
    )
}