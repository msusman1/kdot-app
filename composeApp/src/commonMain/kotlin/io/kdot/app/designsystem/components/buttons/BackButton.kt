package io.kdot.app.designsystem.components.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import kdotapp.composeapp.generated.resources.Res
import kdotapp.composeapp.generated.resources.action_back
import org.jetbrains.compose.resources.stringResource

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.AutoMirrored.Default.ArrowBack,
    contentDescription: String = stringResource(Res.string.action_back),
    enabled: Boolean = true,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
    ) {
        Icon(imageVector, contentDescription = contentDescription)
    }
}