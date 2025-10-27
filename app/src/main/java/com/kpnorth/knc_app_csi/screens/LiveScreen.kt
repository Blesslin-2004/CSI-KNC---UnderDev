package com.kpnorth.knc_app_csi.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun LiveScreen(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) } // track loading state

    Box(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoading = false
                        }
                    }
                    loadUrl("https://www.youtube.com/@christchurchkonganthanparai/streams")
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        )

        // Overlay loading indicator
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f)), // optional dim background
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Example extra UI below WebView
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(30.dp)
                .background(androidx.compose.ui.graphics.Color.White)
        ) {}
    }
}




@Preview(showBackground = true)
@Composable
fun livescreen(){
    LiveScreen(navController = rememberNavController())
}