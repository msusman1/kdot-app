package io.kdot.app.utils

import androidx.compose.ui.graphics.ImageBitmap
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap


@OptIn(ExperimentalResourceApi::class)
fun ByteArray.toImageBitmap(): ImageBitmap? {
    return try {
        decodeToImageBitmap()
    } catch (e: Exception) {
        null
    }

}
