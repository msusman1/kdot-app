package io.kdot.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.kdot.app.designsystem.screens.SplashScreen
import io.kdot.app.ui.login.LoginScreen
import io.kdot.app.ui.onboarding.OnBoardingScreen
import kotlinx.serialization.Serializable

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    LaunchedEffect(Unit) {
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
                onBackClick = { navController.navigateUp() })
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