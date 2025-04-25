package io.kdot.app.matrix


import org.koin.core.module.Module
import org.koin.dsl.module
import web.url.URL
import web.window.WindowTarget
import web.window.window

actual fun platformWebLinkHandlerModule(): Module = module {
    single<WebLinkHandler> {
        WebLinkHandler { link ->
            val safeUri = URL(link)
            window.open(safeUri, WindowTarget._blank)
        }
    }
}