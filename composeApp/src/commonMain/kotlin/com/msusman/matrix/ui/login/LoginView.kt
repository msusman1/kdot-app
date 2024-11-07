package com.msusman.matrix.ui.login


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.msusman.matrix.architecture.AsyncData
import com.msusman.matrix.designSystem.atomic.molecule.ButtonColumnMolecule
import com.msusman.matrix.designSystem.atomic.molecule.IconTitleSubtitleMolecule
import com.msusman.matrix.designSystem.components.BigIcon
import com.msusman.matrix.designSystem.components.buttons.BackButton
import com.msusman.matrix.designSystem.components.buttons.ProgressButton
import com.msusman.matrix.designSystem.components.dialogs.ErrorDialog
import com.msusman.matrix.designSystem.components.onTabOrEnterKeyFocusNext
import matrixclientkmp.composeapp.generated.resources.Res
import matrixclientkmp.composeapp.generated.resources.a11y_hide_password
import matrixclientkmp.composeapp.generated.resources.a11y_show_password
import matrixclientkmp.composeapp.generated.resources.action_clear
import matrixclientkmp.composeapp.generated.resources.action_continue
import matrixclientkmp.composeapp.generated.resources.common_password
import matrixclientkmp.composeapp.generated.resources.common_username
import matrixclientkmp.composeapp.generated.resources.dialog_title_error
import matrixclientkmp.composeapp.generated.resources.screen_account_provider_signin_title
import matrixclientkmp.composeapp.generated.resources.screen_login_form_header
import matrixclientkmp.composeapp.generated.resources.screen_login_subtitle
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    state: LoginState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isLoading by remember(state.loginResultState) {
        derivedStateOf {
            state.loginResultState is AsyncData.Loading
        }
    }
    val focusManager = LocalFocusManager.current

    fun submit() {
        // Clear focus to prevent keyboard issues with textfields
        focusManager.clearFocus(force = true)

        state.eventSink(LoginEvent.Submit)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = { BackButton(onClick = onBackClick) },
            )
        }
    ) { padding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(padding)
                .consumeWindowInsets(padding)
                .verticalScroll(state = scrollState)
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        ) {
            // Title
            IconTitleSubtitleMolecule(
                modifier = Modifier.padding(top = 20.dp, start = 16.dp, end = 16.dp),
                iconStyle = BigIcon.Style.Default(Icons.Filled.AccountCircle),
                title = stringResource(
                    Res.string.screen_account_provider_signin_title,
                    state.accountProvider.title
                ),
                subTitle = stringResource(Res.string.screen_login_subtitle)
            )
            Spacer(Modifier.height(40.dp))
            LoginForm(
                state = state,
                isLoading = isLoading,
                onSubmit = ::submit
            )
            // Min spacing
            Spacer(Modifier.height(24.dp))
            // Flexible spacing to keep the submit button at the bottom
            Spacer(modifier = Modifier.weight(1f))
            // Submit
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                ButtonColumnMolecule {
                    ProgressButton(
                        text = stringResource(Res.string.action_continue),
                        showProgress = isLoading,
                        onClick = ::submit,
                        enabled = state.submitEnabled || isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }

            if (state.loginResultState is AsyncData.Failure) {
                LoginErrorDialog(error = state.loginResultState.error, onDismiss = {
                    state.eventSink(LoginEvent.Reset)
                })
            }
        }
    }
}

@Composable
private fun LoginForm(
    state: LoginState,
    isLoading: Boolean,
    onSubmit: () -> Unit,
) {
    var loginFieldState by remember { mutableStateOf(state.formState.username) }
    var passwordFieldState by remember { mutableStateOf(state.formState.password) }

    val focusManager = LocalFocusManager.current
    val eventSink = state.eventSink

    Column {
        Text(
            text = stringResource(Res.string.screen_login_form_header),
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = loginFieldState,
            readOnly = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .onTabOrEnterKeyFocusNext(focusManager),
            placeholder = {
                Text(text = stringResource(Res.string.common_username))
            },
            onValueChange = {
                val sanitized = it.sanitize()
                loginFieldState = sanitized
                eventSink(LoginEvent.setUsername(sanitized))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            singleLine = true,
            trailingIcon = if (loginFieldState.isNotEmpty()) {
                {
                    IconButton(onClick = {
                        loginFieldState = ""
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(Res.string.action_clear)
                        )
                    }
                }
            } else {
                null
            },
        )
        var passwordVisible by remember { mutableStateOf(false) }
        if (state.loginResultState is AsyncData.Loading) {
            // Ensure password is hidden when user submits the form
            passwordVisible = false
        }
        Spacer(Modifier.height(20.dp))
        OutlinedTextField(
            value = passwordFieldState,
            readOnly = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .onTabOrEnterKeyFocusNext(focusManager),
            onValueChange = {
                val sanitized = it.sanitize()
                passwordFieldState = sanitized
                eventSink(LoginEvent.setPassword(sanitized))
            },
            placeholder = {
                Text(text = stringResource(Res.string.common_password))
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                val description =
                    if (passwordVisible) stringResource(Res.string.a11y_hide_password) else stringResource(
                        Res.string.a11y_show_password
                    )

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { onSubmit() }
            ),
            singleLine = true,
        )
    }
}

/**
 * Ensure that the string does not contain any new line characters, which can happen when pasting values.
 */
private fun String.sanitize(): String {
    return replace("\n", "")
}

@Composable
private fun LoginErrorDialog(error: Throwable, onDismiss: () -> Unit) {
    ErrorDialog(
        title = stringResource(Res.string.dialog_title_error),
        content = stringResource(loginError(error)),
        onDismiss = onDismiss
    )
}
