package com.kpnorth.knc_app_csi

sealed class Routes(val route: String) {
    data object Splash : Routes("splash")
    data object Login1 : Routes("login1")
    data object Home : Routes("home")
    data object Keerthanaigal : Routes("keerthanaigal")
    data object Convention : Routes("convention")
    data object Announcements : Routes("announcements")
    data object Pendings : Routes("pendings")
    data object Praises : Routes("praises")
    data object PrayerRequest1 : Routes("prayerrequest")
    data object LiveScreen : Routes("livescreen")
    data object AboutScreen : Routes("aboutscreen")
    data object OfflineScreen : Routes("offlinescreen")
    data object OrderofService : Routes("orderofservice")
    data object PrayerPoints : Routes("prayerpoints")
}
