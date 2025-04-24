package io.kdot.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.kdot.app.di.koinSharedConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.base(DebugAntilog())
        setContent {
            KDotApp(
                koinAppDeclaration = {
                    androidContext(this@MainActivity)
                    androidLogger()
                    koinSharedConfiguration()
                }
            )
        }
    }
}

