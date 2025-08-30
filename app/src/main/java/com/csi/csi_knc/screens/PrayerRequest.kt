package com.csi.csi_knc.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.graphics.Color as ComposeColor
import com.csi.csi_knc.R
import com.google.api.Context

@Composable
fun PrayerRequest1(navController: NavController){
    var name by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }
    var request by remember { mutableStateOf("") }
    val context = LocalContext.current

    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 50.dp).background(Color(0xFFFFFFFF)),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Prayer Request Form", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            placeholder = {Text("பெயர்")},
            colors = TextFieldDefaults.colors(
               focusedIndicatorColor = ComposeColor(0xFF27A9FF),
                       unfocusedIndicatorColor = ComposeColor(0xFF9E9E9E),
                   errorIndicatorColor = ComposeColor(0xFFD32F2F),
                   focusedPlaceholderColor = ComposeColor(0xFF9899A2),
                   unfocusedPlaceholderColor = ComposeColor(0xFF757575),
                focusedContainerColor =  ComposeColor(0xFFFBFBFB),
                unfocusedContainerColor = ComposeColor(0xFFFFFFFF)
            ),
            modifier = Modifier.fillMaxWidth().background(Color(0xFFFFFFFF))
        )

        OutlinedTextField(
            value = place,
            onValueChange = { place = it },
            label = { Text("Address") },
            placeholder = {Text("முகவரி")},
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = ComposeColor(0xFF27A9FF),
                unfocusedIndicatorColor = ComposeColor(0xFF9E9E9E),
                errorIndicatorColor = ComposeColor(0xFFD32F2F),
                focusedPlaceholderColor = ComposeColor(0xFF9899A2),
                unfocusedPlaceholderColor = ComposeColor(0xFF757575),
                focusedContainerColor =  ComposeColor(0xFFFBFBFB),
                unfocusedContainerColor = ComposeColor(0xFFFFFFFF)
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = request,
            onValueChange = { request = it },
            label = { Text("Prayer request") },
            placeholder = { Text("உங்கள் விண்ணப்பத்தை இங்கு எழுதவும்...")},
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = ComposeColor(0xFF27A9FF),
                unfocusedIndicatorColor = ComposeColor(0xFF9E9E9E),
                errorIndicatorColor = ComposeColor(0xFFD32F2F),
                focusedPlaceholderColor = ComposeColor(0xFF9899A2),
                unfocusedPlaceholderColor = ComposeColor(0xFF757575),
                focusedContainerColor =  ComposeColor(0xFFFBFBFB),
                unfocusedContainerColor = ComposeColor(0xFFFFFFFF)
            ),
                      modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 5
        )

        Button(
            onClick = {
                val requestData = hashMapOf(
                    "name" to name,
                    "place" to place,
                    "details" to request,
                    "timestamp" to System.currentTimeMillis()
                )

                if(!name.isEmpty() && !place.isEmpty() && !request.isEmpty()){
                    db.collection("PrayerRequests")
                        .add(requestData)
                        .addOnSuccessListener {
                            println("Data successfully written with ID: ${it.id}")
                            Toast.makeText(context, "Prayer Request Sent to Church", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                        .addOnFailureListener { e ->
                            println("Error writing document: $e")
                        }
                }
                else{
                    Toast.makeText(context, "Please fill all fields" , Toast.LENGTH_SHORT).show()
                }


            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF27A9FF)
            )


        ) {
            Text(text = "SUBMIT",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun PrayerRequest(navController: NavController){
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var place by remember { mutableStateOf(TextFieldValue("")) }
    var request by remember { mutableStateOf(TextFieldValue("")) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp).background(Color(0xFFFFFFFF)),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Prayer Request Form", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            placeholder = {Text("பெயர்")},
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = ComposeColor(0xFF27A9FF),
                unfocusedIndicatorColor = ComposeColor(0xFF9E9E9E),
                errorIndicatorColor = ComposeColor(0xFFD32F2F),
                focusedPlaceholderColor = ComposeColor(0xFF9899A2),
                unfocusedPlaceholderColor = ComposeColor(0xFF757575),
                focusedContainerColor =  ComposeColor(0xFFFBFBFB),
                unfocusedContainerColor = ComposeColor(0xFFFFFFFF)
            ),
            modifier = Modifier.fillMaxWidth().background(Color(0xFFFFFFFF))
        )

        OutlinedTextField(
            value = place,
            onValueChange = { place = it },
            label = { Text("Address") },
            placeholder = {Text("முகவரி")},
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = ComposeColor(0xFF27A9FF),
                unfocusedIndicatorColor = ComposeColor(0xFF9E9E9E),
                errorIndicatorColor = ComposeColor(0xFFD32F2F),
                focusedPlaceholderColor = ComposeColor(0xFF9899A2),
                unfocusedPlaceholderColor = ComposeColor(0xFF757575),
                focusedContainerColor =  ComposeColor(0xFFFBFBFB),
                unfocusedContainerColor = ComposeColor(0xFFFFFFFF)
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = request,
            onValueChange = { request = it },
            label = { Text("Prayer request") },
            placeholder = { Text("உங்கள் விண்ணப்பத்தை இங்கு எழுதவும்...")},
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = ComposeColor(0xFF27A9FF),
                unfocusedIndicatorColor = ComposeColor(0xFF9E9E9E),
                errorIndicatorColor = ComposeColor(0xFFD32F2F),
                focusedPlaceholderColor = ComposeColor(0xFF9899A2),
                unfocusedPlaceholderColor = ComposeColor(0xFF757575),
                focusedContainerColor =  ComposeColor(0xFFFBFBFB),
                unfocusedContainerColor = ComposeColor(0xFFFFFFFF)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 5
        )

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF27A9FF)
            )
        ) {
            Text(text = "SUBMIT",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.Bold)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun requestpreview(){
    PrayerRequest(navController = rememberNavController())
}