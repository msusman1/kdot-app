package com.msusman.matrix.ui.onboarding

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertTrue

class OnBoardingViewTest {


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun firstUiTest() = runComposeUiTest {
        val appName = "Matrix Client"
        val onBoardingState = OnBoardingState(appName)
        var signInClicked = false
        var createAccountClicked = false
        setContent {
            OnBoardingView(
                state = onBoardingState,
                onSignIn = { signInClicked = true },
                onCreateAccount = { createAccountClicked = true })
        }
        onNodeWithTag("onboarding_title").assertTextContains(value = appName, substring = true)
        onNodeWithTag("onboarding_logo").assertExists()
        onNodeWithText("Sign in").performClick()
        assertTrue(signInClicked, "Sign in is clicked but callback is not invoked")
        onNodeWithText("Create account").performClick()
        assertTrue(createAccountClicked, "Create Account is clicked but callback is not invoked")
    }
}