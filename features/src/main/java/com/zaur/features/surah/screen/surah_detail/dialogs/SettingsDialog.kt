package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    selectedTranslator: String? = "Переводчик",
    showTranslatorDialog: (Boolean) -> Unit = {},
    selectedTranscription: String? = "Транскрипция",
    showTranscriptionDialog: (Boolean) -> Unit = {},
    showArabic: Boolean = true,
    onShowArabicChange: (Boolean) -> Unit = {},
    showTranscript: Boolean = true,
    onShowTranscriptChange: (Boolean) -> Unit = {},
    showTranslation: Boolean = true,
    onShowTranslationChange: (Boolean) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    CustomBottomSheet(
        colors = colors,
        isVisible = showSheet,
        onDismiss = onDismiss
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(colors.boxBackground)
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Настройки",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )

                Spacer(Modifier.height(16.dp))

                // 1) Выбор чтеца
                Text(
                    text = "Чтец: ${selectedReciter ?: "не выбран"}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.textPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showReciterDialog(true) }
                        .background(colors.cardBackground, RoundedCornerShape(12.dp))
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                )

                Spacer(Modifier.height(12.dp))

                // 2) Выбор переводчика
                Text(
                    text = "Перевод: ${selectedTranslator ?: "не выбран"}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.textPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTranslatorDialog(true) }
                        .background(colors.cardBackground, RoundedCornerShape(12.dp))
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                )

                Spacer(Modifier.height(12.dp))

                // 3) Выбор транскрипции
                Text(
                    text = "Транскрипция: ${selectedTranscription ?: "не выбрана"}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.textPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTranscriptionDialog(true) }
                        .background(colors.cardBackground, RoundedCornerShape(12.dp))
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                )

                Spacer(Modifier.height(24.dp))

                // Нижний ряд: кнопки-переключатели для отображения
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { onShowArabicChange(!showArabic) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showArabic) colors.buttonPrimary else colors.buttonDisabled,
                            contentColor = colors.textOnButton
                        )
                    ) {
                        Text("Arabic")
                    }

                    Button(
                        onClick = { onShowTranscriptChange(!showTranscript) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showTranscript) colors.buttonPrimary else colors.buttonDisabled,
                            contentColor = colors.textOnButton
                        )
                    ) {
                        Text("Transcript")
                    }

                    Button(
                        onClick = { onShowTranslationChange(!showTranslation) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showTranslation) colors.buttonPrimary else colors.buttonDisabled,
                            contentColor = colors.textOnButton
                        )
                    ) {
                        Text("Перевод")
                    }
                }
            }
        }
    }
}