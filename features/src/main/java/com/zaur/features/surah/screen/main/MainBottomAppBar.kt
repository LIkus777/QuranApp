package com.zaur.features.surah.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zaur.presentation.R
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors
import com.zaur.presentation.ui.getNavBarHeightInPx


/**
 * @author Zaur
 * @since 27.05.2025
 */

@Composable
fun MainBottomAppBar(
    colors: QuranColors = LightThemeColors,
    navController: NavHostController,
    showSettings: () -> Unit = {},
) {
    val iconModifier = Modifier.size(26.dp)

    BottomAppBar(
        containerColor = colors.appBarColor,
        contentColor = colors.iconColorForBottom,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .windowInsetsPadding(WindowInsets.navigationBars) // автоматически добавляет паддинг снизу под навигационную панель
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        IconButton(onClick = { /* TODO: Читать Коран */ }) {
            Icon(
                modifier = iconModifier,
                painter = painterResource(R.drawable.bookopen),
                contentDescription = "Читать Коран",
                tint = colors.iconColorForBottom
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { /* TODO: Избранное */ }) {
            Icon(
                modifier = iconModifier,
                painter = painterResource(R.drawable.bookmark),
                contentDescription = "Закладки",
                tint = colors.iconColorForBottom
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { showSettings() }) {
            Icon(
                modifier = iconModifier,
                painter = painterResource(R.drawable.settings),
                contentDescription = "Настройки",
                tint = colors.iconColorForBottom
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))
    }
}
