package com.zaur.features.surah.screen.bookmarks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp


/**
 * @author Zaur
 * @since 30.05.2025
 */

@Composable
fun BookmarksScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("📑 Bookmarks", fontSize = 24.sp)
    }
}