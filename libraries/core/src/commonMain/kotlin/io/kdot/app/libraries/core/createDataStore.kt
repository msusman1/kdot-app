package io.kdot.app.libraries.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

expect fun createDataStore(context: Any? = null): DataStore<Preferences>

fun createDataStore(producePath: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() })
}

internal const val PREF_FILE_NAME = "kdot_app.preferences_pb"