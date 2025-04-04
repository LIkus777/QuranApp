package com.zaur.features.surah.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.zaur.features.R
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranColors

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
    colors: QuranColors = LightThemeColors,
    showReciterDialog: (Boolean) -> Unit = {},
    showSettings: () -> Unit = {}
) {

    val density = LocalDensity.current.density

    val context = LocalContext.current

    val navBarHeightInDp = getNavBarHeightInPx(context) / density

    BottomAppBar(
        containerColor = colors.appBarColor,
        contentColor = colors.iconColor,
        modifier = Modifier.height(56.dp + navBarHeightInDp.dp)
    ) {
        IconButton(onClick = { showReciterDialog(true) }, modifier = Modifier.padding(start = 10.dp)) {
            Icon(
                painter = painterResource(R.drawable.letter_case),
                contentDescription = "Размер шрифтов",
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { showSettings() }) {
            Icon(Icons.Default.Settings, contentDescription = "Настройки")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {/* TODO: Избранное */ }, modifier = Modifier.padding(end = 10.dp)) {
            Icon(Icons.Default.Favorite, contentDescription = "Избранное")
        }
    }
}

@Composable
fun SettingsBottomSheet(
    showSheet: Boolean,
    colors: QuranColors,
    selectedReciter: String?,
    showReciterDialog: (Boolean) -> Unit,
    showArabic: Boolean,
    onShowArabicChange: (Boolean) -> Unit,
    showRussian: Boolean,
    onShowRussianChange: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {

    CustomBottomSheet(isVisible = showSheet, onDismiss = { onDismiss() }, alignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp)
            ) {
                Text("Настройки", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Чтец: $selectedReciter",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable { showReciterDialog(true) }
                        .padding(top = 8.dp, bottom = 8.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Column {
                    Button(onClick = { onShowArabicChange(!showArabic) }) {
                        Text(if (showArabic) "Скрыть арабский" else "Показать арабский")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onShowRussianChange(!showRussian) }) {
                        Text(if (showRussian) "Скрыть русский" else "Показать русский")
                    }
                }
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



