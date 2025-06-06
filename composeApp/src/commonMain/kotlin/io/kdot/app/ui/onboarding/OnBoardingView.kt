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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.atomic.atom.ElementLogoAtomSize
import io.kdot.app.designsystem.atomic.atom.KDotLogoAtom
import io.kdot.app.designsystem.atomic.molecule.ButtonColumnMolecule
import io.kdot.app.ui.theme.KDotTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnBoardingScreen(
    onSignIn: () -> Unit,
    vm: OnboardingViewModel = koinViewModel()
) {

    OnBoardingView(
        onSignIn = onSignIn,
        onCreateAccount = vm::onSignUpClicked,
    )
}


@Composable
fun KDotPreview(content: @Composable () -> Unit) {
    KDotTheme {
        Surface(content = content)
    }
}

@Preview
@Composable
fun OnBoardingPreview() {
    KDotPreview {
        OnBoardingView(
            onSignIn = {},
            onCreateAccount = {})
    }
}


@Composable
fun OnBoardingView(
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
            OnBoardingContent()
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
private fun OnBoardingContent() {
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
                        stringResource(Resources.String.title_app_name)
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
