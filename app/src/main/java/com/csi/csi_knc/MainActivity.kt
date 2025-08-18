package com.csi.csi_knc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import com.csi.csi_knc.ui.theme.CSIKNCTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CSIKNCTheme {
                FirebaseApp.initializeApp(this)

                Surface (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFfffcee)) // 🔵 Set your desired color here
                ) {
                    NavigationComponent()
                }
            }
        }
    }
}






