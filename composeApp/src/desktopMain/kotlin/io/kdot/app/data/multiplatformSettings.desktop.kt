package io.kdot.app.data

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import java.util.prefs.Preferences

@OptIn(ExperimentalSettingsApi::class)
actual fun createSetting(): SuspendSettings {
    val delegate: Preferences = Preferences.userRoot()
    val setting = PreferencesSettings(delegate)
    return setting.toFlowSettings()
}