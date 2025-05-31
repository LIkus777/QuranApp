package com.zaur.presentation.ui.ui_state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaur.domain.al_quran_cloud.models.arabic.Ayah
import com.zaur.presentation.ui.AyaColumn
import com.zaur.presentation.ui.ui_state.aqc.QuranPageUIState
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState
import com.zaur.presentation.ui.QuranColors
import com.zaur.presentation.ui.SurahPageScreen


/**
 * @author Zaur
 * @since 13.05.2025
 */

interface SurahDetailUiState {

    @Composable
    fun Render() = Unit

    @Composable
    fun RenderSurahMode(
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
    ) = Unit

    @Composable
    fun RenderPageMode(
        pageState: QuranPageUIState,
        surahDetailState: SurahDetailScreenState,
        surahPlayerState: SurahPlayerState,
        isDarkTheme: Boolean,
        colors: QuranColors,
        onClickPreviousPage: () -> Unit,
        onClickNextPage: () -> Unit,
        onClickSound: (Int, Int) -> Unit,
    ) = Unit

    object SurahModeState : SurahDetailUiState {
        @Composable
        override fun RenderSurahMode(
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
            translations: List<com.zaur.domain.al_quran_cloud.models.translate.Ayah.Base>
        ) {
            AyaColumn(
                state = state,
                chapterNumber = chapterNumber,
                surahDetailState = surahDetailState,
                surahPlayerState = surahPlayerState,
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

    object PageModeState : SurahDetailUiState {
        @Composable
        override fun RenderPageMode(
            pageState: QuranPageUIState,
            surahDetailState: SurahDetailScreenState,
            surahPlayerState: SurahPlayerState,
            isDarkTheme: Boolean,
            colors: QuranColors,
            onClickPreviousPage: () -> Unit,
            onClickNextPage: () -> Unit,
            onClickSound: (Int, Int) -> Unit
        ) {
            SurahPageScreen(
                pageState = pageState,
                surahDetailState = surahDetailState,
                surahPlayerState = surahPlayerState,
                isDarkTheme = isDarkTheme,
                colors = colors,
                onClickSound = onClickSound,
                onClickPreviousPage = onClickPreviousPage,
                onClickNextPage = onClickNextPage,
            )
        }
    }

    object Loading : SurahDetailUiState {
        @Composable
        override fun Render() {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }

}