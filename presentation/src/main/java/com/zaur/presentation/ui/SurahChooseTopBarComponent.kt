package com.zaur.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaur.presentation.R
import com.zaur.presentation.ui.fonts.OpenSansFontLight


/**
 * @author Zaur
 * @since 27.05.2025
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahChooseTopBarComponent(
    modifier: Modifier = Modifier,
    colors: QuranColors,
    onClickPlayer: () -> Unit,
    onClickSearch: () -> Unit,
    onClickQuranLabel: () -> Unit,
) {
    val iconModifier = Modifier.size(26.dp)

    Column(modifier = modifier) {
        TopAppBar(
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding(),  // паддинг сверху под статус-бар
            title = {
                Row(
                    modifier = Modifier.clickable(onClick = onClickQuranLabel),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Коран",
                        fontFamily = OpenSansFontLight,
                        fontSize = 24.sp,
                        color = colors.textPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(R.drawable.arrow_down),
                        contentDescription = "Коран",
                        tint = colors.textSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }, navigationIcon = {}, actions = {
                IconButton(onClick = onClickSearch) {
                    Icon(
                        modifier = iconModifier,
                        painter = painterResource(R.drawable.search),
                        contentDescription = "Поиск",
                        tint = colors.iconColorForTop
                    )
                }
                IconButton(onClick = onClickPlayer) {
                    Icon(
                        modifier = iconModifier,
                        painter = painterResource(R.drawable.headphone_line),
                        contentDescription = "Play/Pause",
                        tint = colors.iconColorForTop
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = colors.background)
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(colors.divider)
        )
    }
}