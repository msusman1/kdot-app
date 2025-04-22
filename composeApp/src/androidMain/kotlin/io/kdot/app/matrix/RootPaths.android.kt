package io.kdot.app.matrix

import okio.Path.Companion.toOkioPath
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformPathsModule(): Module = module {
    single<RootPath> {
        RootPath(androidContext().filesDir.toOkioPath())
    }
}