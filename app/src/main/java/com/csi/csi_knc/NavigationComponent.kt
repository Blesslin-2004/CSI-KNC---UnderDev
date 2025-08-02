package com.csi.csi_knc

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csi.csi_knc.screens.Login1Screen
import com.csi.csi_knc.screens.HomeScreen

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Splash.route) {
        composable(Routes.Splash.route) { SplashScreen(navController) }
        composable(Routes.Login1.route) { Login1Screen() }
        composable ( Routes.Home.route ) {HomeScreen()}
    }
}
