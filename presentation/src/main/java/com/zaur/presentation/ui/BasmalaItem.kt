package com.zaur.presentation.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaur.presentation.R
import com.zaur.presentation.ui.fonts.NotoFontMedium

@Preview(showBackground = true)
@Composable
fun BasmalaItem(colors: QuranColors = LightThemeColors) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        val options = BitmapFactory.Options().apply {
            inSampleSize = 2 // уменьшает изображение в 2 раза
        }
        val bitmap =
            BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.bs1, options)

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Басмаля",
            modifier = Modifier.width(390.dp).height(380.dp).padding(start = 45.dp)
        )

        Text(
            text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
            fontSize = 26.sp,
            fontFamily = NotoFontMedium,
            color = colors.textOnButton,
            textAlign = TextAlign.Center,
        )
    }
}