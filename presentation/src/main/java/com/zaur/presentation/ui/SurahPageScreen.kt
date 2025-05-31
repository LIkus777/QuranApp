package com.zaur.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zaur.presentation.ui.ui_state.SurahPlayerState
import com.zaur.presentation.ui.ui_state.aqc.QuranPageUIState
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState

/**
 * @author Zaur
 * @since 15.05.2025
 */

@Composable
fun SurahPageScreen(
    pageState: QuranPageUIState,
    surahDetailState: SurahDetailScreenState,
    surahPlayerState: SurahPlayerState,
    isDarkTheme: Boolean,
    colors: QuranColors,
    onClickSound: (ayahNumber: Int, ayahInSurah: Int) -> Unit,
    onClickPreviousPage: () -> Unit,
    onClickNextPage: () -> Unit,
) {
    val listState = rememberLazyListState()
    val arabicText = remember { derivedStateOf { pageState.uthmaniPage() } }
    val translations = remember { derivedStateOf { pageState.translatedPage() } }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), state = listState
        ) {
            // Отображаем басмалу, если первая сура на странице не 9

            val shouldShowBasmala = arabicText.value.ayahs().firstOrNull()?.let { ayah ->
                // номер аята в суре == 1 (начало суры)
                // и сама сура не 9‑я
                ayah.numberInSurah() == 1L && ayah.surah().number() != 9L
            } != false
            if (shouldShowBasmala) {
                item { BasmalaItem(colors) }
            }

            itemsIndexed(
                items = arabicText.value.ayahs(),
                key = { index, aya -> aya.number() }) { index, aya ->
                val translationText =
                    if (index < translations.value.ayahs().size) translations.value.ayahs()
                        .getOrNull(index)?.text() else "Перевод отсутствует"
                val arabicAyah =
                    if (index == 0 && aya.surah().number() != 9L) aya.text().removeBasmala()
                    else aya.text()

                val ayats = arabicText.value.ayahs()
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
                    isCurrent = surahPlayerState.currentAyah() == aya.number().toInt(),
                    arabicAyah = arabicAyah,
                    translation = translationText.toString(),
                    colors = colors,
                    surahDetailState = surahDetailState,
                    onClickSound = { number, numberInSurah ->
                        onClickSound(number, numberInSurah)
                    })
            }
        }

        // Нижние кнопки: предыдущая и следующая страницы
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onClickPreviousPage, modifier = Modifier.weight(1f)
            ) {
                Text("Предыдущая")
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onClickNextPage, modifier = Modifier.weight(1f)
            ) {
                Text("Следующая")
            }
        }
    }
}