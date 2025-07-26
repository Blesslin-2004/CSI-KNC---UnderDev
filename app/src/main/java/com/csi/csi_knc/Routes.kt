package com.csi.csi_knc

sealed class Routes(val route: String) {
    data object Splash : Routes("splash")
    data object Login1 : Routes("login1")
}