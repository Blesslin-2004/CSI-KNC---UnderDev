package com.kpnorth.knc_app_csi

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kpnorth.knc_app_csi.screens.*

@Composable
fun NavigationComponent() {
    val userViewModel: UserViewModel = viewModel()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Splash.route) {
        composable(Routes.Splash.route) { SplashScreen(navController) }
        composable(Routes.Home.route) { HomeScreen(navController) }
        composable(Routes.Login1.route) { Login1Screen(navController) }
        composable(Routes.Keerthanaigal.route) { Keerthanaigal(navController) }
        composable(Routes.Convention.route) { Convention(navController) }
        composable(Routes.Announcements.route) { Announcements(navController) }
        composable(Routes.Pendings.route) { Pendings(navController) }
        composable(Routes.Praises.route){ Praises(navController)}
        composable (Routes.PrayerRequest1.route){ PrayerRequest1(navController) }
        composable (Routes.LiveScreen.route){ LiveScreen(navController) }
        composable(Routes.AboutScreen.route) { AboutScreen(navController) }
        composable(Routes.OfflineScreen.route) { OfflineScreen(navController) }
        composable(Routes.OrderofService.route) { OrderofService(navController) }
        composable(Routes.PrayerPoints.route) { PrayerPoints( viewModel = userViewModel ,navController) }


    }
}
