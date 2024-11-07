package com.msusman.matrix.ui.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface Node {
    @Composable
    fun View(modifier: Modifier)
}


class OnBoardingNode(
    private val presenter: OnBoardingPresenter
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