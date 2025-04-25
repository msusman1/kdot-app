package io.kdot.app.matrix

import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.COLOR_SCHEME_DARK
import androidx.browser.customtabs.CustomTabsIntent.COLOR_SCHEME_LIGHT
import androidx.compose.ui.graphics.toArgb
import androidx.core.net.toUri
import io.kdot.app.ui.theme.md_theme_dark_primary
import io.kdot.app.ui.theme.md_theme_light_primary
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


actual fun platformWebLinkHandlerModule(): Module = module {
    single<WebLinkHandler> {
        WebLinkHandler { link ->
            val androidContext = androidContext()
            val safeUri = link.toUri()

            val darkParams = CustomTabColorSchemeParams.Builder().apply {
                setToolbarColor(md_theme_dark_primary.toArgb())
            }.build()
            val lightParams = CustomTabColorSchemeParams.Builder().apply {
                setToolbarColor(md_theme_light_primary.toArgb())
            }.build()

            val customTabsIntent = CustomTabsIntent.Builder().apply {
                setColorSchemeParams(COLOR_SCHEME_DARK, darkParams)
                setColorSchemeParams(COLOR_SCHEME_LIGHT, lightParams)
                setShowTitle(true)
            }.build()
            customTabsIntent.launchUrl(androidContext, safeUri)
        }
    }
}