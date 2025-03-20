package io.kdot.app.libraries.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlin.jvm.Throws

fun createDataStore(context: Context): DataStore<Preferences> {
    return createDataStore(producePath = {
        context.filesDir.resolve(PREF_FILE_NAME).absolutePath
    })

}