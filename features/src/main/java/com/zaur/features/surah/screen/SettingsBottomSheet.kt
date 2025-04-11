package com.zaur.features.surah.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaur.presentation.ui.QuranColors

@Composable
fun SettingsBottomSheet(
    showSheet: Boolean,
    colors: QuranColors,
    selectedReciter: String?,
    showReciterDialog: (Boolean) -> Unit,
    showArabic: Boolean,
    onShowArabicChange: (Boolean) -> Unit,
    showRussian: Boolean,
    onShowRussianChange: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {

    CustomBottomSheet(
        isVisible = showSheet,
        onDismiss = { onDismiss() },
        alignment = Alignment.BottomCenter
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
                Text("Настройки", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Чтец: $selectedReciter",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable { showReciterDialog(true) }
                        .padding(top = 8.dp, bottom = 8.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Column {
                    Button(onClick = { onShowArabicChange(!showArabic) }) {
                        Text(if (showArabic) "Скрыть арабский" else "Показать арабский")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onShowRussianChange(!showRussian) }) {
                        Text(if (showRussian) "Скрыть русский" else "Показать русский")
                    }
                }
            }
        }
    }
}