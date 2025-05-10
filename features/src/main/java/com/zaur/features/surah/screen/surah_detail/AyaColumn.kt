package com.zaur.features.surah.screen.surah_detail

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import com.zaur.features.surah.ui_state.aqc.QuranTranslationAqcUIState
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import com.zaur.presentation.ui.AyahItem
import com.zaur.presentation.ui.BasmalaItem
import com.zaur.presentation.ui.QuranColors

@Composable
fun AyaColumn(
    isDarkTheme: Boolean,
    chapterNumber: Int,
    isLoading: Boolean,
    textState: QuranTextAqcUIState,
    translateState: QuranTranslationAqcUIState,
    colors: QuranColors,
    surahDetailState: SurahDetailScreenState,
    listState: LazyListState,
    onClickSound: (Int, Int) -> Unit,
) {
    val ayats = remember(textState.currentArabicText().ayahs()) {
        textState.currentArabicText().ayahs()
    }
    val translations = remember(translateState.translations().translationAyahs()) {
        translateState.translations().translationAyahs()
    }

    Log.i("TAG", "AyaColumn: translations $translations")

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            state = listState, modifier = Modifier.fillMaxSize()
        ) {
            if (chapterNumber != 9) {
                item {
                    BasmalaItem(colors)
                }
            }

            itemsIndexed(
                items = ayats,
                key = { index, aya -> aya.number() } // или любой другой уникальный параметр
            ) { index, aya ->
                val translationText =
                    if (index < translations.size) translations[index].text() else "Перевод отсутствует"
                val arabicText = if (index == 0 && chapterNumber != 9) aya.text()
                    .removePrefix("بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیمِ")
                    .trimStart(' ', '،', '\n')
                else aya.text()

                with(surahDetailState) {
                    AyahItem(
                        isDarkTheme = isDarkTheme,
                        ayahNumber = aya.number().toInt(),
                        currentAyahInSurah = aya.numberInSurah().toInt(),
                        isCurrent = audioPlayerState().currentAyahInSurah() == aya.numberInSurah()
                            .toInt(),
                        arabicText = arabicText,
                        translation = translationText,
                        colors = colors,
                        fontSizeArabic = uiPreferencesState().fontSizeArabic(),
                        fontSizeRussian = uiPreferencesState().fontSizeRussian(),
                        soundIsActive = audioPlayerState().isAudioPlaying(),
                        showArabic = uiPreferencesState().showArabic(),
                        showRussian = uiPreferencesState().showRussian(),
                        onClickSound = { number, numberInSurah ->
                            onClickSound(
                                number,
                                numberInSurah
                            )
                        })
                }
            }
        }
    }
}