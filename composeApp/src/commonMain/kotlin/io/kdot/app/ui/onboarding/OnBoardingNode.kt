package io.kdot.app.ui.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface Node {
    @Composable
    fun View(modifier: Modifier)
}


class OnBoardingNode(
    private val presenter: io.kdot.app.ui.onboarding.OnBoardingPresenter
) : Node {
    fun handleSignInClick() {
        //handle signin
    }

    fun handleRegisterClick() {
        //handle registration
    }

    @Composable
    override fun View(modifier: Modifier) {
        val state = presenter.present()
        OnBoardingView(
            state = state,
            onSignIn = ::handleSignInClick,
            onCreateAccount = ::handleRegisterClick,
            modifier = modifier,
        )
    }

}