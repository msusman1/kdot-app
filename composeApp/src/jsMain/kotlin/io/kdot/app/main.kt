package io.kdot.app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.kdot.app.di.koinSharedConfiguration
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    console.log("THis is kotlin for web/js")
    onWasmReady {
        ComposeViewport(document.body!!) {
            KDotApp(koinAppDeclaration = { koinSharedConfiguration() })
        }
    }
}