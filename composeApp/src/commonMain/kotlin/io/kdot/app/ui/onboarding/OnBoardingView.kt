package io.kdot.app.ui.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.atomic.atom.ElementLogoAtomSize
import io.kdot.app.designsystem.atomic.atom.KDotLogoAtom
import io.kdot.app.designsystem.atomic.molecule.ButtonColumnMolecule
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnBoardingScreen(
    onSignIn: () -> Unit,
    onCreateAccount: () -> Unit,
) {
    val vm = viewModel { OnBoardingViewModel() }
    val onBoardingState = vm.onBoardingState
    OnBoardingView(
        state = onBoardingState,
        onSignIn = onSignIn,
        onCreateAccount = onCreateAccount,
    )
}


@Composable
fun OnBoardingView(
    state: OnBoardingState,
    onSignIn: () -> Unit,
    onCreateAccount: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().systemBarsPadding().padding(all = 20.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalAlignment = CenterHorizontally,
        ) {
            OnBoardingContent(
                state = state,
            )
        }

        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            OnBoardingButtons(
                onSignIn = onSignIn,
                onCreateAccount = onCreateAccount,
            )
        }
    }

}

@Composable
private fun OnBoardingContent(
    state: OnBoardingState,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = BiasAlignment(
                horizontalBias = 0f, verticalBias = -0.4f
            )
        ) {
            KDotLogoAtom(
                size = ElementLogoAtomSize.Large,
                modifier = Modifier.padding(top = ElementLogoAtomSize.Large.shadowRadius / 2)
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = BiasAlignment(
                horizontalBias = 0f, verticalBias = 0.6f
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = CenterHorizontally,
            ) {
                Text(
                    text = stringResource(
                        Resources.String.screen_onboarding_welcome_title,
                        stringResource(state.applicationName)
                    ), style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        Resources.String.screen_onboarding_welcome_message,
                    ),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 17.sp),
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}


@Composable
fun OnBoardingButtons(
    onSignIn: () -> Unit,
    onCreateAccount: () -> Unit,
) {
    ButtonColumnMolecule {

        Button(
            content = { Text(stringResource(Resources.String.screen_onboarding_sign_in_manually)) },
            onClick = onSignIn,
            modifier = Modifier.fillMaxWidth()
        )

        TextButton(
            content = { Text(stringResource(Resources.String.screen_onboarding_sign_up)) },
            onClick = onCreateAccount,
            modifier = Modifier.fillMaxWidth()
        )

    }
}
