package com.kpnorth.knc_app_csi.screens

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.kpnorth.knc_app_csi.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.navigation.NavController
import com.kpnorth.knc_app_csi.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.ui.window.Dialog
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun HomeScreen(navController: NavController){
    val featuredItems = listOf(
        Triple("அறிவிப்புகள்", "(Announcements)", R.drawable.announcements),
        Triple("பாக்கிகள்", "(Pendings)", R.drawable.pending)
    )

    val prayerItems = listOf(
        Triple("ஆராதனை முறைமை", "(Order of Service)", R.drawable.serviceorder),
        Triple("ஜெப குறிப்புகள்", "(Prayer Points)", R.drawable.prayer_points),
        Triple("ஜெப விண்ணப்பம் ", "(Prayer Request)", R.drawable.prayer_request),
        Triple("1000 ஸ்தோத்திரங்கள்", "(1000 Praises)", R.drawable.praises)

    )

    val SongItems = listOf(
        Triple("கீதங்களும்\nகீர்த்தனைகளும்", "", R.drawable.keerthanaigal),
        Triple("கன்வென்ஷன் கீதங்கள்", "", R.drawable.convention)
    )

    var verse by remember { mutableStateOf<String?>(null) }
    var reference by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val auth = FirebaseAuth.getInstance()
    var showdialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context as Activity

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Exit App", fontFamily = FontFamily(Font(R.font.roboto))) },
            text = { Text("Are you sure you want to quit?", fontSize = 17.sp, fontFamily = FontFamily(Font(R.font.roboto)))},
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    activity.finish()
                }) {
                    Text("Yes",fontSize = 15.sp, fontFamily = FontFamily(Font(R.font.roboto)))
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("No", fontSize = 15.sp, fontFamily = FontFamily(Font(R.font.roboto)))
                }
            }
        )
    }
    LaunchedEffect(Unit) {
        val result = todayverse()
        result?.let {
            verse = it.first
            reference = it.second
        }
        isLoading = false

        checkForUpdate(activity)

        context.observeConnectivity().collect { state ->
            if (state == ConnectionState.Unavailable) {
                navController.navigate("offlinescreen") {
                    popUpTo(0)
                }
            }
        }
    }

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

                Spacer(Modifier.height(20.dp))

                // Today's Verse
                Card(
                    shape = RoundedCornerShape(5.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    modifier = Modifier.fillMaxSize()

                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.dailyverse),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Dailyverse",
                        )

                        Column(
                            modifier = Modifier.padding(16.dp).background(Color(0x55000000)).fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "இன்றைய வார்த்தை",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF7BB3FE),
                                fontSize = 16.sp
                            )
                            Spacer(Modifier.height(8.dp))

                            //  transition for verse
                            Crossfade(targetState = isLoading to verse) { (loading, v) ->
                                if (loading) {
                                    Text(
                                        text = "உங்களுக்கான இன்றைய ஆசீர்வாதமான வார்த்தை...",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium,
                                    )
                                    Spacer(Modifier.height(5.dp))

                                } else {
                                    Text(
                                        text = v ?: "Not available",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            Spacer(Modifier.height(5.dp))

                            Crossfade(targetState = isLoading to reference) { (loading, r) ->
                                if (loading) {
                                    Text(
                                        text = "...",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium
                                    )
                                } else {
                                    Text(
                                        text = r ?: "",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
    
              /*  BirthdayDropdownCard(
                    name = "E.Blesslin",
                    date = "Jan 10, 2004",
                    imagePainter = painterResource(R.drawable.birthdaywishimage) // Replace with your image
                )*/

                Spacer(Modifier.height(16.dp))

                // Featured Section
                Text("Featured", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))

                    // Right side: Two vertically stacked cards
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FeatureCard(featuredItems[0], Modifier.weight(1f)){
                            navController.navigate(Routes.Announcements.route)
                        }
                        FeatureCard(featuredItems[1], Modifier.weight(1f)) {
                            if(auth.currentUser?.isAnonymous == true){
                                showdialog = true
                            }else{
                                navController.navigate(Routes.Pendings.route)

                            }
                        }
                        if (showdialog) {
                            RestrictedAccessPopup { showdialog = false }
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
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SongsCard(SongItems[0], Modifier.weight(1f)) {
                            navController.navigate(Routes.Keerthanaigal.route)
                        }
                        SongsCard(SongItems[1], Modifier.weight(1f)) {
                            navController.navigate(Routes.Convention.route)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Prayer Support Section
                Text("Prayer Support", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    PrayerCard(prayerItems[0]){
                            navController.navigate(Routes.OrderofService.route)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        PrayerCard(prayerItems[1], Modifier.weight(1f)){
                            navController.navigate(Routes.PrayerPoints.route)
                        }
                        PrayerCard(prayerItems[2], Modifier.weight(1f)){
                            navController.navigate(Routes.PrayerRequest1.route)
                        }
                    }
                    PrayerCard(prayerItems[3]){
                        navController.navigate(Routes.Praises.route)
                    }
                }

                Spacer(Modifier.height(100.dp)) // space for bottom nav
            }

            // Bottom Navigation Bar
        BottomAppBar(
            containerColor = Color(0xFFFBFBFB),
            modifier = Modifier
                .align(Alignment.BottomCenter).shadow(
                    elevation = (-6).dp,
                    shape = RoundedCornerShape(8.dp),
                    ambientColor = Color.Black,
                    spotColor = Color.Black,
                    clip = false
                ),
        ) {
               Row (
                   modifier = Modifier.fillMaxWidth(),
                   horizontalArrangement = Arrangement.SpaceEvenly
               ){
                   BottomNavigationItem("Home", R.drawable.homeasset){
                   }
                   BottomNavigationItem("Services", R.drawable.liveasset){
                        navController.navigate(Routes.LiveScreen.route)
                   }
                   BottomNavigationItem("Church", R.drawable.aboutasset){
                        navController.navigate(Routes.AboutScreen.route)
                   }
               }
            }
        }
}


@Composable
fun HomeScreen2(navController: NavController) {

    val featuredItems = listOf(
        Triple("அறிவிப்புகள்", "(Announcements)", R.drawable.announcements),
        Triple("பாக்கிகள்", "(Pendings)", R.drawable.pending)
    )

    val prayerItems = listOf(
        Triple("ஆராதனை முறைமை", "(Order of Service)", R.drawable.serviceorder),
        Triple("ஜெப குறிப்புகள்", "(Prayer Points)", R.drawable.prayer_points),
        Triple("ஜெப விண்ணப்பம் ", "(Prayer Request)", R.drawable.prayer_request),
        Triple("1000 ஸ்தோத்திரங்கள்", "(1000 Praises)", R.drawable.praises)
    )

    val SongItems = listOf(
        Triple("கீதங்களும்\nகீர்த்தனைகளும்", "", R.drawable.keerthanaigal),
        Triple("கன்வென்ஷன் கீதங்கள்", "", R.drawable.convention)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(20.dp))

            // Today's Verse
            Card(
                shape = RoundedCornerShape(5.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dailyverse),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Dailyverse",
                    )

                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "இன்றைய வார்த்தை",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7BB3FE),
                            fontSize = 16.sp
                        )
                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "உங்களுக்கான இன்றைய ஆசீர்வாதமான வார்த்தை...",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                        )
                        Spacer(Modifier.height(5.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Featured Section
            Text("Featured", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FeatureCard(featuredItems[0], Modifier.weight(1f)) {
                    navController.navigate(Routes.Announcements.route)
                }
                FeatureCard(featuredItems[1], Modifier.weight(1f)) {}
            }

            Spacer(Modifier.height(16.dp))

            // Song book Section
            Text("Song Book", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SongsCard(SongItems[0], Modifier.weight(1f)) {
                    navController.navigate(Routes.Keerthanaigal.route)
                }
                SongsCard(SongItems[1], Modifier.weight(1f)) {
                    navController.navigate(Routes.Convention.route)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Prayer Support Section
            Text("Prayer Support", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PrayerCard(prayerItems[0]) {}
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    PrayerCard(prayerItems[1], Modifier.weight(1f)) {}
                    PrayerCard(prayerItems[2], Modifier.weight(1f)) {
                        navController.navigate(Routes.PrayerRequest1.route)
                    }
                }
                PrayerCard(prayerItems[3]) {
                    navController.navigate(Routes.Praises.route)
                }
            }

            Spacer(Modifier.height(100.dp)) // space for bottom nav
        }

        // Row above Bottom Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFF444444))
                .align(Alignment.BottomCenter)  // anchor to bottom
                .offset(y=(-75).dp),       // push it up above BottomAppBar height
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Above BottomAppBar", color = Color.White)
        }


        // Bottom Navigation Bar
        BottomAppBar(
            containerColor = Color(0xFFFBFBFB),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .shadow(
                    elevation = (-6).dp,
                    shape = RoundedCornerShape(8.dp),
                    ambientColor = Color.Black,
                    spotColor = Color.Black,
                    clip = false
                ),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomNavigationItem("Home", R.drawable.homeasset) {
                    navController.navigate(Routes.Home.route)
                }
                BottomNavigationItem("Services", R.drawable.liveasset) {
                    navController.navigate(Routes.LiveScreen.route)
                }
                BottomNavigationItem("Offering", R.drawable.offeringasset) {}
                BottomNavigationItem("Church", R.drawable.aboutasset) {
                    navController.navigate(Routes.AboutScreen.route)
                }
                BottomNavigationItem("Account", R.drawable.accountasset) {}
            }
        }
    }
}



@Composable
fun  BottomNavigationItem(label: String, @DrawableRes iconRes: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    )
     {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(24.dp).clickable{onClick()},
            tint = Color.Unspecified,
        )
        Text(label, fontSize = 12.sp, modifier = Modifier.clickable{onClick()}, color = Color(0xFF000000))
    }
}

@Composable
fun FeatureCard(
    item: Triple<String, String, Int>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .height(130.dp)
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = item.third),
                contentDescription = item.first,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x55000000))
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    item.first,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.roboto, weight = FontWeight.Normal))
                )
                Text(
                    item.second,
                    color = Color.White,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.roboto, weight = FontWeight.Medium))
                )
            }
        }
    }
}



@Composable
fun PrayerCard(item: Triple<String, String, Int>, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .fillMaxSize()
            .height(130.dp)
            .clickable { onClick() }
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
fun SongsCard(item: Triple<String, String, Int>,modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .height(130.dp)
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
                    .background(Color(0x77000000)),
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

suspend fun todayverse() : Pair<String, String>?{
    val db = FirebaseFirestore.getInstance()
    val todaydate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    val firestore = db.collection("DailyVerses")
        .document(todaydate)
        .get()
        .await()

    return firestore.takeIf { it.exists() }?.let {
        val verse = it.getString("verse").orEmpty()
        val reference = it.getString("reference").orEmpty()
        Pair(verse, reference)
    }
}

@Composable
fun RestrictedAccessPopup(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Restricted",
                    tint = Color(0xFFd32f2f),
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Access Restricted",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Not Available for Guest Login. Only church members can access this feature.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { onDismiss() },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("OK", color = Color.White)
                }
            }
        }
    }
}

fun checkForUpdate(activity: Activity) {
    val appUpdateManager = AppUpdateManagerFactory.create(activity)

    val appUpdateInfoTask = appUpdateManager.appUpdateInfo

    appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
        ) {
            // Start immediate update flow
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.IMMEDIATE,
                activity,
                123 // requestCode
            )
        }
    }
}

enum class ConnectionState { Available, Unavailable }

fun Context.observeConnectivity() = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun getCurrentState(): ConnectionState {
        val network = connectivityManager.activeNetwork
        val caps = connectivityManager.getNetworkCapabilities(network)
        return if (caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true) {
            ConnectionState.Available
        } else {
            ConnectionState.Unavailable
        }
    }

    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) { trySend(ConnectionState.Available) }
        override fun onLost(network: Network) { trySend(ConnectionState.Unavailable) }
    }

    // Emit initial state
    trySend(getCurrentState())

    // Register callback
    val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()
    connectivityManager.registerNetworkCallback(request, callback)

    awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
}.distinctUntilChanged()



@Composable
@Preview(showBackground = true)
fun homescreenpreview(){
    HomeScreen2(navController = rememberNavController())
}