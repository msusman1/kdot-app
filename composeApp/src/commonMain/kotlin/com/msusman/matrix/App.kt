package com.msusman.matrix

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.msusman.matrix.architecture.AsyncData
import com.msusman.matrix.data.AppPreferenceStoreImpl
import com.msusman.matrix.data.createSetting
import com.msusman.matrix.domain.AppPreferenceStore
import com.msusman.matrix.ui.login.LoginFormState
import com.msusman.matrix.ui.login.LoginState
import com.msusman.matrix.ui.login.LoginView
import com.msusman.matrix.ui.login.defaultAccountProvider
import com.msusman.matrix.ui.theme.MatrixTheme
import com.russhwolf.settings.ExperimentalSettingsApi
import kotlinx.coroutines.launch
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
            LoginView(
                state = LoginState(
                    formState = LoginFormState("", ""),
                    accountProvider = defaultAccountProvider,
                    loginResultState = AsyncData.Uninitialized,
                    eventSink = { s -> }
                ),
                onBackClick = {}
            )
        }
    }
}
