package com.msusman.matrix

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.msusman.matrix.ui.onboarding.OnBoardingState
import com.msusman.matrix.ui.onboarding.OnBoardingView
import com.msusman.matrix.ui.theme.MatrixTheme


@Composable
fun App() {
    MatrixTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            OnBoardingView(
                state = OnBoardingState(applicationName = "Matrix Client"),
                onCreateAccount = {},
                onSignIn = {})
        }
    }
}
