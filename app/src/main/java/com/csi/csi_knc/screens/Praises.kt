package com.csi.csi_knc.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.csi.csi_knc.R


data class ThousandPraises(
   val praise: String = ""
)



@Composable
fun Praises(navController: NavController) {
    var praises by remember { mutableStateOf<List<ThousandPraises>>(emptyList()) }

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



    Surface (
        modifier = Modifier.fillMaxSize().padding(bottom = 50.dp),
        color = Color(0xFFFFFFFF)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, 35.dp, 10.dp)
                .background(Color(0xFFFFFFFF))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(Modifier.width(8.dp))
                Text("1000 ஸ்தோத்திரங்கள் :", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.roboto )))
            }
            Spacer(Modifier.height(16.dp))

            // Display praises in list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(praises) { item ->
                    PraiseCard(item)
                }
            }

        }



    }

}


@Composable
fun PraiseCard(praise: ThousandPraises) {
    var checked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left side - text
            Text(
                text = praise.praise,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)

            )

            // Right side - checkbox
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF27A9FF),
                    uncheckedColor = Color(0xFF4C515B),
                    checkmarkColor = Color.White
                ))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Output() {
    val dummy = listOf(
        ThousandPraises("Praise the Lord"),
        ThousandPraises("God is good"),
        ThousandPraises("Blessed be His name")
    )

    Column {
        dummy.forEach {
            PraiseCard(it)
        }
    }
}