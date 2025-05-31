package com.zaur.features.surah.screen.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zaur.navigation.Screen
import com.zaur.presentation.R
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors
import com.zaur.presentation.ui.getNavBarHeightInPx


/**
 * @author Zaur
 * @since 27.05.2025
 */

@Preview(showBackground = true)
@Composable
fun MainBottomAppBar(
    currentRoute: String? = "",
    modifier: Modifier = Modifier,
    colors: QuranColors = LightThemeColors,
    showQuran: () -> Unit = {},
    showBookmarks: () -> Unit = {},
    showSettings: () -> Unit = {},
) {
    val iconModifier = Modifier.size(26.dp)

    // Здесь можно выбрать «серый» цвет активной вкладки.
    // Например, возьмём тот же colors.iconColorForBottom, но с уменьшенной альфой.
    val selectedTint = colors.iconColorForBottom.copy(alpha = 0.6f)
    val defaultTint = colors.iconColorForBottom

    val densityCurrent = LocalDensity.current.density
    val context = LocalContext.current
    val navBarHeightInDp = getNavBarHeightInPx(context) / densityCurrent

    BottomAppBar(
        containerColor = colors.appBarColor,
        contentColor = colors.iconColorForBottom,
        modifier = modifier
            .fillMaxWidth()
            .height(navBarHeightInDp.dp)
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        // «Читать Коран» (SurahChoose)
        val isQuranSelected = currentRoute == Screen.SurahChoose.route
        IconButton(onClick = { showQuran() }) {
            Icon(
                modifier = iconModifier,
                painter = painterResource(R.drawable.bookopen),
                contentDescription = "Читать Коран",
                tint = if (isQuranSelected) selectedTint else defaultTint
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // «Закладки»
        val isBookmarksSelected = currentRoute == Screen.Bookmarks.route
        IconButton(onClick = { showBookmarks() }) {
            Icon(
                modifier = iconModifier,
                painter = painterResource(R.drawable.bookmark),
                contentDescription = "Закладки",
                tint = if (isBookmarksSelected) selectedTint else defaultTint
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // «Настройки»
        val isSettingsSelected = currentRoute == Screen.Settings.route
        IconButton(onClick = { showSettings() }) {
            Icon(
                modifier = iconModifier,
                painter = painterResource(R.drawable.settings),
                contentDescription = "Настройки",
                tint = if (isSettingsSelected) selectedTint else defaultTint
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))
    }
}
