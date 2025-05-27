package com.zaur.presentation.ui

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
        val arabicTextFormatted = BidiFormatter.getInstance().unicodeWrap(arabicText)
        val backgroundColor =
            if (isCurrent && audioPlayerState().isAudioPlaying()) colors.currentCard else Color.Unspecified
        val soundIconColor = when {
            isCurrent && audioPlayerState().isAudioPlaying() -> colors.border
            isDarkTheme -> Color.White
            else -> Color.Unspecified
        }

        // Создаём interactionSource
        val interactionSource = remember { MutableInteractionSource() }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .height(24.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = currentAyahInSurah.toString(),
                        fontSize = 10.sp,
                        color = colors.ayahColor,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            .border(
                                4.dp, colors.ayahBorder, shape = RoundedCornerShape(8.dp)
                            )
                            .background(color = Color.Unspecified, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .size(40.dp) // Увеличиваем область для ripple эффекта
                        .clip(CircleShape) // Делаем иконку круглой
                        .clickable(
                            interactionSource = interactionSource, // Указываем interactionSource
                            indication = LocalIndication.current, // Используем текущий ripple эффект из темы
                            onClick = {
                                onClickSound(ayahNumber, currentAyahInSurah)
                            })) {
                        Icon(
                            painter = painterResource(R.drawable.volume_up_line),
                            contentDescription = "Звук",
                            tint = soundIconColor,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp)) // Отступ от номера аята

            Column {
                if (uiPreferencesState().showArabic()) {
                    Text(
                        text = arabicTextFormatted,
                        fontFamily = NotoFontMedium,
                        fontSize = uiPreferencesState().fontSizeArabic().sp,
                        color = colors.textPrimary,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth(),
                        lineHeight = (uiPreferencesState().fontSizeArabic() * 1.4).sp, // Увеличиваем расстояние между строками
                        maxLines = Int.MAX_VALUE
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (uiPreferencesState().showRussian()) {
                    Text(
                        text = translation,
                        fontFamily = OpenSansFontLight,
                        fontSize = uiPreferencesState().fontSizeRussian().sp,
                        color = colors.textSecondary,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = (uiPreferencesState().fontSizeRussian() * 2).dp), // Минимальная высота предотвращает наложение
                        lineHeight = (uiPreferencesState().fontSizeRussian() * 1.4).sp
                    )
                }
            }
        }

        // Разделительная линия
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colors.divider)
        )
    }
}