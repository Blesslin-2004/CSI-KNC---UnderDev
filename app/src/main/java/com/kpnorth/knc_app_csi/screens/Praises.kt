package com.kpnorth.knc_app_csi.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.kpnorth.knc_app_csi.R
import kotlinx.coroutines.tasks.await

data class ThousandPraises(
    val praise: String = ""
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Praises(navController: NavController) {
    var praises by remember { mutableStateOf<List<ThousandPraises>>(emptyList()) }
    var currentPage by remember { mutableStateOf(0) }
    val itemsPerPage = 20

    // Fetch from Firestore
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        try {
            val snapshot = db.collection("Praises").get().await()
            praises = snapshot.documents.map { doc ->
                ThousandPraises(
                    praise = doc.getString("praise") ?: ""
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val totalPages = (praises.size + itemsPerPage - 1) / itemsPerPage

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, 35.dp, 10.dp)
                .background(Color.White)
        ) {
            // Top bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    "1000 ஸ்தோத்திரங்கள் :",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.roboto))
                )
            }

            Spacer(Modifier.height(16.dp))

            // Animated content for pagination
            AnimatedContent(
                targetState = currentPage,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(300)) togetherWith
                                slideOutHorizontally(
                                    targetOffsetX = { -it / 2 },
                                    animationSpec = tween(400)
                                ) + fadeOut(animationSpec = tween(300))
                    } else {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(300)) togetherWith
                                slideOutHorizontally(
                                    targetOffsetX = { it / 2 },
                                    animationSpec = tween(400)
                                ) + fadeOut(animationSpec = tween(300))
                    }
                }, label = "", modifier = Modifier.weight(1f)
            ) { page ->
                val currentItems = praises.drop(page * itemsPerPage).take(itemsPerPage)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(currentItems) { index, item ->
                        // Global index number
                        val globalIndex = (currentPage * itemsPerPage) + index + 1
                        PraiseCard(globalIndex, item)
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // Pagination controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (currentPage > 0) currentPage-- },
                    enabled = currentPage > 0
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
                }

                Text(
                    text = "Page ${currentPage + 1} of $totalPages",
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = { if (currentPage < totalPages - 1) currentPage++ },
                    enabled = currentPage < totalPages - 1
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                }
            }
        }
    }
}

@Composable
fun PraiseCard(index: Int, praise: ThousandPraises) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),

        ) {
            Text(
                text = "$index. ",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E2E2E),
                fontSize = 16.sp
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = praise.praise,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

