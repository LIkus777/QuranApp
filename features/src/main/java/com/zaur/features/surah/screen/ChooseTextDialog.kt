package com.zaur.features.surah.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ChooseTextDialog(
    showTextDialog: Boolean,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    fontSizeArabic: Float,
    onFontSizeArabicChange: (Float) -> Unit,
    fontSizeRussian: Float,
    onFontSizeRussianChange: (Float) -> Unit,
    onDismiss: () -> Unit
) {
    if (showTextDialog) {
        CustomBottomSheet(
            showTextDialog, onDismiss = { onDismiss() }, alignment = Alignment.BottomStart
        ) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Тема:")
                        Spacer(modifier = Modifier.width(8.dp))
                        Switch(checked = isDarkTheme, onCheckedChange = { onThemeChange(it) })
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Размер арабского шрифта")
                    Slider(
                        value = fontSizeArabic,
                        onValueChange = onFontSizeArabicChange,
                        valueRange = 14f..48f
                    )

                    Text("Размер русского шрифта")
                    Slider(
                        value = fontSizeRussian,
                        onValueChange = onFontSizeRussianChange,
                        valueRange = 14f..48f
                    )
                }
            }
        }
    }
}