package io.kdot.app.di

import io.kdot.app.matrix.MatrixClientFactory
import io.kdot.app.matrix.MatrixClientFactoryImpl
import io.kdot.app.matrix.MatrixClientProvider
import io.kdot.app.matrix.platformWebLinkHandlerModule
import io.kdot.app.matrix.platformCreateMediaStoreModule
import io.kdot.app.matrix.platformCreateRepositoriesModule
import io.kdot.app.matrix.platformPathsModule
import io.kdot.app.ui.login.LoginViewModel
import io.kdot.app.ui.splash.SplashViewModel
import io.kdot.app.ui.rooms.RoomsViewModel
import io.kdot.app.ui.onboarding.OnboardingViewModel
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModules = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::RoomsViewModel)
    viewModelOf(::OnboardingViewModel)
    single<MatrixClientFactory> { MatrixClientFactoryImpl(get(), get()) }
    singleOf(::MatrixClientProvider)
}

fun KoinApplication.koinSharedConfiguration() {
    modules(
        appModules,
        platformWebLinkHandlerModule(),
        platformPathsModule(),
        platformCreateRepositoriesModule(),
        platformCreateMediaStoreModule()
    )
}
