package com.zaur.presentation.ui.ui_state

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
import com.zaur.presentation.ui.removeBasmala
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState
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
        isCurrentSurah: Boolean,
        chapterNumber: Int,
        surahDetailState: SurahDetailScreenState,
        listState: LazyListState,
        ayats: List<Ayah.Base>,
        isDarkTheme: Boolean,
        colors: QuranColors,
        onAyahItemChanged: (Int) -> Unit,
        onPageItemChanged: (Int) -> Unit,
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
            isCurrentSurah: Boolean,
            chapterNumber: Int,
            surahDetailState: SurahDetailScreenState,
            listState: LazyListState,
            ayats: List<Ayah.Base>,
            isDarkTheme: Boolean,
            colors: QuranColors,
            onAyahItemChanged: (Int) -> Unit,
            onPageItemChanged: (Int) -> Unit,
            onClickSound: (Int, Int) -> Unit,
            translations: List<com.zaur.domain.al_quran_cloud.models.translate.Ayah.Base>
        ) {
            val currentAudioAyah = surahDetailState.audioPlayerState().currentAyah()

            // Эффект для скролла при изменении текущего аята аудио
            LaunchedEffect(currentAudioAyah) {
                if (currentAudioAyah > 0 && isCurrentSurah) {
                    listState.animateScrollToItem(currentAudioAyah)
                }
            }

            // Отслеживаем верхний видимый аят и передаём его page
            LaunchedEffect(listState) {
                snapshotFlow { listState.firstVisibleItemIndex }.distinctUntilChanged()
                    .collect { index ->
                        val ayahNumberInSurah = ayats.getOrNull(index)?.numberInSurah()?.toInt()
                        if (ayahNumberInSurah != null) onAyahItemChanged(ayahNumberInSurah)
                        val pageNumber = ayats.getOrNull(index)?.page()?.toInt()
                        if (pageNumber != null) onPageItemChanged(pageNumber)
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
                    items = ayats, key = { index, aya -> aya.number() }) { index, aya ->
                    val translationText =
                        if (index < translations.size) translations[index].text() else "Перевод отсутствует"
                    val arabicText =
                        if (index == 0 && chapterNumber != 9) aya.text().removeBasmala()
                        else aya.text()

                    //todo сделать для page режима
                    // определяем глобальный индекс текущего аята
                    val firstGlobalIndex = ayats.indexOfFirst { it.number() == ayats.first().number() }
                    val globalIndex      = firstGlobalIndex + index

                    val prevAya = ayats.getOrNull(globalIndex - 1)

                    val showJuzChange  = prevAya?.juz()         != aya.juz()
                    val showHizbChange = prevAya?.hizbQuarter() != aya.hizbQuarter()

                    AyahItem(
                        showJuzChange = showJuzChange,
                        newJuz = aya.juz(),
                        showHizbChange = showHizbChange,
                        newHizbQuarter = aya.hizbQuarter(),
                        isDarkTheme = isDarkTheme,
                        ayahNumber = aya.number().toInt(),
                        currentAyahInSurah = aya.numberInSurah().toInt(),
                        isCurrent = currentAudioAyah == aya.numberInSurah().toInt(),
                        arabicText = arabicText,
                        translation = translationText,
                        colors = colors,
                        surahDetailState = surahDetailState,
                        onClickSound = { number, numberInSurah ->
                            onClickSound(number, numberInSurah)
                        })
                }
            }
        }
    }
}