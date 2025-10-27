@file:OptIn(ExperimentalMaterial3Api::class)

package com.kpnorth.knc_app_csi.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore

// -------- ViewModel --------
class KeerthanaigalViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    var documentNames by mutableStateOf(listOf<String>())
        private set

    var selectedDocumentData by mutableStateOf<Map<String, Any>?>(null)
        private set

    var searchQuery by mutableStateOf("")
    var isSearching by mutableStateOf(false)

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

    fun searchInFirestore(query: String) {
        if (query.isBlank()) {
            loadDocumentNames()
            return
        }

        isSearching = true
        val trimmedQuery = query.trim()

        firestore.collection("Keerthanaigal")
            .get()
            .addOnSuccessListener { result ->
                val isNumericQuery = trimmedQuery.toIntOrNull() != null

                for (doc in result.documents) {
                    val data = doc.data ?: continue
                    for ((key, value) in data) {
                        val keyStr = key.trim()
                        val valueStr = value.toString().trim()

                        val match = if (isNumericQuery) {
                            // 🔹 Exact number match (e.g. "7" matches only "7", not "537")
                            keyStr.equals(trimmedQuery, ignoreCase = true)
                        } else {
                            // 🔹 Partial lyric/text match (case-insensitive)
                            valueStr.contains(trimmedQuery, ignoreCase = true) ||
                                    keyStr.contains(trimmedQuery, ignoreCase = true)
                        }

                        if (match) {
                            selectedDocumentData = data
                            isSearching = false
                            return@addOnSuccessListener
                        }
                    }
                }
                // If no match found
                selectedDocumentData = mapOf("Result" to "No match found for \"$query\"")
                isSearching = false
            }
    }


    fun clearSelection() {
        selectedDocumentData = null
    }
}

// -------- UI --------
@Composable
fun Keerthanaigal(navController: NavController) {
    val viewModel = remember { KeerthanaigalViewModel() }
    val docNames = viewModel.documentNames
    val selectedData = viewModel.selectedDocumentData
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var isSearchOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadDocumentNames()
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    AnimatedContent(
                        targetState = isSearchOpen,
                        transitionSpec = {
                            (slideInHorizontally(
                                animationSpec = tween(300),
                                initialOffsetX = { fullWidth -> -fullWidth / 2 }
                            ) + fadeIn(tween(300))) togetherWith
                                    (slideOutHorizontally(
                                        animationSpec = tween(300),
                                        targetOffsetX = { fullWidth -> fullWidth / 2 }
                                    ) + fadeOut(tween(300)))
                        },
                        label = "searchTransition"
                    ) { searchVisible ->
                        if (searchVisible) {
                            TextField(
                                value = searchText,
                                onValueChange = {
                                    searchText = it
                                    viewModel.searchQuery = it.text
                                },
                                placeholder = { Text("Search by number or lyrics...") },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxSize().background(Color(0xFFFFFFFF)).padding(horizontal = 10.dp),
                                textStyle = LocalTextStyle.current.copy(fontSize = 17.sp),
                                shape = RoundedCornerShape(8.dp),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search"
                                    )
                                },
                                trailingIcon = {
                                    IconButton(onClick = {
                                        if (searchText.text.isNotEmpty()) {
                                            viewModel.searchInFirestore(searchText.text)
                                        } else {
                                            isSearchOpen = false
                                        }
                                    }) {
                                        Icon(
                                            imageVector = if (searchText.text.isEmpty()) Icons.Default.Close else Icons.Default.ArrowForward,
                                            contentDescription = "Action"
                                        )
                                    }
                                }
                            )
                        } else {
                            Text("Keerthanaigal")
                        }
                    }
                },
                navigationIcon = {
                    if (selectedData != null) {
                        IconButton(onClick = { viewModel.clearSelection() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    if (!isSearchOpen && selectedData == null) {
                        IconButton(onClick = { isSearchOpen = true }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Open Search"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        when {
            viewModel.isSearching -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            selectedData == null -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(Color.White),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(docNames) { name ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { viewModel.loadDocumentData(name) },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1))
                        ) {
                            Text(
                                text = name,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    selectedData.forEach { (key, value) ->
                        Text(
                            text = "கீ. கீ : $key \n\n$value",
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
