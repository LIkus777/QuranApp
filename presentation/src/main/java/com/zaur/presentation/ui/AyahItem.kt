package com.zaur.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.BidiFormatter
import com.zaur.presentation.R
import com.zaur.presentation.ui.fonts.NotoFontMedium
import com.zaur.presentation.ui.fonts.OpenSansFontLight
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Preview(showBackground = true)
@Composable
fun AyahItem(
    showJuzChange: Boolean = true,
    newJuz: Long = 0L,
    showHizbChange: Boolean = true,
    newHizbQuarter: Long = 0L,
    isDarkTheme: Boolean = true,
    ayahNumber: Int = 72,
    currentAyahInSurah: Int = 72,
    isCurrent: Boolean = false,
    arabicText: String = "وَعَدَ اللَّهُ الْمُؤْمِنِينَ وَالْمُؤْمِنَاتِ جَنَّاتٍ تَجْرِي مِن تَحْتِهَا الْأَنْهَارُ خَالِدِينَ فِيهَا وَمَسَاكِنَ طَيِّبَةً فِي جَنَّاتِ عَدْنٍ وَرِضْوَانٌ مِّنَ اللَّهِ أَكْبَرُ ذَلِكَ هُوَ الْفَوْزُ الْعَظِيمُ",
    translation: String = "Аллах обещал верующим мужчинам и женщинам Райские сады, в которых текут реки и в которых они пребудут вечно, а также прекрасные жилища в садах Эдема. Но довольство Аллаха будет превыше этого. Это и есть великое преуспеяние.",
    colors: QuranColors = LightThemeColors,
    surahDetailState: SurahDetailScreenState = SurahDetailScreenState.Empty,
    onClickSound: (Int, Int) -> Unit = { _, _ -> },
) {
    with(surahDetailState) {
        val arabic = BidiFormatter.getInstance().unicodeWrap(arabicText)
        val bgColor =
            if (isCurrent && audioPlayerState().isAudioPlaying()) colors.currentCard else Color.Transparent
        val iconTint = when {
            isCurrent && audioPlayerState().isAudioPlaying() -> colors.border
            isDarkTheme -> Color.White
            else -> Color.Unspecified
        }

        Column(
            Modifier
                .fillMaxWidth()
                .background(bgColor)
                .padding(16.dp)
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    currentAyahInSurah.toString(),
                    fontSize = 10.sp,
                    color = colors.ayahColor,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .border(4.dp, colors.ayahBorder, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp)
                )
                Spacer(Modifier.weight(1f))
                Box(
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { onClickSound(ayahNumber, currentAyahInSurah) }) {
                    Icon(
                        painter = painterResource(R.drawable.volume_up_line),
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            if (uiPreferencesState().showArabic()) Text(
                text = arabic,
                fontFamily = NotoFontMedium,
                fontSize = uiPreferencesState().fontSizeArabic().sp,
                color = colors.textPrimary,
                textAlign = TextAlign.Right,
                lineHeight = (uiPreferencesState().fontSizeArabic() * 1.4).sp,
                modifier = Modifier.fillMaxWidth()
            )

            if (uiPreferencesState().showRussian()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    translation,
                    fontFamily = OpenSansFontLight,
                    fontSize = uiPreferencesState().fontSizeRussian().sp,
                    color = colors.textSecondary,
                    textAlign = TextAlign.Left,
                    lineHeight = (uiPreferencesState().fontSizeRussian() * 1.4).sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = (uiPreferencesState().fontSizeRussian() * 2).dp)
                )
            }

            // --- НОВЫЙ БЛОК: индикаторы смены джуза / хизба ---
            if (showJuzChange || showHizbChange) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),                       // займет всю ширину
                    horizontalArrangement = Arrangement.End     // всё вложенное «припадает» к правому краю
                ) {
                    if (showJuzChange) {
                        Text(
                            text = "Джуз $newJuz",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = colors.buttonPrimary,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(end = 4.dp)
                        )
                    }
                    if (showHizbChange) {
                        Text(
                            text = "Хизб $newHizbQuarter",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = colors.buttonPrimary,
                            modifier = Modifier
                                .wrapContentWidth()
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colors.divider)
            )
        }
    }
}