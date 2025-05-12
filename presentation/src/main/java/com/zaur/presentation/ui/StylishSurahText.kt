package com.zaur.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaur.domain.al_quran_cloud.models.chapter.RevelationType
import com.zaur.presentation.R
import com.zaur.presentation.ui.fonts.NotoFontMedium

/**
* @author Zaur
* @since 2025-05-12
*/

@Preview(showBackground = true)
@Composable
fun ModernSurahText(
    colors: QuranColors = LightThemeColors,
    number: Long = 1L,
    englishName: String = "Al-Bakara",
    arabicName: String = "بِسۡمِ ٱللَّهِ",
    numberOfAyats: Int = 100,
    revelationType: RevelationType = RevelationType.Meccan,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(
            containerColor = colors.cardBackground
        ), elevation = CardDefaults.elevatedCardElevation(), // Тень для объёма
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.background)
            .padding(start = 6.dp, end = 6.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 14.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            // Прямоугольник с номером суры
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .border(4.dp, colors.border, shape = RoundedCornerShape(12.dp))
                    .background(
                        color = colors.appBarColor, shape = RoundedCornerShape(12.dp)
                    ), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$number", style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.ayahTextPrimary,
                    ), modifier = Modifier.padding(2.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = englishName, style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Thin,
                        fontFamily = FontFamily(
                            Font(R.font.noto_medium)
                        ),
                        color = colors.textPrimary, // Синий цвет ссылки
                    ), modifier = Modifier.padding(start = 6.dp).height(30.dp)
                )

                //Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "$numberOfAyats аятов • ${if (revelationType == RevelationType.Meccan) "Мекканская" else "Мединская"}",
                    style = TextStyle(
                        fontSize = 12.sp, fontWeight = FontWeight.Light, color = Color.Gray
                    ),
                    modifier = Modifier.padding(start = 6.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))
            }

            Text(
                text = arabicName, style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default, // Подбери арабский шрифт, если нужно
                    color = colors.textPrimary,
                    textAlign = TextAlign.End
                ), modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

