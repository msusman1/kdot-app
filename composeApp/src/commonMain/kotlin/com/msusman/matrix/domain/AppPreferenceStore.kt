package com.msusman.matrix.domain

interface AppPreferenceStore {
    suspend fun getAppTheme(): String?
    suspend fun setAppTheme(theme: String)
}