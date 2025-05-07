package io.kdot.app.ui.roomlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SyncBanner(message: String, isError: Boolean = false) {
    val bgColor =
        if (isError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer
    val textColor =
        if (isError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor)
            .padding(8.dp)
    ) {
        Text(
            text = message,
            color = textColor,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}