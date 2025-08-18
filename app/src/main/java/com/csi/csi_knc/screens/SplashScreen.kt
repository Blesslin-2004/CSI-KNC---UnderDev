package com.csi.csi_knc


import android.graphics.Color
import android.util.Log
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import com.csi.csi_knc.ui.theme.CSIKNCTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController? = null) {

    val currentUser = FirebaseAuth.getInstance().currentUser
    val user = currentUser.toString()



    LaunchedEffect(Unit) {
        delay(2000)
        if (currentUser != null) {
            Log.d("Current user", user)
            navController?.navigate(Routes.Home.route) {
                popUpTo(Routes.Login1.route) { inclusive = true } // remove login from back stack
                launchSingleTop = true // avoid multiple copies of home
            }
        } else {
            Log.d("Current user", "No user found")
            navController?.navigate(Routes.Login1.route)

        }
    }
    Column(modifier = Modifier.fillMaxSize().background(_root_ide_package_.androidx.compose.ui.graphics.Color(0xFFfffcee)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ){


        val logo = painterResource(id = R.drawable.knclogo)

        Image(
            painter = logo,
            contentDescription = "knclogo",
            modifier = Modifier.wrapContentSize()
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CSIKNCTheme {
        SplashScreen()
    }
}