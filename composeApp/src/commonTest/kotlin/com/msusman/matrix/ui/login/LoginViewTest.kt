package com.msusman.matrix.ui.login

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.text.AnnotatedString
import com.msusman.matrix.architecture.AsyncData
import com.msusman.matrix.domain.model.AuthenticationException
import com.msusman.matrix.utils.TestTags
import matrixclientkmp.composeapp.generated.resources.Res
import matrixclientkmp.composeapp.generated.resources.a11y_hide_password
import matrixclientkmp.composeapp.generated.resources.a11y_show_password
import matrixclientkmp.composeapp.generated.resources.screen_login_error_invalid_credentials
import org.jetbrains.compose.resources.stringResource
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class LoginViewTest {


    @Test
    fun verifyTitleDisplayAccountProviderTitle() = runComposeUiTest {

        val loginState = LoginState(
            formState = LoginFormState(username = "", password = ""),
            accountProvider = AccountProvider(
                url = "https://matrix.org"
            ),
            loginResultState = AsyncData.Uninitialized,
            eventSink = { ev -> }
        )
        setContent {
            LoginView(loginState, onBackClick = {})
        }
        onNodeWithTag(TestTags.Login.title).assertTextContains("matrix.org", substring = true)
    }


    @Test
    fun verifyClearIconDisplaysWhenUserTypeinAndClickingOnClearIconClearstheUsername() =
        runComposeUiTest {
            val loginState = LoginState(
                formState = LoginFormState.Default,
                accountProvider = defaultAccountProvider,
                loginResultState = AsyncData.Uninitialized,
                eventSink = { ev -> }
            )
            setContent {
                LoginView(state = loginState, onBackClick = {})
            }

            onNodeWithTag(TestTags.Login.clearIcon).assertDoesNotExist()
            onNodeWithTag(TestTags.Login.userNameInput).performTextInput("msusman")
            onNodeWithTag(TestTags.Login.clearIcon).assertExists()
            onNodeWithTag(TestTags.Login.clearIcon).performClick()
            onNodeWithTag(TestTags.Login.userNameInput).assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.EditableText,
                    AnnotatedString("")
                )
            )
            onNodeWithTag(TestTags.Login.clearIcon).assertDoesNotExist()

        }

    @Test
    fun verifyDefaultPasswordModeAndIconIsVisibilityOffAndClickingTheVisibilityToggleIconChangeTheModeAndIcon() =
        runComposeUiTest {
            val loginState = LoginState(
                formState = LoginFormState.Default,
                accountProvider = defaultAccountProvider,
                loginResultState = AsyncData.Uninitialized,
                eventSink = { ev -> }
            )
            var onDescription = ""
            var offDescription = ""
            setContent {
                onDescription = stringResource(Res.string.a11y_show_password)
                offDescription = stringResource(Res.string.a11y_hide_password)
                LoginView(state = loginState, onBackClick = {})
            }
            val passwordInputNode = onNodeWithTag(TestTags.Login.passwordInput)
            passwordInputNode.assertExists()
            passwordInputNode.performTextInput("pass")
            passwordInputNode.assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.EditableText,
                    AnnotatedString("••••")
                )
            )
            val passwordToggleButton = onNodeWithTag(TestTags.Login.passwordToggleButton)
            onNodeWithContentDescription(onDescription).assertExists()
            onNodeWithContentDescription(offDescription).assertDoesNotExist()
            passwordToggleButton.performClick()
            onNodeWithContentDescription(onDescription).assertDoesNotExist()
            onNodeWithContentDescription(offDescription).assertExists()
            passwordInputNode.assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.EditableText,
                    AnnotatedString("pass")
                )
            )
        }

    @Test
    fun testContinueButtonIsDisabledWhenUsernameAndPasswordAreEmpty() = runComposeUiTest {
        val loginState = LoginState(
            formState = LoginFormState(username = "user", password = "pass"),
            accountProvider = defaultAccountProvider,
            loginResultState = AsyncData.Uninitialized,
            eventSink = { ev -> })

        setContent {
            LoginView(state = loginState, onBackClick = {})
        }
        val continueButton = onNodeWithText("Continue")
        continueButton.assertExists()
        continueButton.assertIsEnabled()
    }

    @Test
    fun testLoadingDisplaysProgressIndicator() = runComposeUiTest {
        val loginState = LoginState(
            formState = LoginFormState(username = "user", password = "pass"),
            accountProvider = defaultAccountProvider,
            loginResultState = AsyncData.Loading(),
            eventSink = { ev -> })

        setContent {
            LoginView(state = loginState, onBackClick = {})
        }
        val progressIndicator = onNodeWithTag("progress_bar")
        progressIndicator.assertExists()
    }

    @Test
    fun testDisplayErrorDialogOnException() = runComposeUiTest {
        var error = ""
        val loginState = LoginState(
            formState = LoginFormState(username = "user", password = "pass"),
            accountProvider = defaultAccountProvider,
            loginResultState = AsyncData.Failure(error = AuthenticationException.Generic("M_FORBIDDEN")),
            eventSink = { ev -> })

        setContent {
            error = stringResource(Res.string.screen_login_error_invalid_credentials)
            LoginView(state = loginState, onBackClick = {})
        }
        val errorDialog = onNodeWithText(error)
        errorDialog.assertExists()
    }
}