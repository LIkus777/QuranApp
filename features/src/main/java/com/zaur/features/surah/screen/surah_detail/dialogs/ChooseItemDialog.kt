package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Preview(showBackground = true)
@Composable
fun <T> ChooseItemDialog(
    showDialog: Boolean = true,
    title: String = "Выберите элемент",
    options: List<Pair<T, String>> = emptyList(),
    colors: QuranColors = LightThemeColors,
    onSelect: (T?) -> Unit = {},
) {
    if (!showDialog) return

    var selectedItem by remember { mutableStateOf<T?>(null) }

    Dialog(onDismissRequest = {
        onSelect(null)
    }) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(16.dp))
                .background(colors.boxBackground)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )

                Spacer(modifier = Modifier.height(10.dp))

                options.forEachIndexed { index, (identifier, name) ->
                    val isSelected = selectedItem == identifier
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                selectedItem = identifier
                                onSelect(identifier)
                            }
                            .background(if (isSelected) colors.buttonPrimary else colors.cardBackground)
                            .padding(vertical = 12.dp, horizontal = 16.dp)) {
                        Text(
                            text = name,
                            color = if (isSelected) colors.textOnButton else colors.textPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    if (index != options.lastIndex) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(color = colors.divider, thickness = 1.dp)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}