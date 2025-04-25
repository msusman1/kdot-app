package io.kdot.app.matrix

import org.koin.core.module.Module

fun interface WebLinkHandler {
    fun openLink(url: String)
}

expect fun platformWebLinkHandlerModule(): Module