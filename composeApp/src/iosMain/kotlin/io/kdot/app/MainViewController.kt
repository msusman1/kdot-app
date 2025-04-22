package io.kdot.app

import androidx.compose.ui.window.ComposeUIViewController
import io.kdot.app.di.koinSharedConfiguration

fun MainViewController() = ComposeUIViewController {
    KDotApp(koinAppDeclaration = {koinSharedConfiguration()})
}