package com.zaur.features.surah.ui_state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaur.domain.al_quran_cloud.models.arabic.Ayah
import com.zaur.presentation.ui.AyahItem
import com.zaur.presentation.ui.BasmalaItem
import com.zaur.presentation.ui.QuranColors
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * @author Zaur
 * @since 13.05.2025
 */

interface AyaListItem {
    @Composable
    fun Render(): Unit = Unit

    @Composable
    fun Render(
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
    ): Unit = Unit

    object Loading : AyaListItem {
        @Composable
        override fun Render() {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }

    // Обычный аят
    object AyahListItem : AyaListItem {
        @Composable
        override fun Render(
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
            // Отслеживаем верхний видимый аят и передаём его page
            LaunchedEffect(listState) {
                snapshotFlow { listState.firstVisibleItemIndex }
                    .distinctUntilChanged()
                    .collect { index ->
                        val pageNumber = ayats.getOrNull(index)?.page()?.toInt()
                        if (pageNumber != null) {
                            onFirstVisibleItemChanged(pageNumber)
                        }
                    }
            }

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
                    key = { index, aya -> aya.number() }
                ) { index, aya ->
                    val translationText =
                        if (index < translations.size) translations[index].text() else "Перевод отсутствует"
                    val arabicText = if (index == 0 && chapterNumber != 9)
                        aya.text().removePrefix("بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیمِ").trimStart(' ', '،', '\n')
                    else aya.text()

                    AyahItem(
                        isDarkTheme = isDarkTheme,
                        ayahNumber = aya.number().toInt(),
                        currentAyahInSurah = aya.numberInSurah().toInt(),
                        isCurrent = ayaNumber == aya.numberInSurah().toInt(),
                        arabicText = arabicText,
                        translation = translationText,
                        colors = colors,
                        fontSizeArabic = fontSizeArabic,
                        fontSizeRussian = fontSizeRussian,
                        soundIsActive = soundIsActive,
                        showArabic = showArabic,
                        showRussian = showRussian,
                        onClickSound = { number, numberInSurah ->
                            onClickSound(number, numberInSurah)
                        }
                    )
                }
            }
        }
    }
}