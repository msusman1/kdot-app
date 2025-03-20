@file:OptIn(ExperimentalForeignApi::class)

package io.kdot.app.libraries.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


actual fun createDataStore(context: Any?): DataStore<Preferences> {
    val docDir = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )

    return createDataStore(
        producePath = {
            requireNotNull(docDir).path + "/$PREF_FILE_NAME"
        })

}