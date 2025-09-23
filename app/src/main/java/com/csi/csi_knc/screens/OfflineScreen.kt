package com.csi.csi_knc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.csi.csi_knc.R

@Composable
fun OfflineScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.noconnection),
            modifier = Modifier.height(300.dp).width(300.dp),
            contentScale = ContentScale.Crop,
            contentDescription = "offlineimage"
        )
        Text(
            text = "Oops! No Internet",
            fontSize = 20.sp,
            color = Color(0xFF020202)
        )
        Text(
            text = "Something wrong with your connection\nplease check and try again",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 10.dp),
            color = Color(0xFF444444),
            lineHeight = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OfflineScreenPreview(){
    OfflineScreen(navController = rememberNavController())
}