package com.kpnorth.knc_app_csi.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// -----------------------------
// 1️⃣ Data model
// -----------------------------
data class User(
    val point: String = "",
)

// -----------------------------
// 2️⃣ ViewModel
// -----------------------------
class UserViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        // Listen for live updates
        db.collection("PrayerPoints")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    e.printStackTrace()
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { it.toObject(User::class.java) }
                    _users.value = list
                }
            }
    }

    fun loadDocumentData(name: String) {
        db.collection("PrayerPoints").document(name).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val user = doc.toObject(User::class.java)
                    println("Clicked on user: $user")
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}

// -----------------------------
// 3️⃣ UI Composables
// -----------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerPoints(viewModel: UserViewModel = viewModel(), navController: NavController) {
    val users by viewModel.users.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Text(text = "Prayer Points")

                }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(users) { user ->
                UserCard(
                    user = user,
                    onClick = { viewModel.loadDocumentData(user.point) }
                )
            }
        }
    }
}

@Composable
fun UserCard(user: User, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = user.point, style = MaterialTheme.typography.titleMedium)

        }
    }
}
