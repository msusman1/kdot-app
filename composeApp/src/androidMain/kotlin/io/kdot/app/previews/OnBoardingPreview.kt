package io.kdot.app.previews

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.kdot.app.ui.onboarding.OnBoardingState
import io.kdot.app.ui.onboarding.OnBoardingView


@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun OnBoardingPreview() {
    OnBoardingView(
        state = OnBoardingState("test app"),
        onSignIn = {},
        onCreateAccount = {})

}
