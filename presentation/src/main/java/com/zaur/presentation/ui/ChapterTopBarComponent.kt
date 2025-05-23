package com.zaur.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
* @author Zaur
* @since 2025-05-12
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterTopBarComponent(
    surahName: String,
    modifier: Modifier,
    colors: QuranColors,
    onMenuClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(surahName) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Меню")
            }
        },
        actions = {
            IconButton(onClick = { /* например, поиск */ }) {
                Icon(Icons.Default.Search, contentDescription = "Поиск")
            }
        },
    )
}
