package com.zaur.features.surah.screen.surah_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaur.domain.al_quran_cloud.models.chapter.RevelationType
import com.zaur.presentation.R
import com.zaur.presentation.ui.LightThemeColors

@Preview(showBackground = true)
@Composable
fun SurahChooseItem(
    number: Long = 1L,
    englishName: String = "Al-Bakara",
    arabicName: String = "بِسۡمِ ٱللَّهِ",
    numberOfAyats: Int = 100,
    revelationType: RevelationType = RevelationType.Meccan,
    modifier: Modifier = Modifier
) {
    val colors = LightThemeColors
    Row(
        modifier = modifier
            .width(LocalConfiguration.current.screenWidthDp.dp / 1.5f)
            .height(IntrinsicSize.Min)
            .padding(start = 10.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Left
    ) {
        // Прямоугольник с номером суры
        Box(
            modifier = Modifier
                .size(45.dp)
                .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
                .background(
                    color = colors.appBarColor, shape = RoundedCornerShape(8.dp)
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$number", style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                ), modifier = Modifier.padding(2.dp)
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Column(modifier = Modifier
            .padding(start = 2.dp)
            .fillMaxHeight()) {
            //Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = englishName, style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Thin,
                    fontFamily = FontFamily(
                        Font(R.font.noto_medium)
                    ),
                    letterSpacing = 0.5.sp,
                    color = colors.textPrimary, // Синий цвет ссылки
                )
            )
            Text(
                text = "$numberOfAyats аятов • ${if (revelationType == RevelationType.Meccan) "Мекканская" else "Мединская"}",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray,
                    letterSpacing = 0.5.sp
                ),
            )
        }
    }
}
