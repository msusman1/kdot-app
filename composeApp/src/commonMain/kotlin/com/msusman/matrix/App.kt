package com.msusman.matrix

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.msusman.matrix.ui.login.LoginPresenter
import com.msusman.matrix.ui.login.LoginView
import com.msusman.matrix.ui.theme.MatrixTheme


@Composable
fun App() {
    val presenter = remember {
        LoginPresenter()
    }
    MatrixTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LoginView(
                state = presenter.present(),
                onBackClick = {})
        }
    }
}
