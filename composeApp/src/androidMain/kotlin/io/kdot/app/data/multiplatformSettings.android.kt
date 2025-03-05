package io.kdot.app.data

import android.content.Context
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.SharedPreferencesSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings

@OptIn(ExperimentalSettingsApi::class)
actual fun createSetting(): SuspendSettings {
    val preference = io.kdot.app.MatrixApplication.instance.getSharedPreferences(
        "matrix.preference_pb",
        Context.MODE_PRIVATE
    )
    return SharedPreferencesSettings(preference).toSuspendSettings()
}