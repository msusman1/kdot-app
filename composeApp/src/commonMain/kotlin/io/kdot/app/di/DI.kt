package io.kdot.app.di

import io.kdot.app.libraries.matrixui.media.ImageLoaderFactory
import io.kdot.app.libraries.matrixui.media.ImageLoaderFactoryImpl
import io.kdot.app.matrix.MatrixClientFactory
import io.kdot.app.matrix.MatrixClientFactoryImpl
import io.kdot.app.matrix.MatrixClientProvider
import io.kdot.app.matrix.platformWebLinkHandlerModule
import io.kdot.app.matrix.platformCreateMediaStoreModule
import io.kdot.app.matrix.platformCreateRepositoriesModule
import io.kdot.app.matrix.platformPathsModule
import io.kdot.app.ui.login.LoginViewModel
import io.kdot.app.ui.splash.SplashViewModel
import io.kdot.app.ui.roomlist.RoomListViewModel
import io.kdot.app.ui.onboarding.OnboardingViewModel
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModules = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::RoomListViewModel)
    viewModelOf(::OnboardingViewModel)
    single<MatrixClientFactory> { MatrixClientFactoryImpl(get(), get()) }
    single<ImageLoaderFactory> { ImageLoaderFactoryImpl() }
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
