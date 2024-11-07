package com.msusman.matrix.designSystem.components.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressButton(
    text: String,
    showProgress: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        if (showProgress) {
            CircularProgressIndicator(
                modifier = Modifier
                    .progressSemantics()
                    .size(20.dp),
                color = LocalContentColor.current,
                strokeWidth = 2.dp,
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Button(
            content = { Text(text) },
            onClick = onClick,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
        )
    }

}