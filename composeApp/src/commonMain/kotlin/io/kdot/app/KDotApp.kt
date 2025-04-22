package io.kdot.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.kdot.app.ui.theme.KDotTheme
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication


@Composable
fun KDotApp(koinAppDeclaration: KoinApplication.() -> Unit) {
    KoinApplication(application = koinAppDeclaration) {
        KDotTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                MainNavGraph()
            }
        }
    }
}
