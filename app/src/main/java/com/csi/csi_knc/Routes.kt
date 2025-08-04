package com.csi.csi_knc

sealed class Routes(val route: String) {
    data object Splash : Routes("splash")
    data object Login1 : Routes("login1")
    data object Home : Routes("home")
    data object Keerthanaigal : Routes("keerthanaigal")
    data object Convention : Routes("convention")
}