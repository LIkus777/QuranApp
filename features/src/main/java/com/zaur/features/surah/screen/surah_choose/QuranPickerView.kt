package com.zaur.features.surah.screen.surah_choose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.zaur.data.al_quran_aqc.utils.surahNamesRu
import com.zaur.features.surah.screen.surah_detail.CustomBottomSheet
import com.zaur.presentation.ui.QuranColors
import com.zaur.presentation.ui.getNavBarHeightInPx


/**
 * @author Zaur
 * @since 27.05.2025
 */

@Composable
fun QuranPickerDialog(
    isVisible: Boolean,
    colors: QuranColors,
    onDismiss: () -> Unit,
    onPageSelected: (Int) -> Unit = {},
    onSurahSelected: (Int) -> Unit = {},
    onJuzSelected: (Int) -> Unit = {},
) {
    val densityCurrent = LocalDensity.current.density
    val context = LocalContext.current
    val navBarHeightInDp = getNavBarHeightInPx(context) / densityCurrent

    var selectedTab by remember { mutableIntStateOf(1) }
    var selectedSurahIndex by remember { mutableIntStateOf(0) }
    var selectedAyahIndex by remember { mutableIntStateOf(0) }
    val surahNames = surahNamesRu.keys.toList()
    val rightItems = remember(selectedSurahIndex) {
        val ayahCount = surahNamesRu[surahNames[selectedSurahIndex]] ?: 7
        (1..ayahCount).toList()
    }
    val numberedSurahNames = surahNames.mapIndexed { index, name -> "${index + 1}. $name" }

    CustomBottomSheet(
        colors = colors, isVisible = isVisible, onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(bottom = navBarHeightInDp.dp)
                .background(colors.boxBackground)
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTab])
                            .height(3.dp),
                        color = colors.textPrimary
                    )
                }) {
                listOf("Страницы", "Суры", "Джузы").forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, color = colors.textPrimary) })
                }
            }

            Spacer(Modifier.height(12.dp))

            when (selectedTab) {
                0 -> SingleWheelPickerView(
                    itemName = "Страница",
                    items = (1..604).toList(),
                    onItemSelected = onPageSelected
                )

                1 -> DualWheelPickerView(
                    leftItems = numberedSurahNames,
                    rightItems = rightItems,
                    selectedLeftIndex = selectedSurahIndex,
                    selectedRightIndex = selectedAyahIndex,
                    onLeftSelected = { selectedIndex ->
                        selectedSurahIndex = selectedIndex
                        selectedAyahIndex = 0 // сброс выбора аята при смене суры
                        onSurahSelected(selectedIndex + 1)
                    },
                    onRightSelected = { ayahIndex ->
                        selectedAyahIndex = ayahIndex
                        // ваш код обработки выбора аята
                    })

                2 -> SingleWheelPickerView(
                    itemName = "Джуз", items = (1..30).toList(), onItemSelected = onJuzSelected
                )
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.buttonPrimary)
            ) {
                Text("Перейти", color = colors.textOnButton)
            }
        }
    }
}