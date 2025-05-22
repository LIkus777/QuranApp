package com.zaur.features.surah.screen.surah_detail

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.presentation.ui.QuranColors
import com.zaur.presentation.ui.ui_state.AnimatedMenuUiState
import com.zaur.presentation.ui.ui_state.SurahDetailUiState

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun ScreenContent(
    chapterNumber: Int,
    deps: SurahDetailDependencies,
    uiData: SurahDetailUiData,
    colors: QuranColors,
    surahName: String,
    onMenuClick: () -> Unit,
) {
    with(uiData) {
        with(deps) {
            val isBarsVisible = remember { mutableStateOf(true) }
            val surahMode = screenContentViewModel().surahMode().collectAsState()
            val animatedMenu = screenContentViewModel().animatedMenu().collectAsState()
            val isLoading =
                textState().currentArabicText() == ArabicChapter.Empty || translateState().translations() == TranslationAqc.Empty

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        isBarsVisible.value = !isBarsVisible.value
                    }) {

                // Основной контент на фоне
                when (surahMode.value) {
                    is SurahDetailUiState.SurahModeState -> surahMode.value.RenderSurahMode(
                        state = screenContentViewModel().fetchAyaListItem(
                            isLoading, chapterNumber
                        ),
                        colors = colors,
                        isDarkTheme = isDarkTheme(),
                        chapterNumber = chapterNumber,
                        surahDetailState = surahDetailState(),
                        translations = translateState().translations().translationAyahs(),
                        ayats = textState().currentArabicText().ayahs(),
                        onAyahItemChanged = { index ->
                            quranTextViewModel().saveLastReadAyahPosition(chapterNumber, index)
                        },
                        onPageItemChanged = { page ->
                            quranPageViewModel().saveLastReadPagePosition(page)
                        },
                        onClickSound = { ayahNumber, ayahNumberInSurah ->
                            surahDetailViewModel().setAyahInAudio(ayahNumberInSurah)
                            Log.i("TAG", "SreenContent: ayahNumber $ayahNumber ayahNumberInSurah $ayahNumberInSurah")
                            quranAudioViewModel().onPlaySingleClicked(
                                ayahNumberInSurah, chapterNumber
                            )
                        })

                    is SurahDetailUiState.PageModeState -> {
                        deps.quranPageViewModel()
                            .getUthmaniPage(deps.quranPageViewModel().getLastReadPagePosition())
                        deps.quranPageViewModel().getTranslatedPage(
                            deps.quranPageViewModel().getLastReadPagePosition(),
                            "ru.kuliev"
                        )
                        surahMode.value.RenderPageMode(
                            colors = colors,
                            isDarkTheme = isDarkTheme(),
                            pageState = pageState(),
                            surahDetailState = surahDetailState(),
                            onClickPreviousPage = {},
                            onClickNextPage = {},
                            onClickSound = { ayahNumber, ayahNumberInSurah ->
                                surahDetailViewModel().setAyahInAudio(ayahNumberInSurah)
                                Log.i("TAG", "SreenContent: ayahNumber $ayahNumber ayahNumberInSurah $ayahNumberInSurah")
                                quranAudioViewModel().onPlaySingleClicked(
                                    ayahNumberInSurah, chapterNumber
                                )
                            })
                    }
                }

                // TopBar поверх контента
                when (animatedMenu.value) {
                    is AnimatedMenuUiState.Animate -> animatedMenu.value.Render(
                        isPlaying = surahDetailState().audioPlayerState().isAudioPlaying(),
                        surahName = surahName,
                        isBarsVisible = isBarsVisible.value,
                        colors = colors,
                        onMenuClick = onMenuClick,
                        onClickSettings = { surahDetailViewModel().showSettingsBottomSheet(true) },
                        onClickReciter = { surahDetailViewModel().showTextBottomSheet(true) },
                        onClickPlay = { surahDetailViewModel().showPlayerBottomSheet(true) },
                    )
                }
            }
        }
    }
}