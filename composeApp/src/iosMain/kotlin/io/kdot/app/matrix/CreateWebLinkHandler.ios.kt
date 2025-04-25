package io.kdot.app.matrix

import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun platformWebLinkHandlerModule(): Module = module {
    single<WebLinkHandler> {
        WebLinkHandler { link ->

            val nsUrl = NSURL.URLWithString(link)
            if (nsUrl != null && UIApplication.sharedApplication.canOpenURL(nsUrl)) {
                UIApplication.sharedApplication.openURL(
                    nsUrl,
                    options = emptyMap<Any?, Any>(),
                    completionHandler = null
                )
            }
        }
    }
}