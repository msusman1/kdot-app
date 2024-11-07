package com.msusman.matrix.data

import com.msusman.matrix.domain.AppPreferenceStore
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings

@OptIn(ExperimentalSettingsApi::class)
class AppPreferenceStoreImpl(
    private val settings: SuspendSettings
) : AppPreferenceStore {
    companion object {
        val THEME_KEY = "theme"
    }

    override suspend fun getAppTheme(): String? {
        return settings.getStringOrNull(THEME_KEY)
    }

    override suspend fun setAppTheme(theme: String) {
        settings.putString(THEME_KEY, theme)
    }
}