package com.zaur.presentation.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaur.presentation.R

/**
* @author Zaur
* @since 2025-05-12
*/

fun getNavBarHeightInPx(context: Context): Int {
    // Получение высоты навигационной панели через ресурсы
    val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    val navBarHeightInPx = if (resourceId > 0) {
        context.resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
    return navBarHeightInPx
}

@Preview(showBackground = true)
@Composable
fun ChapterBottomBar(
    modifier: Modifier = Modifier,
    colors: QuranColors = LightThemeColors,
    isPlaying: Boolean = false,
    showReciterDialog: (Boolean) -> Unit = {},
    showSettings: () -> Unit = {},
    onClickPlayer: () -> Unit = {}
) {

    val context = LocalContext.current
    val density = LocalDensity.current.density
    val navBarHeightInDp = getNavBarHeightInPx(context) / density

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(navBarHeightInDp.dp + 50.dp),
    ) {
        BottomAppBar(
            containerColor = colors.appBarColor,
            contentColor = colors.iconColor,
            modifier = Modifier.fillMaxSize() // заполнит родительский Box
        ) {
            IconButton(onClick = { showSettings() }) {
                Icon(Icons.Default.Settings, contentDescription = "Настройки")
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { showReciterDialog(true) }) {
                Icon(
                    painter = painterResource(R.drawable.letter_case),
                    contentDescription = "Размер шрифтов",
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = onClickPlayer) {
                Icon(
                    painter = painterResource(R.drawable.headphone_line), contentDescription = "Play/Pause"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { /* TODO: Избранное */ }) {
                Icon(Icons.Default.Favorite, contentDescription = "Избранное")
            }
        }
    }
}

@Composable
fun CustomBottomSheetDefaultPreview() {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center) // Выравниваем по центру
                .padding(horizontal = 16.dp) // Дополнительные отступы по бокам
        ) {
            Text("Настройки", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Чтец: ", fontSize = 16.sp, modifier = Modifier
                    //.clickable { showReciterDialog(true) }
                    .padding(top = 8.dp, bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Тема:")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = false, onCheckedChange = { /*onThemeChange(it)*/ })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Размер арабского шрифта")
            Slider(
                value = 1f, onValueChange = { }, valueRange = 14f..48f
            )

            Text("Размер русского шрифта")
            Slider(
                value = 1f, onValueChange = { }, valueRange = 14f..48f
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Button(onClick = { /*onShowArabicChange(!showArabic)*/ }) {
                    Text(if (false) "Скрыть арабский" else "Показать арабский")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { /*onShowRussianChange(!showRussian)*/ }) {
                    Text(if (false) "Скрыть русский" else "Показать русский")
                }
            }
        }
    }
}