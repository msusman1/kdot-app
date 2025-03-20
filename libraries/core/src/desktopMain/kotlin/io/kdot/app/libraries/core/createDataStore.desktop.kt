package io.kdot.app.libraries.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    return createDataStore {
        PREF_FILE_NAME
    }
}