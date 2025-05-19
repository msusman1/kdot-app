package io.kdot.app.di

import io.kdot.app.libraries.dateformatter.DateFormatter
import io.kdot.app.libraries.dateformatter.DateFormatterImpl
import io.kdot.app.libraries.dateformatter.DateFormatters
import io.kdot.app.libraries.dateformatter.LocalDateTimeProvider
import io.kdot.app.libraries.dateformatter.formatters.DateFormatterDay
import io.kdot.app.libraries.dateformatter.formatters.DateFormatterFull
import io.kdot.app.libraries.dateformatter.formatters.DateFormatterMonth
import io.kdot.app.libraries.dateformatter.formatters.DateFormatterTime
import io.kdot.app.libraries.dateformatter.formatters.DateFormatterTimeOnly
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
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
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

}

val dateModules = module {
    single<DateFormatter> { DateFormatterImpl(get(), get(), get(), get(), get()) }
    singleOf(::MatrixClientProvider)
    single { DateFormatterFull(get(), get(), get()) }
    single { DateFormatterMonth(get(), get()) }
    single { DateFormatterDay(get(), get()) }
    single { DateFormatterTime(get(), get()) }
    single { DateFormatterTimeOnly(get(), get()) }
    single { LocalDateTimeProvider(get(), get()) }
    single { DateFormatters(get(), get()) }
    single<Clock> { Clock.System }
    single<TimeZone> { TimeZone.currentSystemDefault() }
}

fun KoinApplication.koinSharedConfiguration() {
    modules(
        appModules,
        dateModules,
        platformWebLinkHandlerModule(),
        platformPathsModule(),
        platformCreateRepositoriesModule(),
        platformCreateMediaStoreModule()
    )
}
