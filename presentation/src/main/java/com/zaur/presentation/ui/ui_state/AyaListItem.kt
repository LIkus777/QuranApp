package com.zaur.presentation.ui.ui_state

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaur.domain.al_quran_cloud.models.arabic.Ayah
import com.zaur.presentation.R
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
        headerCount: Int,
        isCurrentSurah: Boolean,
        chapterNumber: Int,
        surahDetailState: SurahDetailScreenState,
        surahPlayerState: SurahPlayerState,
        listState: LazyListState,
        ayats: List<Ayah.Base>,
        isDarkTheme: Boolean,
        colors: QuranColors,
        onAyahItemChanged: (Int) -> Unit,
        onPageItemChanged: (Int) -> Unit,
        onClickSound: (Int, Int) -> Unit,
        onListenSurahClicked: () -> Unit,
        translations: List<com.zaur.domain.al_quran_cloud.models.translate.Ayah.Base>,
    ): Unit = Unit

    object Loading : AyaListItem {
        @Composable
        override fun Render() {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }

    object AyahListItem : AyaListItem {
        @Composable
        override fun Render(
            headerCount: Int,
            isCurrentSurah: Boolean,
            chapterNumber: Int,
            surahDetailState: SurahDetailScreenState,
            surahPlayerState: SurahPlayerState,
            listState: LazyListState,
            ayats: List<Ayah.Base>,
            isDarkTheme: Boolean,
            colors: QuranColors,
            onAyahItemChanged: (Int) -> Unit,
            onPageItemChanged: (Int) -> Unit,
            onClickSound: (Int, Int) -> Unit,
            onListenSurahClicked: () -> Unit,
            translations: List<com.zaur.domain.al_quran_cloud.models.translate.Ayah.Base>
        ) {
            val currentAudioAyah = surahPlayerState.currentAyah()

            // 1) Скролл при изменении текущего аята
            LaunchedEffect(currentAudioAyah) {
                if (currentAudioAyah > 0 && isCurrentSurah) {
                    Log.i("TAG", "Render: IF CALLED $currentAudioAyah")
                    // Тоже отдаем реальный индекс с учётом двух “хедеров”:
                    listState.animateScrollToItem(currentAudioAyah + headerCount)
                }
            }

            // 2) Отслеживаем, какой аят вверху, и передаём его наружу
            LaunchedEffect(listState) {
                snapshotFlow { listState.firstVisibleItemIndex }.distinctUntilChanged()
                    .collect { index ->
                        ayats.getOrNull(index)?.let { aya ->
                            onAyahItemChanged(aya.numberInSurah().toInt())
                            onPageItemChanged(aya.page().toInt())
                        }
                    }
            }

            LazyColumn(
                state = listState, modifier = Modifier.fillMaxSize()
            ) {
                // — Если это не 9-я сура, сначала рендерим BasmalaItem
                if (chapterNumber != 9) {
                    item {
                        BasmalaItem(colors)
                    }

                    // — Сразу после BasmalaItem вставляем кнопку «Слушать»
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .background(
                                        colors.buttonPrimary, shape = RoundedCornerShape(4.dp)
                                    )
                                    .clickable { onListenSurahClicked() }
                                    .padding(vertical = 8.dp, horizontal = 12.dp)
                                    .align(Alignment.Center),
                                verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.headphone_line),
                                    contentDescription = "Иконка слушать",
                                    tint = colors.iconColorForBottom,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Слушать",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = colors.textOnButton
                                )
                            }
                        }
                    }
                }

                // — Дальше уже все аяты
                itemsIndexed(
                    items = ayats, key = { index, aya -> aya.number() }) { index, aya ->
                    val translationText = if (index < translations.size) translations[index].text()
                    else "Перевод отсутствует"

                    val arabicText =
                        if (index == 0 && chapterNumber != 9) aya.text().removeBasmala()
                        else aya.text()

                    // prevAya = предыдущий аят в списке ayats (или null, если index == 0)
                    val prevAya = ayats.getOrNull(index - 1)

                    val showJuzChange = when {
                        index == 0 && chapterNumber == 1 -> true
                        index == 0 -> false
                        else -> prevAya?.juz() != aya.juz()
                    }

                    val showHizbChange = when {
                        index == 0 && chapterNumber == 1 -> true
                        index == 0 -> false
                        else -> prevAya?.hizbQuarter() != aya.hizbQuarter()
                    }

                    AyahItem(
                        showJuzChange = showJuzChange,
                        newJuz = aya.juz(),
                        showHizbChange = showHizbChange,
                        newHizbQuarter = aya.hizbQuarter(),
                        isDarkTheme = isDarkTheme,
                        ayahNumber = aya.number().toInt(),
                        currentAyahInSurah = aya.numberInSurah().toInt(),
                        isCurrent = currentAudioAyah == aya.numberInSurah().toInt() && isCurrentSurah,
                        arabicAyah = arabicText,
                        translation = translationText,
                        colors = colors,
                        surahDetailState = surahDetailState,
                        surahPlayerState = surahPlayerState,
                        onClickSound = { number, numberInSurah ->
                            onClickSound(number, numberInSurah)
                        })
                }
            }
        }
    }
}