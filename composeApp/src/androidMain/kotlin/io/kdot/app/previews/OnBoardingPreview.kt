package io.kdot.app.previews

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.kdot.app.features.onboarding.ui.OnBoardingState
import io.kdot.app.features.onboarding.ui.OnBoardingView
import io.kdot.app.libraries.designsystem.Resources

@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun OnBoardingPreview() {

    OnBoardingView(
        state = OnBoardingState(Resources.String.title_app_name),
        onSignIn = {},
        onCreateAccount = {})
}
