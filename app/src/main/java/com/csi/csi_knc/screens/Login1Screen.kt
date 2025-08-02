package com.csi.csi_knc.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.csi.csi_knc.R
import com.csi.csi_knc.ui.theme.CSIKNCTheme

@Composable
fun Login1Screen() {
    var showTextField by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }
    var showOtpField by remember { mutableStateOf(true) }
    val context = LocalContext.current


    // OTP states
    val focusRequesters = remember { List(4) { FocusRequester() } }
    val otpValues = remember { mutableStateListOf("", "", "", "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val logo = painterResource(id = R.drawable.knclogo)

        //logo
        Image(
            painter = logo,
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(top = 20.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (!showTextField && !showOtpField) {

            //Church member login
            Button(
                onClick = { showTextField = true },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .width(380.dp)
                    .height(50.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
            ) {
                Text("CHURCH MEMBER LOGIN", fontFamily = FontFamily(Font(R.font.roboto)))
            }

            Spacer(modifier = Modifier.height(15.dp))

            //Guest login
            Button(
                onClick = { showTextField = true },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .width(380.dp)
                    .height(50.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
            ) {
                Text("GUEST LOGIN", fontFamily = FontFamily(Font(R.font.roboto)))
            }
        }

        //Family no. entering page
        if (showTextField && !showOtpField) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Family no./குடும்ப எண்.") },
                    modifier = Modifier
                        .width(360.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(5.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {
                        if (inputText.isNotEmpty()) {
                            showOtpField = true
                        }
                    },
                    modifier = Modifier
                        .width(360.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("LOGIN" ,fontFamily = FontFamily(Font(R.font.roboto)))
                }
            }
        }

        //OTP sending page
        if (showOtpField) {
            Spacer(Modifier.height(30.dp))
            Text("Verification Code", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.roboto)))
            Spacer(Modifier.height(8.dp))
            Text(
                "We have sent the verification code\nto your mobile number",
                fontSize = 14.sp ,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.roboto))
            )
            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                otpValues.forEachIndexed { index, value ->
                    OutlinedTextField(
                        value = value,
                        onValueChange = { newChar ->
                            if (newChar.length <= 1 && newChar.all { it.isDigit() }) {
                                otpValues[index] = newChar
                                if (newChar.isNotEmpty() && index < 3) {
                                    focusRequesters[index + 1].requestFocus()
                                }
                            }
                        },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        modifier = Modifier
                            .width(60.dp)
                            .focusRequester(focusRequesters[index])
                            .onKeyEvent {
                                if (it.key == Key.Backspace && otpValues[index].isEmpty() && index > 0) {
                                    otpValues[index - 1] = ""
                                    focusRequesters[index - 1].requestFocus()
                                    true
                                } else false
                            },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    val otp = otpValues.joinToString("")
                    if (otp.length == 4) {
                        Log.d("OTP", "Entered OTP: $otp")
                        Toast.makeText(context, "$otp", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(context, "Invalid OTP", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text("Confirm", fontSize = 18.sp, fontFamily = FontFamily())
            }

            // Auto-focus first box
            LaunchedEffect(Unit) {
                focusRequesters[0].requestFocus()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun loginpreview() {
    CSIKNCTheme {
        Login1Screen()
    }
}
