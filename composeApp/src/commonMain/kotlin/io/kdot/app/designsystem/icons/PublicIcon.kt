package io.kdot.app.designsystem.icons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kdot.app.designsystem.Resources
import org.jetbrains.compose.resources.stringResource


@Composable
fun BoxScope.PublicIcon() {
    Box(Modifier.align(Alignment.BottomEnd), contentAlignment = Alignment.Center) {
        Icon(
            Icons.Default.Circle,
            stringResource(Resources.String.unencrypted),
            Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.background,
        )
        Icon(
            Icons.Default.Public,
            stringResource(Resources.String.public),
            Modifier.size(12.dp),
            tint = MaterialTheme.colorScheme.onSecondary,
        )
    }
}
