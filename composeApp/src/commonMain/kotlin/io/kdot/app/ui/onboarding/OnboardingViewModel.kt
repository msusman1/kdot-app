package io.kdot.app.ui.onboarding

import androidx.lifecycle.ViewModel
import io.kdot.app.matrix.WebLinkHandler

class OnboardingViewModel(
    private val webLinkHandler: WebLinkHandler,
) : ViewModel() {

    fun onSignUpClicked() {
        webLinkHandler.openLink("https://account.matrix.org/register/password")
    }
}