package com.zaur.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zaur.presentation.R
import com.zaur.presentation.ui.ui_state.ReadItem


/**
 * @author Zaur
 * @since 30.05.2025
 */

@Composable
fun SearchOverlay(
    colors: QuranColors,
    onDismiss: () -> Unit,
) {
    val readHistory = listOf(
        ReadItem("Аль-Хашр (59)", "Стр. 545"),
        ReadItem("Аль-Кахф (18)", "Стр. 293"),
        ReadItem("Page 191", "Ат-Тавба")
    )

    Surface(
        color = colors.background,
        modifier = Modifier
            .fillMaxSize()
            //.padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            var query by remember { mutableStateOf("") }
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Поиск по Корану", color = colors.textSecondary) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = null,
                        tint = colors.textSecondary
                    )
                },
                trailingIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Закрыть",
                            tint = colors.textSecondary
                        )
                    }
                },
                colors = TextFieldDefaults.colors()
            )

            Spacer(Modifier.height(16.dp))

            // Чипы быстрого доступа
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "Яа Син (36)",
                    "Ар-Рахман (55)",
                    "Аль-Мульк (67)",
                    "Аль-Фатх (48)",
                    "Аль-Кахф (18)"
                ).forEach { label ->
                        AssistChip(
                            onClick = { /* TODO */ },
                            label = { Text(label, color = colors.textOnButton) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = colors.buttonSecondary
                            )
                        )
                    }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Недавнее",
                color = colors.textSecondary,
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(Modifier.height(8.dp))

            LazyColumn {
                itemsIndexed(readHistory) { index, item ->
                    ListItem(
                        headlineContent = { Text(item.title, color = colors.textPrimary) },
                        supportingContent = { Text(item.subtitle, color = colors.textSecondary) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* TODO */ })
                    Divider(color = colors.divider)
                }
            }
        }
    }
}