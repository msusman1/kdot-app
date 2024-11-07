package com.msusman.matrix.ui.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.msusman.matrix.designSystem.atomic.molecule.ButtonColumnMolecule
import com.msusman.matrix.designSystem.atomic.pages.OnBoardingPage
import com.msusman.matrix.designSystem.atomic.atom.ElementLogoAtom
import com.msusman.matrix.designSystem.atomic.atom.ElementLogoAtomSize
import matrixclientkmp.composeapp.generated.resources.Res
import matrixclientkmp.composeapp.generated.resources.screen_onboarding_sign_in_manually
import matrixclientkmp.composeapp.generated.resources.screen_onboarding_sign_up
import matrixclientkmp.composeapp.generated.resources.screen_onboarding_welcome_message
import matrixclientkmp.composeapp.generated.resources.screen_onboarding_welcome_title
import org.jetbrains.compose.resources.stringResource


@Composable
fun OnBoardingView(
    state: OnBoardingState,
    onSignIn: () -> Unit,
    onCreateAccount: () -> Unit,
    modifier: Modifier = Modifier
) {

    OnBoardingPage(modifier = modifier, content = {
        OnBoardingContent(
            state = state,
        )
    }, footer = {
        OnBoardingButtons(
            onSignIn = onSignIn,
            onCreateAccount = onCreateAccount,
        )
    })
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
            ElementLogoAtom(
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
                    text = stringResource(Res.string.screen_onboarding_welcome_title),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        Res.string.screen_onboarding_welcome_message, state.applicationName
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
            content = { Text(stringResource(Res.string.screen_onboarding_sign_in_manually)) },
            onClick = onSignIn,
            modifier = Modifier.fillMaxWidth()
        )

        TextButton(
            content = { Text(stringResource(Res.string.screen_onboarding_sign_up)) },
            onClick = onCreateAccount,
            modifier = Modifier.fillMaxWidth()
        )

    }
}
