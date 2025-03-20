package io.kdot.app.feature.login.ui

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
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import io.kdot.app.libraries.architecture.AsyncData
import io.kdot.app.libraries.designsystem.Resources
import io.kdot.app.libraries.designsystem.atomic.molecule.ButtonColumnMolecule
import io.kdot.app.libraries.designsystem.atomic.molecule.IconTitleSubtitleMolecule
import io.kdot.app.libraries.designsystem.components.BigIcon
import io.kdot.app.libraries.designsystem.components.buttons.BackButton
import io.kdot.app.libraries.designsystem.components.buttons.ProgressButton
import io.kdot.app.libraries.designsystem.components.dialogs.ErrorDialog
import io.kdot.app.libraries.designsystem.components.onTabOrEnterKeyFocusNext
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
    onBackClick: () -> Unit,
) {
    val vm = viewModel { LoginViewModel() }
    val state = vm.loginState.collectAsState()
    LoginView(
        state = state.value,
        onBackClick = onBackClick,
        handleAction = vm::handleAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    state: LoginState,
    onBackClick: () -> Unit,
    handleAction: (LoginEvent) -> Unit,

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

        handleAction(LoginEvent.Submit)
    }

    Scaffold(
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
                    Resources.String.screen_account_provider_signin_title,
                    "Matrix.org"
                ),
                subTitle = stringResource(Resources.String.screen_login_subtitle)
            )
            Spacer(Modifier.height(40.dp))
            LoginForm(
                state = state,
                isLoading = isLoading,
                onSubmit = ::submit,
                handleAction = handleAction
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
                        text = stringResource(Resources.String.action_continue),
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
                    handleAction(LoginEvent.Reset)
                })
            }
        }
    }
}

@Composable
private fun LoginForm(
    state: LoginState,
    isLoading: Boolean,
    handleAction: (LoginEvent) -> Unit,
    onSubmit: () -> Unit,
) {
    var loginFieldState by remember { mutableStateOf(state.formState.username) }
    var passwordFieldState by remember { mutableStateOf(state.formState.password) }

    val focusManager = LocalFocusManager.current


    Column {
        Text(
            text = stringResource(Resources.String.screen_login_form_header),
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
                Text(text = stringResource(Resources.String.common_username))
            },
            onValueChange = {
                val sanitized = it.sanitize()
                loginFieldState = sanitized
                handleAction(LoginEvent.setUsername(sanitized))
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
                    IconButton(
                        onClick = {
                            loginFieldState = ""
                            handleAction(LoginEvent.setUsername(""))
                        }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(Resources.String.action_clear)
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
                handleAction(LoginEvent.setPassword(sanitized))
            },
            placeholder = {
                Text(text = stringResource(Resources.String.common_password))
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                val description =
                    if (passwordVisible) stringResource(Resources.String.a11y_hide_password) else stringResource(
                        Resources.String.a11y_show_password
                    )

                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                ) {
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
        title = stringResource(Resources.String.dialog_title_error),
        content = stringResource(loginError(error)),
        onDismiss = onDismiss
    )
}