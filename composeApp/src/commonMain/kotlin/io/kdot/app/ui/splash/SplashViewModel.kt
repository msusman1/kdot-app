package io.kdot.app.ui.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kdot.app.matrix.MatrixClientFactory
import io.kdot.app.matrix.MatrixClientProvider
import kotlinx.coroutines.launch
import net.folivo.trixnity.client.MatrixClient

sealed class SplashUiState {
    data object Loading : SplashUiState()
    data object NavigateToRooms : SplashUiState()
    data object NavigateToOnboarding : SplashUiState()
}


class SplashViewModel(
    private val matrixClientProvider: MatrixClientProvider,
    private val matrixClientFactory: MatrixClientFactory
) : ViewModel() {
    private val _uiState = mutableStateOf<SplashUiState>(SplashUiState.Loading)
    val uiState: State<SplashUiState> = _uiState


    init {
        initClient()
    }

    private fun initClient() = viewModelScope.launch {
        matrixClientFactory.createFromStore().onSuccess { client ->
            if (client != null && client.loginState.value == MatrixClient.LoginState.LOGGED_IN) {
                matrixClientProvider.setClient(client)
                _uiState.value = SplashUiState.NavigateToRooms
            } else {
                _uiState.value = SplashUiState.NavigateToOnboarding
            }
        }.onFailure {
            _uiState.value = SplashUiState.NavigateToOnboarding
        }
    }

}