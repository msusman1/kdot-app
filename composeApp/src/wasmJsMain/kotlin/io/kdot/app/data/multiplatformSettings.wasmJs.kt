package io.kdot.app.data

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.StorageSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import kotlinx.browser.localStorage

@OptIn(ExperimentalSettingsApi::class)
actual fun createSetting(): SuspendSettings {
    val delegate = localStorage
    val settings = StorageSettings(delegate)
    return settings.toSuspendSettings()
}