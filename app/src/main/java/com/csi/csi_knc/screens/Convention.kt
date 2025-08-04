package com.csi.csi_knc.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.csi.csi_knc.Routes

@Composable
fun Convention(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is Screen Two")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.popBackStack() // Navigate back
        }) {
            Text("Back to Screen One")
        }
    }
}
