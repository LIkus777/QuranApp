package com.zaur.features.surah.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import com.zaur.features.surah.ui_state.aqc.QuranTranslationAqcUIState
import com.zaur.presentation.ui.QuranColors


@Composable
fun AyaColumn(
    chapterNumber: Int,
    currentAyah: Int,
    isLoading: Boolean,
    textState: QuranTextAqcUIState,
    translateState: QuranTranslationAqcUIState,
    colors: QuranColors,
    fontSizeArabic: Float,
    fontSizeRussian: Float,
    soundIsActive: Boolean,
    isScrollToAyah: Boolean,
    listState: LazyListState,
    paddingValues: PaddingValues,
    onClickSound: (Int) -> Unit = {}
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
            LazyColumn(state = listState) {  // Передаем listState в LazyColumn
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
                    val arabicText =
                        if (index == 0 && chapterNumber != 9) aya.text.removePrefix("بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیمِ")
                            .trimStart(' ', '،', '\n')
                        else aya.text

                    AyahItem(
                        ayahNumber = aya.numberInSurah.toInt(),
                        arabicText = arabicText,
                        translation = translationText,
                        colors = colors,
                        fontSizeArabic = fontSizeArabic,
                        fontSizeRussian = fontSizeRussian,
                        soundIsActive = soundIsActive,
                        currentAyah = currentAyah,
                        onClickSound = { onClickSound(aya.numberInSurah.toInt()) })
                }
            }
        }
    }
}