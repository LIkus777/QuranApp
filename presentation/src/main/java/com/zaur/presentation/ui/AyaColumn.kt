package com.zaur.presentation.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.zaur.domain.al_quran_cloud.models.arabic.Ayah
import com.zaur.presentation.ui.ui_state.AyaListItem
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun AyaColumn(
    state: AyaListItem,
    chapterNumber: Int,
    surahDetailState: SurahDetailScreenState,
    ayats: List<Ayah.Base>,
    isDarkTheme: Boolean,
    colors: QuranColors,
    onAyahItemChanged: (Int) -> Unit,
    onPageItemChanged: (Int) -> Unit,
    onClickSound: (Int, Int) -> Unit,
    translations: List<com.zaur.domain.al_quran_cloud.models.translate.Ayah.Base>,
) {

    // 1) получаем начальный аят
    val currentTextAyah = surahDetailState.textState().currentAyah()

    // 2) state для Ленивая колонка
    val listState = remember(ayats) {
        LazyListState(currentTextAyah)
    }

    when (state) {
        is AyaListItem.Loading -> state.Render()
        is AyaListItem.AyahListItem -> state.Render(
            chapterNumber,
            surahDetailState,
            listState,
            ayats,
            isDarkTheme,
            colors,
            onAyahItemChanged,
            onPageItemChanged,
            onClickSound,
            translations
        )
    }
}