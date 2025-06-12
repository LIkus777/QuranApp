package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.runtime.Composable
import com.zaur.features.surah.manager.TranslatorManager
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.presentation.ui.QuranColors
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState


/**
 * @author Zaur
 * @since 11.06.2025
 */

@Composable
fun ChooseTranslationDialogComponent(
    state: SurahDetailScreenState,
    colors: QuranColors,
    viewModel: SurahDetailViewModel,
    translatorManager: TranslatorManager,
) {
    ChooseTranslationDialog(
        showDialog = state.bottomSheetState().showTranslatorDialog(), colors = colors
    ) { identifier ->
        viewModel.showTranslatorDialog(false)
        if (!identifier.isNullOrEmpty()) translatorManager.saveTranslator(identifier)
        viewModel.selectedTranslator(
            translatorManager.getTranslator().toString(),
            translatorManager.getTranslatorName().toString()
        )
    }
}