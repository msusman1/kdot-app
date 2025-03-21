package io.kdot.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.kdot.app.ui.theme.KDotTheme


@Composable
fun KDotApp() {
    KDotTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainNavGraph()
        }
    }
}
