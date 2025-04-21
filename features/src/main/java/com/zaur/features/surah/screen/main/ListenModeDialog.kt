package com.zaur.features.surah.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListenModeDialog(
    onDismiss: () -> Unit,
    onChooseOnline: () -> Unit,
    onChooseOffline: () -> Unit,
) {
    AlertDialog(onDismissRequest = {
        onDismiss()
    }, title = { Text("Как вы хотите слушать Коран?") }, text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    onChooseOffline()
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Слушать оффлайн (скачать)")
            }

            Button(
                onClick = {
                    onChooseOnline()
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Слушать онлайн")
            }
        }
    }, confirmButton = {})
}