package com.zaur.features.surah.ui_state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaur.domain.al_quran_cloud.models.arabic.Ayah
import com.zaur.domain.al_quran_cloud.models.page.QuranPage
import com.zaur.features.surah.screen.surah_detail.AyaColumn
import com.zaur.features.surah.screen.surah_detail.SurahPageScreen
import com.zaur.presentation.ui.QuranColors


/**
 * @author Zaur
 * @since 13.05.2025
 */

interface SurahDetailUiState {

    @Composable
    fun Render() = Unit

    @Composable
    fun Render(
        arabicText: QuranPage,
        currentAyahNumber: Int,
        showArabic: Boolean,
        showRussian: Boolean,
        fontSizeArabic: Float,
        fontSizeRussian: Float,
        isDarkTheme: Boolean,
        colors: QuranColors,
        soundIsActive: Boolean,
        translatedText: QuranPage,
        onClickPreviousPage: () -> Unit,
        onClickNextPage: () -> Unit,
        onClickSound: (Int, Int) -> Unit,
    ) = Unit

    @Composable
    fun Render(
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
        onFirstVisibleItemChanged: (Int) -> Unit,
        onClickSound: (Int, Int) -> Unit,
        translations: List<com.zaur.domain.al_quran_cloud.models.translate.Ayah.Base>,
    ) = Unit

    object SurahModeState : SurahDetailUiState {
        @Composable
        override fun Render(
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
            onFirstVisibleItemChanged: (Int) -> Unit,
            onClickSound: (Int, Int) -> Unit,
            translations: List<com.zaur.domain.al_quran_cloud.models.translate.Ayah.Base>,
        ) {
            AyaColumn(
                state = state,
                ayaNumber = ayaNumber,
                chapterNumber = chapterNumber,
                soundIsActive = soundIsActive,
                showArabic = showArabic,
                showRussian = showRussian,
                fontSizeArabic = fontSizeArabic,
                fontSizeRussian = fontSizeRussian,
                listState = listState,
                ayats = ayats,
                isDarkTheme = isDarkTheme,
                colors = colors,
                onFirstVisibleItemChanged,
                onClickSound = onClickSound,
                translations = translations
            )
        }
    }

    object PageModeState : SurahDetailUiState {
        @Composable
        override fun Render(
            arabicText: QuranPage,
            currentAyahNumber: Int,
            showArabic: Boolean,
            showRussian: Boolean,
            fontSizeArabic: Float,
            fontSizeRussian: Float,
            isDarkTheme: Boolean,
            colors: QuranColors,
            soundIsActive: Boolean,
            translatedText: QuranPage,
            onClickPreviousPage: () -> Unit,
            onClickNextPage: () -> Unit,
            onClickSound: (Int, Int) -> Unit,
        ) {
            SurahPageScreen(
                arabicText = arabicText,
                currentAyahNumber = currentAyahNumber,
                showArabic = showArabic,
                showRussian = showRussian,
                fontSizeArabic = fontSizeArabic,
                fontSizeRussian = fontSizeRussian,
                isDarkTheme = isDarkTheme,
                colors = colors,
                soundIsActive = soundIsActive,
                translations = translatedText,
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