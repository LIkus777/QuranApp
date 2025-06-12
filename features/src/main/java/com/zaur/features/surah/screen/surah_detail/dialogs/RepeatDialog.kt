package com.zaur.features.surah.screen.surah_detail.dialogs

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zaur.features.surah.screen.surah_detail.CustomBottomSheet
import com.zaur.presentation.ui.CustomProgressBarWithBuffer
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors
import kotlin.math.roundToInt

/**
 * @author Zaur
 * @since 12.06.2025
 */

@Preview(showBackground = true)
@Composable
fun RepeatDialog(
    showSheet: Boolean = true,
    colors: QuranColors = LightThemeColors,
    lastAyah: Int = 20,
    onDismiss: () -> Unit = {},
    onApply: (Int, Int, Int, Int) -> Unit = { _, _, _, _ -> },
) {
    var startFrac by remember { mutableStateOf(0f) }
    var endFrac by remember { mutableStateOf(1f) }
    var rangeRepeats by remember { mutableStateOf(1) }
    var ayahRepeats by remember { mutableStateOf(1) }

    CustomBottomSheet(colors = colors, isVisible = showSheet, onDismiss = onDismiss) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Выберите диапазон аятов",
                style = MaterialTheme.typography.titleLarge,
                color = colors.textPrimary
            )
            Spacer(Modifier.height(12.dp))
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                // левый номер — никакого weight
                Text(
                    text = "1", color = colors.textPrimary, modifier = Modifier.padding(end = 14.dp)
                )

                // слайдер растягивается
                CustomProgressBarWithBuffer(
                    colors = colors,
                    startProgress = startFrac,
                    endProgress = endFrac,
                    onRangeChange = { s, e ->
                        startFrac = s; endFrac = e/* no-op */
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp) // под цифры
                )

                // правый номер — без weight
                Text(
                    text = "$lastAyah",
                    color = colors.textPrimary,
                    modifier = Modifier.padding(start = 14.dp)
                )
            }
            Spacer(Modifier.height(24.dp))
            // Range repeats
            Text("Повтор диапазона ${rangeRepeats} раз", color = colors.textSecondary)
            Spacer(Modifier.height(8.dp))
            RepeatControl(
                value = rangeRepeats, colors = colors, onValueChange = { rangeRepeats = it })
            Spacer(Modifier.height(24.dp))
            // Ayah repeats
            Text("Повтор каждого аята ${ayahRepeats} раз", color = colors.textSecondary)
            Spacer(Modifier.height(8.dp))
            RepeatControl(
                value = ayahRepeats, colors = colors, onValueChange = { ayahRepeats = it })
            Spacer(Modifier.height(24.dp))
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    startFrac = 0f; endFrac = 1f; rangeRepeats = 1; ayahRepeats = 1
                }) {
                    Icon(
                        Icons.Default.Create,
                        contentDescription = null,
                        tint = colors.buttonPrimary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Сбросить", color = colors.buttonPrimary)
                }
                Button(onClick = {
                    val startAyah = (startFrac * 1).roundToInt()
                    val endAyah = (endFrac * lastAyah).roundToInt()
                    onApply(startAyah, endAyah, rangeRepeats, ayahRepeats)
                }) {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = colors.textOnButton
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Применить", color = colors.textOnButton)
                }
            }
        }
    }
}

@Composable
fun RepeatControl(
    value: Int,
    colors: QuranColors,
    onValueChange: (Int) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { if (value > 1) onValueChange(value - 1) }) {
            Icon(Icons.Default.Call, contentDescription = null, tint = colors.buttonPrimary)
        }
        Box(
            Modifier
                .background(colors.boxBackground, shape = RoundedCornerShape(4.dp))
                .border(1.dp, colors.divider, shape = RoundedCornerShape(4.dp))
                .size(64.dp, 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(value.toString(), color = colors.textPrimary)
        }
        IconButton(onClick = { onValueChange(value + 1) }) {
            Icon(Icons.Default.Add, contentDescription = null, tint = colors.buttonPrimary)
        }
        IconButton(onClick = { onValueChange(Int.MAX_VALUE) }) {
            Icon(Icons.Default.Lock, contentDescription = null, tint = colors.buttonPrimary)
        }
    }
}
