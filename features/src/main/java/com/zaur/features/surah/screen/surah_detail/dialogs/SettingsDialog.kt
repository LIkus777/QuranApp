package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaur.features.surah.screen.surah_detail.CustomBottomSheet
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Preview(showBackground = true)
@Composable
fun SettingsDialog(
    showSheet: Boolean = true,
    colors: QuranColors = LightThemeColors,
    selectedReciter: String? = "Alafasy",
    showReciterDialog: (Boolean) -> Unit = {},
    showArabic: Boolean = true,
    onShowArabicChange: (Boolean) -> Unit = {},
    showRussian: Boolean = true,
    onShowRussianChange: (Boolean) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    CustomBottomSheet(
        colors = colors,
        isVisible = showSheet,
        onDismiss = { onDismiss() },
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(colors.boxBackground)
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "Настройки",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Чтец: ${selectedReciter ?: "не выбран"}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.textPrimary,
                    modifier = Modifier
                        .clickable { showReciterDialog(true) }
                        .background(
                            colors.cardBackground, shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp))

                Spacer(modifier = Modifier.height(24.dp))

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { onShowArabicChange(!showArabic) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.buttonPrimary,
                            contentColor = colors.textOnButton
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (showArabic) "Скрыть арабский" else "Показать арабский")
                    }

                    Button(
                        onClick = { onShowRussianChange(!showRussian) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.buttonPrimary,
                            contentColor = colors.textOnButton
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (showRussian) "Скрыть русский" else "Показать русский")
                    }
                }
            }
        }
    }
}