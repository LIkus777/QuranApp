package com.zaur.features.surah.screen.surah_detail

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import com.zaur.domain.al_quran_cloud.models.arabic.Ayah
import com.zaur.features.surah.ui_state.AyaListItem
import com.zaur.presentation.ui.QuranColors

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun AyaColumn(
    state: AyaListItem,
    ayaNumber: Int,
    chapterNumber: Int,
    soundIsActive: Boolean,
    showArabic: Boolean,
    showRussian: Boolean,
    fontSizeArabic: Float,
    fontSizeRussian: Float,
    listState: LazyListState,
    ayats: List<Ayah.Base>,
    isDarkTheme: Boolean,
    colors: QuranColors,
    onClickSound: (Int, Int) -> Unit,
    translations: List<com.zaur.domain.al_quran_cloud.models.translate.Ayah.Base>,
) {
    when (state) {
        is AyaListItem.Loading -> state.Render()
        is AyaListItem.AyahListItem -> state.Render(
            ayaNumber,
            chapterNumber,
            soundIsActive,
            showArabic,
            showRussian,
            fontSizeArabic,
            fontSizeRussian,
            listState,
            ayats,
            isDarkTheme,
            colors,
            onClickSound,
            translations
        )
    }
}