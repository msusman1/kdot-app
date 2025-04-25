package io.kdot.app.matrix

import io.kdot.app.OS
import io.kdot.app.getOs
import org.koin.core.module.Module
import org.koin.dsl.module
import java.awt.Desktop
import java.net.URI

actual fun platformWebLinkHandlerModule(): Module = module {
    single<WebLinkHandler> {

        WebLinkHandler { link ->
            val safeUri = URI(link)
            val desktop = Desktop.getDesktop()
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(safeUri)
            } else when (getOs()) {
                OS.LINUX -> {
                    Runtime.getRuntime().exec(arrayOf("xdg-open", safeUri.toString()))
                }

                else -> throw UnsupportedOperationException("AWT does not support the BROWSE action on this platform")

            }
        }
    }
}