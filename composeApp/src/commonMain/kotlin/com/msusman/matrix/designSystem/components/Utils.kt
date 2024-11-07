package com.msusman.matrix.designSystem.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type

fun Modifier.onTabOrEnterKeyFocusNext(focusManager: FocusManager): Modifier = onPreviewKeyEvent { event ->
    if (event.key == Key.Tab || event.key == Key.Enter) {
        if (event.type == KeyEventType.KeyUp) {
            focusManager.moveFocus(FocusDirection.Down)
        }
        true
    } else {
        false
    }
}
