package io.kdot.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.kdot.app.feature.login.ui.LoginScreen
import io.kdot.app.features.onboarding.ui.OnBoardingScreen
import kotlinx.serialization.Serializable

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Onboarding) {
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
object Onboarding


@Serializable
object Login

@Serializable
object Register