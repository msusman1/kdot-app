package io.kdot.app.ui.onboarding

import androidx.compose.runtime.Composable
import io.kdot.app.Presenter
import kdotapp.composeapp.generated.resources.Res
import kdotapp.composeapp.generated.resources.title_app_name
import org.jetbrains.compose.resources.stringResource

class OnBoardingPresenter : Presenter<io.kdot.app.ui.onboarding.OnBoardingState> {

    @Composable
    override fun present(): io.kdot.app.ui.onboarding.OnBoardingState {
        return io.kdot.app.ui.onboarding.OnBoardingState(applicationName = stringResource(Res.string.title_app_name))
    }
}