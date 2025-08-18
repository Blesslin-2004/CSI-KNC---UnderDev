package com.csi.csi_knc

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csi.csi_knc.screens.*

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Splash.route) {
        composable(Routes.Splash.route) { SplashScreen(navController) }
        composable(Routes.Home.route) { HomeScreen(navController) }
        composable(Routes.Login1.route) { Login1Screen(navController) }
        composable(Routes.Keerthanaigal.route) { Keerthanaigal(navController) }
        composable(Routes.Convention.route) { Convention(navController) }
        composable(Routes.Announcements.route) { Announcements(navController) }
    }
}
