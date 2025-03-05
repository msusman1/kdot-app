package io.kdot.app.ui.onboarding

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import io.kdot.app.utils.TestTags
import kdotapp.composeapp.generated.resources.Res
import kdotapp.composeapp.generated.resources.screen_onboarding_sign_in_manually
import kdotapp.composeapp.generated.resources.screen_onboarding_sign_up
import kdotapp.composeapp.generated.resources.title_app_name
import org.jetbrains.compose.resources.stringResource
import kotlin.test.Test
import kotlin.test.assertTrue

class OnBoardingViewTest {


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun firstUiTest() = runComposeUiTest {
        var signInClicked = false
        var createAccountClicked = false
        var appName = ""
        var siginText = ""
        var createAccountText = ""

        setContent {
            appName = stringResource(Res.string.title_app_name)
            siginText = stringResource(Res.string.screen_onboarding_sign_in_manually)
            createAccountText = stringResource(Res.string.screen_onboarding_sign_up)
            val onBoardingState = OnBoardingState(appName)
            OnBoardingView(
                state = onBoardingState,
                onSignIn = { signInClicked = true },
                onCreateAccount = { createAccountClicked = true })
        }
        onNodeWithTag(TestTags.Onboarding.title).assertTextContains(
            value = appName,
            substring = true
        )
        onNodeWithTag(TestTags.Onboarding.logo).assertExists()
        onNodeWithText(siginText).performClick()
        assertTrue(signInClicked, "Sign in is clicked but callback is not invoked")
        onNodeWithText(createAccountText).performClick()
        assertTrue(createAccountClicked, "Create Account is clicked but callback is not invoked")
    }
}