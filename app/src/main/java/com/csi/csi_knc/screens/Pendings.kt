package com.csi.csi_knc.screens

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch

// ---------------------- DATA MODEL ----------------------
data class Payment(
    val id: String = "",
    val title: String = "",
    val date: String = "",
    val amount: String = "",
    val status: String = ""
)

class PaymentsViewModel : ViewModel() {
    var payments by mutableStateOf(listOf<Payment>())
        private set

    private val db = FirebaseFirestore.getInstance()

    fun loadPayments() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("Pendings").get().await()

                if(snapshot.isEmpty){
                    payments = listOf(
                        Payment(
                            id = "",
                            title = "No Pendings",
                            date = "",
                            amount = "",
                            status = ""
                        )
                    )
                }else{
                    payments = snapshot.documents.map { doc ->
                        Payment(
                            id = doc.id,
                            title = doc.getString("title") ?: "",
                            date = doc.getString("date") ?: "",
                            amount = doc.getString("amount") ?: "",
                            status = doc.getString("status") ?: ""
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pendings(navController: androidx.navigation.NavController)
{
    val  viewModel: PaymentsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val onBackClick: () -> Unit = {
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        viewModel.loadPayments()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("பாக்கிகள்") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }, modifier = Modifier.padding( start = 10.dp)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(viewModel.payments) { payment ->
                PaymentItem(payment)
                Divider(  color = Color(0xFFCACACA),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp))
            }
        }
    }
}

@Composable
fun PaymentItem(payment: Payment) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(breakTextByCharacters(payment.title, 22),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                )
            Text("₹${payment.amount}", fontSize = 18.sp, fontWeight = FontWeight.Bold,
               )
        }



        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(payment.date, fontSize = 13.sp, color = Color.Gray)
            Row {
                val isPaid = payment.status.equals("Paid", ignoreCase = true)
                val statusColor = if (isPaid) Color(0xFF4CAF50) else Color(0xFFF44336)
                val statusText = if (isPaid) "Paid" else "Unpaid"

                Box(
                    modifier = Modifier.padding(top = 5.dp, end = 5.dp)
                        .size(12.dp) // fixed size for dot
                        .background(statusColor, shape = MaterialTheme.shapes.small),
                    contentAlignment = Alignment.CenterStart
                ) {
                }

                Text(statusText, color = statusColor, fontSize = 14.sp)
            }
        }

    }
}


fun breakTextByCharacters(text: String, maxCharsPerLine: Int = 15): String {
    val result = StringBuilder()
    var count = 0
    text.forEach { char ->
        result.append(char)
        count++
        if (count >= maxCharsPerLine) {
            result.append("\n")
            count = 0
        }
    }
    return result.toString()
}


@Preview(showBackground = true)
@Composable
fun previews(){
    Pendings(navController = rememberNavController())
}