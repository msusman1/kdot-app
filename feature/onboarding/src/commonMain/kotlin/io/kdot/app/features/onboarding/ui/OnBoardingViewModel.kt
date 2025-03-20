package io.kdot.app.features.onboarding.ui

import androidx.lifecycle.ViewModel
import io.kdot.app.libraries.core.SessionManager
import io.kdot.app.libraries.core.createDataStore
import io.kdot.app.libraries.designsystem.Resources

class OnBoardingViewModel: ViewModel() {
    val onBoardingState = OnBoardingState(applicationName = Resources.String.title_app_name)
    val sessionManager = SessionManager(createDataStore())
}