package com.msusman.matrix.designSystem.components.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun ProgressButton(
    text: String,
    showProgress: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Button(
            content = {
                Text(text)
                Spacer(modifier = Modifier.width(8.dp))
                if (showProgress) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .testTag("progress_bar")
                            .progressSemantics()
                            .size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp,
                    )
                }
            },
            onClick = onClick,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
        )

    }

}