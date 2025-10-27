package com.kpnorth.knc_app_csi.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.kpnorth.knc_app_csi.Routes

data class PendingItem(
    val pending_name: String = "",
    val amount: String = "",
    val date_taken : String ="",
    val due_date : String = ""
)

data class personalPendingItem(
    val pending_name: String = "",
    val amount: String = "",
    val date_taken : String ="",
    val status : String = "",
    val created_at : Timestamp? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalPending(modifier: Modifier = Modifier) {
    var pendingList by remember { mutableStateOf<List<personalPendingItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var fmnum = 250

    // 🔥 Fetch from Firestore collection: personal_pending
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("church_members")
            .document(fmnum.toString())
            .collection("pendings")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    isLoading = false
                    return@addSnapshotListener
                }

                val data = snapshot?.documents
                    ?.mapNotNull { it.toObject(personalPendingItem::class.java) }
                    ?.sortedByDescending { it.created_at } // 👈 Sort by created_at (new → old)
                    ?: emptyList()

                pendingList = data
                isLoading = false
            }
    }


    Scaffold(
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                pendingList.isEmpty() -> Text("No personal pending found")
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                        items(pendingList) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = androidx.compose.ui.graphics.Color(0xFFF1F1F1)
                                ),
                                elevation = CardDefaults.cardElevation(6.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "${item.pending_name}", style = MaterialTheme.typography.titleMedium)
                                        Text(
                                            text = item.status,
                                            color = Color.White, // text color white
                                            modifier = Modifier
                                                .background(
                                                    color = if (item.status == "Unpaid") Color(0xFFec5158) else Color(0xFF11ba69),
                                                    shape = RoundedCornerShape(3.dp) // rounded edges
                                                )
                                                .padding(horizontal = 4.dp, vertical = 2.dp), // inner space
                                            style = MaterialTheme.typography.bodyMedium
                                        )

                                    }

                                    Text(text = "₹${item.amount}", style = MaterialTheme.typography.titleMedium)
                                    Text(text = "${item.date_taken}", style = MaterialTheme.typography.bodyMedium)



                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonPending(modifier: Modifier = Modifier) {
    var commonList by remember { mutableStateOf<List<PendingItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // 🔥 Fetch from Firestore collection: common_pending
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Pendings")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    isLoading = false
                    return@addSnapshotListener
                }
                val data = snapshot?.documents?.mapNotNull { it.toObject(PendingItem::class.java) } ?: emptyList()
                commonList = data
                isLoading = false
            }
    }

    Scaffold() { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                commonList.isEmpty() -> Text("No common pending found")
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                        items(commonList) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = androidx.compose.ui.graphics.Color(0xFFF1F1F1)
                                ),
                                elevation = CardDefaults.cardElevation(6.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(text = "${item.pending_name}", style = MaterialTheme.typography.titleMedium)
                                    Text(text = "₹${item.amount}", style = MaterialTheme.typography.bodyMedium)
                                    Text(text = "${item.date_taken}", style = MaterialTheme.typography.bodyMedium)

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Pendings1(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Playlist Screen")
    }
}

enum class Destination(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val contentDescription: String
) {
    PERSONALPENDING("personalpending", "Personal Pending", Icons.Default.MusicNote, "Personal Pending"),
    COMMONPENDING("Commonpending", "Common pending", Icons.Default.Album, "Common Pending"),
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.PERSONALPENDING -> PersonalPending()
                    Destination.COMMONPENDING -> CommonPending()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun Pendings(navController: NavController) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    val tabs = listOf("Personal Pending", "Common Pending")

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
        ) {
            // 🔹 Top Tabs
            PrimaryTabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        modifier = Modifier.padding(top = 10.dp),
                        text = {
                            Text(
                                text = title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }

            // 🔹 Tab Content Area
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Crossfade(targetState = selectedTabIndex) { index ->
                    when (index) {
                        0 -> PersonalPending()
                        1 -> CommonPending()
                    }
                }

            }
        }
    }
}


