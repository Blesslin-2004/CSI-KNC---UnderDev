package com.csi.csi_knc.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

// -------- ViewModel --------
class KeerthanaigalViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    var documentNames by mutableStateOf(listOf<String>())
        private set

    var selectedDocumentData by mutableStateOf<Map<String, Any>?>(null)
        private set

    fun loadDocumentNames() {
        firestore.collection("Keerthanaigal")
            .get()
            .addOnSuccessListener { result ->
                documentNames = result.documents.map { it.id }
            }
    }

    fun loadDocumentData(docName: String) {
        firestore.collection("Keerthanaigal")
            .document(docName)
            .get()
            .addOnSuccessListener { snapshot ->
                selectedDocumentData = snapshot.data
            }
    }

    fun clearSelection() {
        selectedDocumentData = null
    }
}

// -------- UI --------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Keerthanaigal(navController: NavController) {
    val viewModel = remember { KeerthanaigalViewModel() }
    val docNames = viewModel.documentNames
    val selectedData = viewModel.selectedDocumentData

    LaunchedEffect(Unit) {
        viewModel.loadDocumentNames()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)   // 👈 ensures the full screen is white
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(if (selectedData == null) "Keerthanaigal" else "Details")
                    },
                    navigationIcon = {
                        if (selectedData != null) {
                            IconButton(onClick = { viewModel.clearSelection() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    }
                )
            }
        ) { padding ->
            if (selectedData == null) {
                // List View
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize().background(androidx.compose.ui.graphics.Color(0xFFFFFFFF)),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(docNames) { name ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    viewModel.loadDocumentData(name)
                                },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFBFBFB))

                        ) {
                            Text(
                                text = name,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            } else {
                // Details View
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(16.dp).verticalScroll(rememberScrollState())
                ) {
                    selectedData.forEach { (key, value) ->
                        Text(
                            text = " கீ. கீ : $key: \n \n$value",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun KeerthanaigalPreview() {
    Keerthanaigal(navController = rememberNavController())
}
