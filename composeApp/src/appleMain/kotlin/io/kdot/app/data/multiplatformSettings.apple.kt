package io.kdot.app.data

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import platform.Foundation.NSUserDefaults

@OptIn(ExperimentalSettingsApi::class)
actual fun createSetting(): SuspendSettings {
    val delegate = NSUserDefaults()
    return NSUserDefaultsSettings(delegate).toSuspendSettings()
}