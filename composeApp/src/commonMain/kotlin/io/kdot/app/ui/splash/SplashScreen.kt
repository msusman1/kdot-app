package io.kdot.app.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.kdot.app.designsystem.components.CircularProgressIndicator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(), // or hiltViewModel() if you're using Hilt
    onNavigateToRooms: () -> Unit,
    onNavigateToOnboarding: () -> Unit
) {
    val state by viewModel.uiState

    LaunchedEffect(state) {
        when (state) {
            is SplashUiState.NavigateToRooms -> onNavigateToRooms()
            is SplashUiState.NavigateToOnboarding -> onNavigateToOnboarding()
            else -> {} // Loading: do nothing
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}