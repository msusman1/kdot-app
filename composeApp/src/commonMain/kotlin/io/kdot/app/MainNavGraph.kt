package io.kdot.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.kdot.app.feature.login.ui.LoginScreen
import io.kdot.app.features.onboarding.ui.OnBoardingScreen
import io.kdot.app.libraries.designsystem.screens.SplashScreen
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Onboarding)
    }
    NavHost(navController = navController, startDestination = Splash) {
        composable<Splash> {
            SplashScreen()
        }
        composable<Onboarding> {
            OnBoardingScreen(
                onSignIn = {
                    navController.navigate(Login)
                },
                onCreateAccount = {
                    navController.navigate(Register)
                }
            )
        }
        composable<Login> {
            LoginScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }

}

@Serializable
object Splash

@Serializable
object Onboarding

@Serializable
object Login

@Serializable
object Register