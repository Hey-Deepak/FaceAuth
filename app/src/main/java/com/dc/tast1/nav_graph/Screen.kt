package com.dc.tast1.nav_graph

sealed class Screen(val route: String){
    object Splash: Screen("splash_screen")
    object Login: Screen("login_screen")
    object Profile: Screen("profile_screen")
    object Welcome: Screen("welcome_screen")
}

