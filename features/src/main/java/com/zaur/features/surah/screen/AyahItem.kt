package com.zaur.features.surah.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.BidiFormatter
import com.zaur.features.R
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors
import com.zaur.presentation.ui.fonts.NotoFontMedium
import com.zaur.presentation.ui.fonts.OpenSansFontLight

@Preview(showBackground = true)
@Composable
fun AyahItem(
    ayahNumber: Int = 72,
    arabicText: String = "وَعَدَ اللَّهُ الْمُؤْمِنِينَ وَالْمُؤْمِنَاتِ جَنَّاتٍ تَجْرِي مِن تَحْتِهَا الْأَنْهَارُ خَالِدِينَ فِيهَا وَمَسَاكِنَ طَيِّبَةً فِي جَنَّاتِ عَدْنٍ وَرِضْوَانٌ مِّنَ اللَّهِ أَكْبَرُ ذَلِكَ هُوَ الْفَوْزُ الْعَظِيمُ",
    translation: String = "Аллах обещал верующим мужчинам и женщинам Райские сады, в которых текут реки и в которых они пребудут вечно, а также прекрасные жилища в садах Эдема. Но довольство Аллаха будет превыше этого. Это и есть великое преуспеяние.",
    colors: QuranColors = LightThemeColors,
    fontSizeArabic: Float = 24f,
    fontSizeRussian: Float = 16f,
    onClickSound: (Int) -> Unit = {}
) {
    val arabicTextFormatted = BidiFormatter.getInstance().unicodeWrap(arabicText)

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp, top = 4.dp)
        ) {
            Row (
                modifier = Modifier
                    .height(24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = ayahNumber.toString(),
                    fontSize = 10.sp,
                    color = colors.textSecondary,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.border(4.dp, colors.border, shape = RoundedCornerShape(8.dp))
                        .background(color = colors.boxBackground, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.volume),
                    contentDescription = "Звук",
                    modifier = Modifier.size(24.dp).clickable(onClick = { onClickSound(ayahNumber) })
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp)) // Отступ от номера аята

        Column {
            Text(
                text = arabicTextFormatted,
                fontFamily = NotoFontMedium,
                fontSize = fontSizeArabic.sp,
                color = colors.textPrimary,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = (fontSizeArabic * 1.4).sp, // Увеличиваем расстояние между строками
                maxLines = Int.MAX_VALUE
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = translation,
                fontFamily = OpenSansFontLight,
                fontSize = fontSizeRussian.sp,
                color = colors.textSecondary,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = (fontSizeRussian * 2).dp), // Минимальная высота предотвращает наложение
                lineHeight = (fontSizeRussian * 1.4).sp
            )
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
