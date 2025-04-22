package io.kdot.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.kdot.app.di.koinSharedConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

