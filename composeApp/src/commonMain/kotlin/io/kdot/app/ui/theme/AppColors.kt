package io.kdot.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalAppColors = compositionLocalOf<AppColors> { error("Composition Local not defined for App Colors") }

interface AppColors {
    val success: Color

    val presenceOnline: Color
    val presenceOffline: Color
    val presenceUnavailable: Color

    val verificationTrusted: Color
    val verificationUntrusted: Color
    val verificationNeutral: Color
}

val MaterialTheme.appColors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current
