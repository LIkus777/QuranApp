package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zaur.features.surah.screen.surah_detail.CustomBottomSheet
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Preview(showBackground = true)
@Composable
fun TextSettingsDialog(
    colors: QuranColors = LightThemeColors,
    showTextDialog: Boolean = true,
    isDarkTheme: Boolean = false,
    isSurahMode: Boolean = true,
    onThemeChange: (Boolean) -> Unit = {},
    fontSizeArabic: Float = 24f,
    onFontSizeArabicChange: (Float) -> Unit = {},
    fontSizeRussian: Float = 24f,
    onFontSizeRussianChange: (Float) -> Unit = {},
    onPageModeClicked: () -> Unit = {},
    onSurahModeClicked: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    if (showTextDialog) {
        CustomBottomSheet(
            showTextDialog,
            colors = colors,
            onDismiss = onDismiss,
        ) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(colors.boxBackground)
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val selectedBackground = colors.buttonTertiary
                        val unselectedBackground = Color.Transparent

                        val selectedBorder = BorderStroke(1.dp, colors.ayahBorder)
                        val unselectedBorder = BorderStroke(1.dp, colors.border)

                        val selectedTextColor = colors.textOnButton
                        val unselectedTextColor = colors.textPrimary

                        OutlinedButton(
                            onClick = onPageModeClicked,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (!isSurahMode) selectedBackground else unselectedBackground,
                                contentColor = if (!isSurahMode) selectedTextColor else unselectedTextColor
                            ),
                            border = if (!isSurahMode) selectedBorder else unselectedBorder
                        ) {
                            Text("Страница", maxLines = 1)
                        }

                        OutlinedButton(
                            onClick = onSurahModeClicked,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (isSurahMode) selectedBackground else unselectedBackground,
                                contentColor = if (isSurahMode) selectedTextColor else unselectedTextColor
                            ),
                            border = if (isSurahMode) selectedBorder else unselectedBorder
                        ) {
                            Text("Сура", maxLines = 1)
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Тема:",
                            color = colors.textPrimary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = onThemeChange,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colors.buttonPrimary,
                                checkedTrackColor = colors.buttonTertiary,
                                uncheckedThumbColor = colors.border,
                                uncheckedTrackColor = colors.divider
                            )
                        )
                    }

                    Column {
                        Text(
                            text = "Размер арабского шрифта",
                            color = colors.textPrimary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Slider(
                            value = fontSizeArabic,
                            onValueChange = onFontSizeArabicChange,
                            valueRange = 14f..48f,
                            colors = SliderDefaults.colors(
                                thumbColor = colors.buttonPrimary,
                                activeTrackColor = colors.buttonTertiary,
                                inactiveTrackColor = colors.divider
                            )
                        )
                    }

                    Column {
                        Text(
                            text = "Размер русского шрифта",
                            color = colors.textPrimary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Slider(
                            value = fontSizeRussian,
                            onValueChange = onFontSizeRussianChange,
                            valueRange = 14f..48f,
                            colors = SliderDefaults.colors(
                                thumbColor = colors.buttonPrimary,
                                activeTrackColor = colors.buttonTertiary,
                                inactiveTrackColor = colors.divider
                            )
                        )
                    }
                }
            }
        }
    }
}