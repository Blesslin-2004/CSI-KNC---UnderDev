package com.csi.csi_knc.screens

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.csi.csi_knc.R
import com.csi.csi_knc.ui.theme.CSIKNCTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.csi.csi_knc.Routes

@Composable
fun HomeScreen(navController: NavController){
    val featuredItems = listOf(
        Triple("நேரலை", "(Live Meetings)", R.drawable.live_meeting),
        Triple("அறிவிப்புகள்", "(Announcements)", R.drawable.announcements),
        Triple("பாக்கிகள்", "(Pendings)", R.drawable.pending)
    )

    val prayerItems = listOf(
        Triple("1000 ஸ்தோத்திரங்கள்", "(1000 Praises)", R.drawable.praises),
        Triple("ஜெப குறிப்புகள்", "(Prayer Points)", R.drawable.prayer_points),
        Triple("ஜெப விண்ணப்பம் ", "(Prayer Request)", R.drawable.prayer_request)
    )

    val SongItems = listOf(
        Triple("கீதங்களும் கீர்த்தனைகளும்", "", R.drawable.keerthanaigal),
        Triple("கன்வென்ஷன் கீதங்கள்", "", R.drawable.convention)
    )


    Box(
            modifier = Modifier.fillMaxSize()
        ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                Spacer(Modifier.height(16.dp))

                // Today's Verse
                Card(
                    shape = RoundedCornerShape(5.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()

                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ){
                        Image(
                            painter = painterResource(id= R.drawable.dailyverse),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Dailyverse",

                        )
                        Column(modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "இன்றைய வார்த்தை",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF7BB3FE),

                                fontSize = 16.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "நான் மரண இருளின் பள்ளத்தாக்கிலே நடந்தாலும் பொல்லாப்புக்குப் பயப்படேன்; தேவரீர் என்னோடேகூடஇருக்கிறீர்; உமது கோலும் உமது தடியும் என்னைத் தேற்றும். சங்கீதம் 23:1",
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                color = Color(0xFFFFFFFF),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
    
                BirthdayDropdownCard(
                    name = "E.Blesslin",
                    date = "Jan 10, 2004",
                    imagePainter = painterResource(R.drawable.birthdaywishimage) // Replace with your image
                )

                Spacer(Modifier.height(16.dp))

                // Featured Section
                Text("Featured", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {


                    // Right side: Two vertically stacked cards
                    Row(
                        modifier = Modifier
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        FeatureCard(featuredItems[1])
                        FeatureCard(featuredItems[2])
                    }
                }
                Spacer(Modifier.height(16.dp))


                // Song book Section
                Text("Song Book", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {


                    // Right side: Two vertically stacked cards
                    Row(
                        modifier = Modifier
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SongsCard(SongItems[0]) {
                            navController.navigate(Routes.Keerthanaigal.route)
                        }
                        SongsCard(SongItems[1]) {
                            navController.navigate(Routes.Convention.route)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Prayer Support Section
                Text("Prayer Support", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    PrayerCard(prayerItems[0])
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        PrayerCard(prayerItems[1], Modifier.weight(1f))
                        PrayerCard(prayerItems[2], Modifier.weight(1f))
                    }
                }

                Spacer(Modifier.height(72.dp)) // space for bottom nav
            }

            // Bottom Navigation Bar
            BottomAppBar(
                containerColor = Color(0xFFF2F2F2),
                modifier = Modifier.align ( Alignment.BottomCenter )
            ) {
               Row (
                   modifier = Modifier.fillMaxWidth(),
                   horizontalArrangement = Arrangement.SpaceEvenly
               ){
                   BottomNavigationItem("Home", R.drawable.homeasset)
                   BottomNavigationItem("Live", R.drawable.liveasset)
                   BottomNavigationItem("Gallery", R.drawable.galleryasset)
                   BottomNavigationItem("About", R.drawable.aboutasset)
                   BottomNavigationItem("You", R.drawable.youasset)
               }
            }
        }
}

@Composable
fun BottomNavigationItem(label: String, @DrawableRes iconRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified // Use original icon colors
        )
        Text(label, fontSize = 12.sp)
    }
}


@Composable
fun FeatureCard(item: Triple<String, String, Int>) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .width(160.dp)
            .height(120.dp)

    ) {
        Box (
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = item.third),
                contentDescription = item.first,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(Color(0x55000000))
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(item.first, color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontFamily = FontFamily(
                    Font(R.font.roboto, weight = FontWeight.Normal)))
                Text(item.second, color = Color.White, fontSize = 12.sp, textAlign = TextAlign.Center, fontFamily = FontFamily(
                    Font(R.font.roboto, weight = FontWeight.Medium)))
            }
        }
    }
}

@Composable
fun PrayerCard(item: Triple<String, String, Int>, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = item.third),
                contentDescription = item.first,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier.fillMaxSize().background(Color(0x55000000))
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val Robotofont = FontFamily(
                    Font(R.font.roboto, weight = FontWeight.Normal),
                    Font(R.font.roboto, weight = FontWeight.Medium),
                    Font(R.font.roboto, weight = FontWeight.Bold)
                )
                Text(item.first, color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontFamily = FontFamily(
                    Font(R.font.roboto, weight = FontWeight.Normal)))
                Text(item.second, color = Color.White, fontSize = 12.sp, textAlign = TextAlign.Center, fontFamily = FontFamily(
                    Font(R.font.roboto, weight = FontWeight.Medium)))
            }
        }
    }
}

@Composable
fun BirthdayDropdownCard(name: String, date: String, imagePainter: Painter) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        // Clickable Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Today’s ", fontSize = 16.sp)
            Text("Birthday Wishes", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        // Slide-in Dropdown
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFDEBD0))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = "Birthday Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text("Happy Birthday", fontSize = 15.sp, textAlign = TextAlign.Center)
                    Text("$name", fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Center)
                    Text("$date", fontSize = 14.sp, textAlign = TextAlign.Center)

                }

            }
        }
    }
}

@Composable
fun SongsCard(item: Triple<String, String, Int>, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .width(160.dp)
            .height(120.dp)
            .clickable { onClick() } // <-- handle click
    )  {
        Box (
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = item.third),
                contentDescription = item.first,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(Color(0x77000000))
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(item.first, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp, textAlign = TextAlign.Center, fontFamily = FontFamily(
                    Font(R.font.roboto, weight = FontWeight.Normal)))
                Text(item.second, color = Color.White, fontSize = 12.sp, textAlign = TextAlign.Center, fontFamily = FontFamily(
                    Font(R.font.roboto, weight = FontWeight.Medium)))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun homepreviw(){
    CSIKNCTheme {
        val navController = rememberNavController()
        HomeScreen(navController)    }
}
