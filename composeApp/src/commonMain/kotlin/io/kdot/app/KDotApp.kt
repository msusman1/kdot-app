package io.kdot.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.kdot.app.feature.login.ui.LoginView
import io.kdot.app.feature.login.ui.LoginViewModel
import io.kdot.app.ui.theme.KDotTheme


@Composable
fun KDotApp() {
    val vm = remember { LoginViewModel() }
    val loginState by vm.loginState.collectAsState()
    KDotTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LoginView(
                state = loginState,
                onBackClick = {},
                handleAction = vm::handleEvents,
            )
        }
    }
}
