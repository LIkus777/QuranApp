package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.zaur.data.al_quran_aqc.constans.TranslatorList
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors


/**
 * @author Zaur
 * @since 04.06.2025
 */

@Preview(showBackground = true)
@Composable
fun ChooseTranslationDialog(
    showDialog: Boolean = true,
    colors: QuranColors = LightThemeColors,
    onSelect: (String) -> Unit = {},
) {
    ChooseItemDialog(
        showDialog = showDialog,
        title = "Выберите переводчика",
        options = TranslatorList.translators.toList(),
        colors = colors
    ) { selectedId ->
        onSelect(selectedId)
    }
}