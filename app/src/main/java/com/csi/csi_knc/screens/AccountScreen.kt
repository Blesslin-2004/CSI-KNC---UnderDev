package com.csi.csi_knc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.csi.csi_knc.R
import android.content.Context
import androidx.compose.runtime.remember
import com.google.firebase.firestore.FirebaseFirestore

val db = FirebaseFirestore.getInstance()

//For Front and back-end
@Composable
fun AccountScreen(navController: NavController): String? {
    val context = LocalContext.current

    val prefs = context.getSharedPreferences("Accountnumber", Context.MODE_PRIVATE)
    var familynumber =  prefs.getString("familynumber", "") ?: ""

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

        Row(
            modifier = Modifier.padding(top = 30.dp, start = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .width(2.dp)
                    .height(20.dp)
                    .background(Color(0xFF2DB4EB))
            ) {  }

            Text(text = familynumber, fontWeight = FontWeight.SemiBold, fontSize = 19.sp,
                modifier = Modifier.padding(start = 5.dp))

//            Image(
//                painter = painterResource(R.drawable.editasset),
//                contentScale = ContentScale.Crop,
//                contentDescription = "Edit icon"
//            )
        }

        //Family head

        Row{
            Column {
                Column {
                    Text(text = "Family head name:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "ஞானக்குமார் ", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column {
                    Text(text = "Date of Birth:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column {
                    Text(text = "Baptism date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column{
                    Text(text = "Marriage date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

            }

            Column {

                Column(
                    modifier = Modifier.padding(start = 25.dp)
                ) {
                    Text(text = "Contact Number:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "9361985499", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column(
                    modifier = Modifier.padding(start = 25.dp)
                ) {
                    Text(text = "Date of joining:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column(
                    modifier = Modifier.padding(start = 25.dp)
                ) {
                    Text(text = "Communion date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }
            }
        }

//split line
        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFDDDDDD))
        ) { }

        //Spouse name

        Row{
            Column {
                Column {
                    Text(text = "Spouse name:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "ஸ்டெல்லா மேரி ", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column {
                    Text(text = "Date of Birth:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column {
                    Text(text = "Baptism date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }



            }

            Column(
                modifier = Modifier.padding(start = 25.dp)
            ) {

                Column{
                    Text(text = "Marriage date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column{
                    Text(text = "Communion date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }
            }
        }

//split line
        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFDDDDDD))
        ) { }


        //Family member

        Row{
            Column {
                Column {
                    Text(text = "Family member 1:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "விக்டோர் ஐசக்", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column {
                    Text(text = "Date of Birth:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column {
                    Text(text = "Baptism date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }



            }

            Column(
                modifier = Modifier.padding(start = 25.dp)
            ) {

                Column{
                    Text(text = "Marriage date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column{
                    Text(text = "Communion date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }
            }
        }

//split line
        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFDDDDDD))
        ) { }


    }
}


//For design
@Composable
fun AccountScreen2(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {

        Row(
            modifier = Modifier.padding(top = 30.dp, start = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .width(2.dp)
                    .height(20.dp)
                    .background(Color(0xFF2DB4EB))
            ) { }

            Text(text = "001", fontWeight = FontWeight.SemiBold, fontSize = 19.sp,
                modifier = Modifier.padding(start = 5.dp))

//            Image(
//                painter = painterResource(R.drawable.editasset),
//                contentScale = ContentScale.Crop,
//                contentDescription = "Edit icon"
//            )
        }

        //Family head

        Row{
            Column {
                Column {
                    Text(text = "Family head name:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "ஞானக்குமார் ", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column {
                    Text(text = "Date of Birth:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column {
                    Text(text = "Baptism date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column{
                    Text(text = "Marriage date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

            }

            Column {

                Column(
                    modifier = Modifier.padding(start = 25.dp)
                ) {
                    Text(text = "Contact Number:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "9361985499", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column(
                    modifier = Modifier.padding(start = 25.dp)
                ) {
                    Text(text = "Date of joining:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column(
                    modifier = Modifier.padding(start = 25.dp)
                ) {
                    Text(text = "Communion date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }
            }
        }

//split line
        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFDDDDDD))
        ) { }

        //Spouse name

        Row{
            Column {
                Column {
                    Text(text = "Spouse name:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "ஸ்டெல்லா மேரி ", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column {
                    Text(text = "Date of Birth:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column {
                    Text(text = "Baptism date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }



            }

            Column(
                modifier = Modifier.padding(start = 25.dp)
            ) {

                Column{
                    Text(text = "Marriage date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column{
                    Text(text = "Communion date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }
            }
        }

//split line
        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFDDDDDD))
        ) { }


        //Family member

        Row{
            Column {
                Column {
                    Text(text = "Family member 1:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "விக்டோர் ஐசக்", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column {
                    Text(text = "Date of Birth:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column {
                    Text(text = "Baptism date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }



            }

            Column(
                modifier = Modifier.padding(start = 25.dp)
            ) {

                Column{
                    Text(text = "Marriage date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }

                Column{
                    Text(text = "Communion date:", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Normal, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 25.dp))

                    Text(text = "00/00/0000", fontFamily = FontFamily(Font(R.font.roboto)), fontWeight = FontWeight.Medium, fontSize = 13.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 7.dp))
                }
            }
        }

//split line
        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFDDDDDD))
        ) { }


    }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview(){
    AccountScreen2(navController = rememberNavController())
}
