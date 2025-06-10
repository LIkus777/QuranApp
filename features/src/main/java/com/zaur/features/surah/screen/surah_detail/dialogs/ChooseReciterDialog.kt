package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.zaur.data.al_quran_aqc.constans.ReciterList
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Preview(showBackground = true)
@Composable
fun ChooseReciterDialog(
    showDialog: Boolean = true,
    colors: QuranColors = LightThemeColors,
    onSelect: (String) -> Unit = {},
) {
    ChooseItemDialog(
        showDialog = showDialog,
        title = "Выберите чтеца",
        options = ReciterList.reciters.toList(),
        colors = colors
    ) { selectedId ->
        if (selectedId != null) {
            onSelect(selectedId)
        }
    }
}