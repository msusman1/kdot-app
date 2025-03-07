package io.kdot.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.kdot.app.ui.onboarding.OnBoardingView
import io.kdot.app.ui.theme.KDotTheme


@Composable
fun KDotApp() {
    val presenter = remember {
        io.kdot.app.ui.onboarding.OnBoardingPresenter()
    }
    KDotTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            OnBoardingView(
                state = presenter.present(),
                onSignIn = {},
                onCreateAccount = {})
        }
    }
}
