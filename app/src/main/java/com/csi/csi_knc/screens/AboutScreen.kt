package com.csi.csi_knc.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.csi.csi_knc.R

@Composable
fun AboutScreen(navController: NavController){


    Surface (
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFFFFF)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, start = 20.dp)
                .background(Color(0xFFFFFFFF)).verticalScroll(rememberScrollState())
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,

            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(Modifier.width(15.dp))
                Text("சபையை பற்றி :", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.roboto )))
            }
            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                verticalAlignment = Alignment.Top,

            ) {
                Icon(
                    painter = painterResource(R.drawable.locationasset),
                    contentDescription = "Location",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                Text( text = "Konganthanparai, Tamil Nadu,\nIndia - 627007",
                    modifier = Modifier.padding(horizontal = 15.dp),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight.Normal,
                    lineHeight = 24.sp,
                )
            }
            Spacer(Modifier.height(25.dp))

            Text( text = "Head of Pastorate:",
                modifier = Modifier.padding(horizontal = 15.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.SemiBold,
                lineHeight = 24.sp,
            )

            Text( text = "Rev.B. ஜெபராஜ் ஞானஸ்வாமி.",
                modifier = Modifier.padding(horizontal = 15.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
            )
            Spacer(Modifier.height(25.dp))

            //Catechist
            Text( text = "Catechist of Pastorate:",
                modifier = Modifier.padding(horizontal = 15.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.SemiBold,
                lineHeight = 24.sp,
            )

            Text( text = "J. பென்னி ஜோசப்.",
                modifier = Modifier.padding(horizontal = 15.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
            )
            Spacer(Modifier.height(25.dp))

            //DCs
            Text( text = "DCs of Pastorate:",
                modifier = Modifier.padding(horizontal = 15.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.SemiBold,
                lineHeight = 24.sp,
            )

            Text( text = "A. எபினேசர் கோயில்பிள்ளை.\nW. வில்சன் துரை.",
                modifier = Modifier.padding(horizontal = 15.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
            )
            Spacer(Modifier.height(25.dp))


//Treasurer
            Text( text = "Treasurer of Pastorate:",
                modifier = Modifier.padding(horizontal = 15.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.SemiBold,
                lineHeight = 24.sp,
            )

            Text( text = "R. ரிச்சர்ட் ஜேம்ஸ் பீட்டர்.",
                modifier = Modifier.padding(horizontal = 15.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
            )
            Spacer(Modifier.height(25.dp))

            //Secretory
            Text( text = "Secretory of Pastorate:",
                modifier = Modifier.padding(horizontal = 15.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.SemiBold,
                lineHeight = 24.sp,
            )

            Text( text = "T. பாக்கியராஜ்.",
                modifier = Modifier.padding(horizontal = 15.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
            )
            Spacer(Modifier.height(25.dp))

//Official members
            Text( text = "Official Members of Pastorate:",
                modifier = Modifier.padding(horizontal = 15.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.SemiBold,
                lineHeight = 24.sp,
            )

            Text( text = "S. ஜெபராஜ்.\nD. யோவான்.\nD. முத்துக்குமார்." +
                    "\nN. கோயில்ராஜ்.\nD. அந்தோணிராஜ்.\nY. ஜெயக்குமார்.\nR. நிக்சன்.\nR. ஏசுபாதம்.\nC. வினில்",
                modifier = Modifier.padding(horizontal = 15.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
            )

            Spacer(Modifier.height(50.dp))

            val context = LocalContext.current
            val email = "jgeneration2024@gmail.com"
//Powered by

                Text( text = "Powered by,",
                    modifier = Modifier.padding(horizontal = 15.dp).alpha(0.4f),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp,
                )

                Text( text = "J Generation",
                    modifier = Modifier.padding(horizontal = 15.dp).alpha(0.4f),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 24.sp,
                )

            Text( text = "Contact: $email",
                modifier = Modifier.padding(horizontal = 15.dp)
                    .alpha(0.4f)
                    .clickable{
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:$email")
                        }
                        context.startActivity(intent)
                    },
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.SemiBold,
                lineHeight = 24.sp,
                textDecoration = TextDecoration.Underline
            )

            Spacer(Modifier.height(40.dp))

        }


    }
}

@Preview(showBackground = true)
@Composable
fun aboutpreview(){
    AboutScreen(navController = rememberNavController())
}