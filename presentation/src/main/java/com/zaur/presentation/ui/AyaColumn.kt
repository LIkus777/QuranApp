package com.zaur.presentation.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.zaur.domain.al_quran_cloud.models.arabic.Ayah
import com.zaur.presentation.ui.ui_state.AyaListItem
import com.zaur.presentation.ui.ui_state.SurahPlayerState
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
    surahPlayerState: SurahPlayerState,
    ayats: List<Ayah.Base>,
    isDarkTheme: Boolean,
    colors: QuranColors,
    onAyahItemChanged: (Int) -> Unit,
    onPageItemChanged: (Int) -> Unit,
    onClickSound: (Int, Int) -> Unit,
    onListenSurahClicked: () -> Unit,
    translations: List<com.zaur.domain.al_quran_cloud.models.translate.Ayah.Base>,
) {

    val isCurrentSurah = surahDetailState.textState().surahName() == surahPlayerState.surahName()

    // 1) получаем начальный аят
    val currentTextAyah = surahPlayerState.currentAyah()

    // Вычисляем, сколько у нас хедеров перед аятами:
    val headerCount = if (chapterNumber != 9) 1 else 0

    // При создании состояния с учётом смещённого индекса:
    val listState = remember(ayats) {
        LazyListState(firstVisibleItemIndex = currentTextAyah + headerCount)
    }

    when (state) {
        is AyaListItem.Loading -> state.Render()
        is AyaListItem.AyahListItem -> state.Render(
            headerCount = headerCount,
            isCurrentSurah = isCurrentSurah,
            chapterNumber = chapterNumber,
            surahDetailState = surahDetailState,
            surahPlayerState = surahPlayerState,
            listState = listState,
            ayats = ayats,
            isDarkTheme = isDarkTheme,
            colors = colors,
            onAyahItemChanged = onAyahItemChanged,
            onPageItemChanged = onPageItemChanged,
            onClickSound = onClickSound,
            onListenSurahClicked = onListenSurahClicked,
            translations = translations
        )
    }
}