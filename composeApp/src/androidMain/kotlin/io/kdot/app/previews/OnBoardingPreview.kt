package io.kdot.app.previews

import androidx.compose.runtime.Composable
import io.kdot.app.ui.onboarding.OnBoardingView

@PreviewDayNight
@Composable
fun OnBoardingPreview() {
    KDotPreview {
        OnBoardingView(
            onSignIn = {},
            onCreateAccount = {})
    }
}
