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
    modifier: Modifier = Modifier,
    colors: QuranColors = LightThemeColors,
    showQuran: () -> Unit = {},
    showBookmarks: () -> Unit = {},
    showSettings: () -> Unit = {},
) {
    val iconModifier = Modifier.size(26.dp)

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

        IconButton(onClick = { showQuran() }) {
            Icon(
                modifier = iconModifier,
                painter = painterResource(R.drawable.bookopen),
                contentDescription = "Читать Коран",
                tint = colors.iconColorForBottom
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { showBookmarks() }) {
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
