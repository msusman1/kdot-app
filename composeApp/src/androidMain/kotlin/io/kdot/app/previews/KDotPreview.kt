package io.kdot.app.previews

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.kdot.app.ui.theme.KDotTheme
import android.content.res.Configuration


@Preview(name = "Day", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class PreviewDayNight

@Composable
fun KDotPreview(content: @Composable () -> Unit) {
    KDotTheme {
        Surface(content = content)
    }
}