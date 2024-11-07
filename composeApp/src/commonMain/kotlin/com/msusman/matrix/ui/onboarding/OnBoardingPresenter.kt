package com.msusman.matrix.ui.onboarding

import androidx.compose.runtime.Composable
import com.msusman.matrix.Presenter
import matrixclientkmp.composeapp.generated.resources.Res
import matrixclientkmp.composeapp.generated.resources.title_app_name
import org.jetbrains.compose.resources.stringResource

class OnBoardingPresenter : Presenter<OnBoardingState> {

    @Composable
    override fun present(): OnBoardingState {
        return OnBoardingState(applicationName = stringResource(Res.string.title_app_name))
    }
}