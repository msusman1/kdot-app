package io.kdot.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.kdot.app.di.koinSharedConfiguration

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KDot",
    ) {

        KDotApp(koinAppDeclaration = { koinSharedConfiguration() })
    }
}