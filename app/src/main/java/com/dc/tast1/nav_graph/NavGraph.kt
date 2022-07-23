package com.dc.tast1.nav_graph

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dc.tast1.presentation.screen.LoginScreen
import com.dc.tast1.presentation.screen.ProfileScreen
import com.dc.tast1.presentation.screen.SplashScreen
import com.dc.tast1.presentation.screen.WelcomeScreen
import com.dc.tast1.presentation.viewmodel.*


@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    splashViewModel: SplashViewModel,
    launchLoginFlow: (() -> Unit) -> Unit,
    launchImagePickerFlow: () -> Unit,
    profileViewModel: ProfileViewModel,
    sharedViewModel: SharedViewModel
    ) {

    LaunchedEffect(Unit) {
        splashViewModel.readProfileFromPrefsAndNavigate(navHostController)
    }

    val startDestination = Screen.Splash.route
    Log.d("TAG", "SetupNavGraph: $startDestination")

    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(route = Screen.Splash.route) {
            SplashScreen()
        }
        composable(route = Screen.Login.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                loginViewModel = loginViewModel,
                navHostController = navHostController,
                launchLoginFlow,
                sharedViewModel
            )
        }
        composable(
            route = Screen.Profile.route
        ) {
            ProfileScreen(
                profileViewModel = profileViewModel,
                navHostController = navHostController,
                launchImagePickerFlow,
                sharedViewModel
            )
        }
        composable(
            route = Screen.Welcome.route
        ) {
            val welcomeViewModel: WelcomeViewModel = hiltViewModel()
            WelcomeScreen(navController = navHostController, profileViewModel = profileViewModel, welcomeViewModel = welcomeViewModel)
        }
    }
}