package io.kdot.app.libraries.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionManager(private val dataStore: DataStore<Preferences>) {

    val CURRENT_NUMBER_KEY = intPreferencesKey("currentNumber")

    val currentNumber: Flow<Int> = dataStore.data.map {
        it[CURRENT_NUMBER_KEY] ?: 0
    }

    suspend fun updateCurrentNumber(number: Int) {
        dataStore.edit {
            it[CURRENT_NUMBER_KEY] = number

        }
    }
}