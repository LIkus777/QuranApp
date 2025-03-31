package com.zaur.features.surah.screen

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.zaur.data.al_quran_aqc.constans.ReciterList
import com.zaur.presentation.ui.LightThemeColors

//@Preview(showBackground = true)
@Composable
fun ChooseReciterDialog(showDialog: Boolean, onDismiss: (String?) -> Unit) {
    if (showDialog) {
        val colors = LightThemeColors
        var selectedItem by remember { mutableStateOf<String?>(null) } // Храним выбор

        Dialog(onDismissRequest = { /* Блокируем закрытие, если не выбран элемент */ }) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Выберите чтеца", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(10.dp))

                    ReciterList.reciters.toList().forEachIndexed { index, (name, identifier) ->
                        val isSelected = selectedItem == identifier
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clickable { selectedItem = identifier }
                                .padding(9.dp)
                                .background(
                                    if (isSelected) colors.buttonSecondary else colors.buttonDisabled,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .fillMaxWidth()) {
                            Text(
                                name,
                                modifier = Modifier.padding(16.dp),
                                color = if (isSelected) colors.textOnButton else colors.textOnCard,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Показываем разделитель, если это не последний элемент
                        if (index != ReciterList.reciters.toList().lastIndex) {
                            Box(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(colors.divider)
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                            .background(Color.Black)
                    )

                    Button(
                        onClick = {
                            // При закрытии передаем выбранный identifier
                            onDismiss(selectedItem)
                        }, enabled = selectedItem != null, colors = ButtonDefaults.buttonColors(
                            containerColor = colors.buttonPrimary, // Зелёный фон кнопки
                            contentColor = Color.White // Белый цвет текста
                        )
                    ) {
                        Text("ГОТОВО")
                    }
                }
            }
        }
    }
}