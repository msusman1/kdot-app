package com.msusman.matrix

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.msusman.matrix.data.AppPreferenceStoreImpl
import com.msusman.matrix.data.createSetting
import com.msusman.matrix.domain.AppPreferenceStore
import com.msusman.matrix.ui.theme.MatrixTheme
import com.russhwolf.settings.ExperimentalSettingsApi
import kotlinx.coroutines.launch
import matrixclientkmp.composeapp.generated.resources.Res
import matrixclientkmp.composeapp.generated.resources.app_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalSettingsApi::class)
@Composable
@Preview
fun App() {
    val appStore: AppPreferenceStore = remember { AppPreferenceStoreImpl(createSetting()) }
    var darkTheme by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit, {
        darkTheme = appStore.getAppTheme() == "dark"
    })
    fun toggleTheme() {
        coroutineScope.launch {
            appStore.setAppTheme(if (darkTheme) "light" else "dark")
        }
    }
    MatrixTheme(
        darkTheme = darkTheme
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            var showContent by remember { mutableStateOf(false) }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Hit me!")
                }
                Button(onClick = ::toggleTheme) {
                    Text("TOggle theme")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(
                        Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painterResource(Res.drawable.app_logo), null)
                        Text("Welcome to Compose: $greeting")
                    }
                }
            }
        }
    }
}
