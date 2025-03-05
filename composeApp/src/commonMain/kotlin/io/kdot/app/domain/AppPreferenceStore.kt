package io.kdot.app.domain

interface AppPreferenceStore {
    suspend fun getAppTheme(): String?
    suspend fun setAppTheme(theme: String)
}