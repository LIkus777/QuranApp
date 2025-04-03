package com.zaur.features.surah.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import com.zaur.features.surah.ui_state.aqc.QuranTranslationAqcUIState
import com.zaur.presentation.ui.QuranColors


@Composable
fun AyaColumn(
    chapterNumber: Int,
    isLoading: Boolean,
    textState: QuranTextAqcUIState,
    translateState: QuranTranslationAqcUIState,
    colors: QuranColors,
    fontSizeArabic: Float,
    fontSizeRussian: Float,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                val ayats = textState.currentArabicText?.arabicChapters?.ayahs ?: emptyList()
                val translations = translateState.translations?.translations?.ayahs ?: emptyList()

                if (chapterNumber != 9) {
                    item {
                        BasmalaItem(colors)
                    }
                }

                itemsIndexed(ayats) { index, aya ->
                    val translationText =
                        if (index < translations.size) translations[index].text else "Перевод отсутствует"
                    AyahItem(aya.numberInSurah.toInt(), aya.text, translationText, colors, fontSizeArabic, fontSizeRussian)
                }
            }
        }
    }
}