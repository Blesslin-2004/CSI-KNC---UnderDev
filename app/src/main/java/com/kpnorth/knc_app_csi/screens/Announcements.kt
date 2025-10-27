package com.kpnorth.knc_app_csi.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
// ... other imports
import coil.compose.AsyncImage


import com.kpnorth.knc_app_csi.R

data class Announcement(
    val title: String = "",
    val date: String = "",
    val description: String = "",
    val noticeImage : String = ""
)

@Composable
fun Announcements(navController: NavController) {
    var announcements by remember { mutableStateOf<List<Announcement>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        try {
            val snapshot = db.collection("Announcements").get().await()
            if(snapshot.isEmpty){
                announcements = listOf(
                    Announcement(
                        title = "No Announcements",
                        date = "",
                        description = "",
                        noticeImage = ""
                    )
                )
            }else{
                announcements = snapshot.documents.map { doc ->
                    Announcement(
                        title = doc.getString("Announcement_name") ?: "",
                        date = doc.getString("Date") ?: "",
                        description = doc.getString("Description") ?: "",
                        noticeImage = doc.getString("NoticeImage") ?: ""
                    )
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            loading = false
        }
    }


Surface (
    modifier = Modifier.fillMaxSize(),
    color = Color(0xFFFFFFFF)
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 35.dp)
            .background(Color(0xFFFFFFFF))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Spacer(Modifier.width(8.dp))
            Text("அறிவிப்புகள் :", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.roboto )))
        }
        Spacer(Modifier.height(16.dp))

        if (loading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(announcements) { announcement ->
                    ExpandableAnnouncementCard(announcement)
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}
}

@Composable
fun ExpandableAnnouncementCard(announcement: Announcement) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { expanded = !expanded },
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        announcement.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.roboto))
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        announcement.date,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF111111)
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
            }
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(animationSpec = tween(400)) + fadeIn(),
                exit = shrinkVertically(animationSpec = tween(400)) + fadeOut()
            ) {
                // Wrap content in a Column to arrange them vertically
                Column {
                    Text(
                        text = announcement.description,
                        modifier = Modifier.padding(top = 8.dp),
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontSize = 16.sp
                    )

                    // 1. Check if the image URL is not empty before showing the image.
                    if (announcement.noticeImage.isNotEmpty()) {
                        Spacer(Modifier.height(8.dp)) // Add space before the image

                        // 2. Use AsyncImage from Coil to load the image from the URL.
                        AsyncImage(
                            model = announcement.noticeImage,
                            // 3. Provide a contentDescription for accessibility.
                            contentDescription = "${announcement.title} Notice Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp), // Set a height for the image
                            contentScale = ContentScale.Crop // Adjust how the image is scaled
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun preview(){
    Announcements(navController = rememberNavController())
}
