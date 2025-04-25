package io.kdot.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.kdot.app.ui.login.LoginScreen
import io.kdot.app.ui.onboarding.OnBoardingScreen
import io.kdot.app.ui.rooms.RoomsScreen
import io.kdot.app.ui.splash.SplashScreen
import kotlinx.serialization.Serializable

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Splash) {
        composable<Splash> {
            SplashScreen(
                onNavigateToRooms = {
                    navController.navigate(Rooms) {
                        popUpTo(Splash) { inclusive = true }
                    }
                },
                onNavigateToOnboarding = {
                    navController.navigate(Onboarding) {
                        popUpTo(Splash) { inclusive = true }
                    }
                }
            )
        }
        composable<Onboarding> {
            OnBoardingScreen(
                onSignIn = {
                    navController.navigate(Login)
                },

            )
        }
        composable<Login> {
            LoginScreen(
                onBackClick = { navController.navigateUp() },
                onLoginSuccess = {
                    navController.navigate(Rooms)
                })
        }
        composable<Rooms> {
            RoomsScreen()
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
object Rooms