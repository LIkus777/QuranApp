package com.zaur.features.surah.screen.surah_detail

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zaur.domain.al_quran_cloud.models.page.QuranPage
import com.zaur.presentation.ui.AyahItem
import com.zaur.presentation.ui.BasmalaItem
import com.zaur.presentation.ui.QuranColors

/**
 * @author Zaur
 * @since 15.05.2025
 */

@Composable
fun SurahPageScreen(
    arabicText: QuranPage,
    currentAyahNumber: Int,
    showArabic: Boolean,
    showRussian: Boolean,
    fontSizeArabic: Float,
    fontSizeRussian: Float,
    isDarkTheme: Boolean,
    colors: QuranColors,
    soundIsActive: Boolean,
    translations: QuranPage,
    onClickSound: (ayahNumber: Int, ayahInSurah: Int) -> Unit,
    onClickPreviousPage: () -> Unit,
    onClickNextPage: () -> Unit,
) {
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), state = listState
        ) {
            // Отображаем басмалу, если первая сура на странице не 9
            val firstSurah = arabicText.ayahs().firstOrNull()?.surah()
            if (firstSurah != null && firstSurah.number() != 9L) {
                item {
                    BasmalaItem(colors)
                }
            }

            itemsIndexed(
                items = arabicText.ayahs(), key = { _, ayah -> ayah.number() }) { index, ayah ->
                val translationText = translations.ayahs().getOrNull(index)?.text() ?: "Перевод отсутствует"
                val arabicText = if (index == 0 && firstSurah?.number() != 9L) {
                    ayah.text().removePrefix("بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیمِ")
                        .trimStart(' ', '،', '\n')
                } else {
                    ayah.text()
                }

                AyahItem(
                    isDarkTheme = isDarkTheme,
                    ayahNumber = ayah.number().toInt(),
                    currentAyahInSurah = ayah.numberInSurah().toInt(),
                    isCurrent = currentAyahNumber == ayah.number().toInt(),
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
