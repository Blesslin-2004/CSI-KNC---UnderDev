package com.kpnorth.knc_app_csi.screens

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kpnorth.knc_app_csi.R
import com.kpnorth.knc_app_csi.Routes
import com.kpnorth.knc_app_csi.ui.theme.MyAppTheme
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

@Composable
fun Login1Screen(navController: NavController) {
    var showTextField by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }
    var showOtpField by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    val firestore = if (!isPreview) FirebaseFirestore.getInstance() else null

    val auth = FirebaseAuth.getInstance()
    var verificationId by remember { mutableStateOf<String?>(null) }

    // OTP states
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val otpValues = remember { mutableStateListOf("", "", "", "", "", "") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val logo = painterResource(id = R.drawable.knclogo)

        Image(
            painter = logo,
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(top = 20.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (!showTextField && !showOtpField) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 30.dp)
            ) {

                Text(
                    "Welcome to\nCSI Konganthanparai North Church",
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(lineHeight = 20.sp)
                )
                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = { showTextField = true },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp).padding(horizontal = 20.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
                ) {
                    Text("CONTINUE AS CHURCH MEMBER", fontFamily = FontFamily(Font(R.font.roboto)))
                }

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {
                        auth.signInAnonymously()
                            .addOnCompleteListener { task ->
                                if(task.isSuccessful){
                                    navController.navigate(Routes.Home.route){
                                        popUpTo(Routes.Login1.route){ inclusive = true}
                                    }
                                }else{
                                    Toast.makeText(context, "Guest login failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp).padding(horizontal = 20.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
                ) {
                    Text("CONTINUE AS GUEST", fontFamily = FontFamily(Font(R.font.roboto)))
                }

                val annotatedText = buildAnnotatedString {
                    append("Read our ")

                    // 🔗 Privacy Policy link
                    pushStringAnnotation(
                        tag = "PRIVACY",
                        annotation = "https://partime-j-generation.blogspot.com/2025/10/privacy-policy-for-knc-konganthanpari.html"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF1E88E5),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("privacy policy")
                    }
                    pop()

                    append(" and ")

                    // 🔗 Terms and Conditions link
                    pushStringAnnotation(
                        tag = "TERMS",
                        annotation = "https://partime-j-generation.blogspot.com/2025/10/terms-conditions-for-knc.html"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF1E88E5),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("terms and conditions")
                    }
                    pop()
                }

                val uriHandler = LocalUriHandler.current

                ClickableText(
                    modifier = Modifier.padding(top = 10.dp),
                    text = annotatedText,
                    style = MaterialTheme.typography.bodyMedium,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(start = offset, end = offset)
                            .firstOrNull()?.let { annotation ->
                                uriHandler.openUri(annotation.item)
                            }
                    }
                )
            }
        }

        if (showTextField && !showOtpField) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Church Member Login", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.roboto)))
                Spacer(Modifier.height(10.dp))
                Text(
                    "Enter your family number. We will send a code to\ncorresponding mobile number",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    style = TextStyle(lineHeight = 20.sp)
                )
                Spacer(Modifier.height(20.dp))
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Family no./குடும்ப எண்.") },
                    modifier = Modifier
                        .width(360.dp)
                        .height(56.dp).background(Color(0xFFFFFFFF)),
                    shape = RoundedCornerShape(5.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {
                        if (inputText.isNotEmpty()) {
                                showOtpField = true
                                firestore?.collection("church_members")?.document(inputText)?.get()
                                    ?.addOnSuccessListener { document ->

                                        val mobileNo = document.getString("mobile")
                                        if (!mobileNo.isNullOrBlank()) {
                                            val phoneNumber = "+91$mobileNo" // ensure country code
                                            val options = PhoneAuthOptions.newBuilder(auth)
                                                .setPhoneNumber(phoneNumber)
                                                .setTimeout(60L, TimeUnit.SECONDS)
                                                .setActivity(context as Activity)
                                                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                                        // Instant verification (optional)
                                                        auth.signInWithCredential(credential)
                                                            .addOnCompleteListener { task ->
                                                                if (task.isSuccessful) {
                                                                    Toast.makeText(context, "Auto Sign-in Success", Toast.LENGTH_SHORT).show()
                                                                }
                                                            }


                                                    }

                                                    override fun onVerificationFailed(e: FirebaseException) {
                                                        Toast.makeText(context, "Verification failed: ${e.message}", Toast.LENGTH_LONG).show()
                                                        Log.e("OTP", "Verification Failed", e)
                                                    }

                                                    override fun onCodeSent(verificationId_: String, token: PhoneAuthProvider.ForceResendingToken) {
                                                        verificationId = verificationId_
                                                        Toast.makeText(context, "OTP Sent to $mobileNo", Toast.LENGTH_SHORT).show()
                                                    }
                                                })
                                                .build()

                                            PhoneAuthProvider.verifyPhoneNumber(options)
                                        }
                                    }?.addOnFailureListener {
                                        Toast.makeText(context, "Family number not available",
                                            Toast.LENGTH_SHORT).show()
                                    }


                        }

                    },
                    modifier = Modifier
                        .width(360.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("LOGIN", fontFamily = FontFamily(Font(R.font.roboto)))
                }
            }
        }

        if (showOtpField) {
            Spacer(Modifier.height(12.dp))
            Text("Verification Code", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.roboto)))
            Spacer(Modifier.height(8.dp))
            Text(
                "We have sent the verification code\nto your mobile number",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.roboto)),
                lineHeight = 15.sp
            )
            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                (0..5).forEach { index ->
                    OutlinedTextField(
                        value = otpValues[index],
                        onValueChange = { newChar ->
                            if (newChar.length <= 1 && newChar.all { it.isDigit() }) {
                                otpValues[index] = newChar
                                if (newChar.isNotEmpty() && index < 5) {
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
                            .width(48.dp)
                            .focusRequester(focusRequesters[index]).background(Color(0xFFFFFFFF))
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

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val otp = otpValues.joinToString("")
                    if (otp.length == 6) {
                        Log.d("OTP", "Entered OTP: $otp")
                        val otp = otpValues.joinToString("")
                        if (otp.length == 6 && verificationId != null) {
                            val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
                            auth.signInWithCredential(credential)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(context, "Phone Verified!", Toast.LENGTH_SHORT).show()
                                        Log.d("OTP", "Sign in success: ${auth.currentUser?.uid}")
                                        //family number sending to account screen
                                        val prefs = context.getSharedPreferences("Accountnumber", Context.MODE_PRIVATE)
                                        prefs.edit().putString("familynumber", inputText).apply()

                                        navController.navigate("home") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    } else {
                                        Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }

                    } else {
                        Toast.makeText(context, "Please enter a valid 6-digit OTP", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text("Confirm", fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.roboto)))
            }

            LaunchedEffect(Unit) {
                focusRequesters[0].requestFocus()
            }
        }
    }
}

@Composable
fun Login1Screen1(navController: NavController) {
    var showTextField by remember { mutableStateOf(false) }
    var showOtpField by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val logo = painterResource(id = R.drawable.knclogo)

        Image(
            painter = logo,
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(top = 20.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (!showTextField && !showOtpField) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 30.dp)
            ) {

                Text(
                    "Welcome to\nCSI Konganthanparai North Church",
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(lineHeight = 20.sp)
                )
                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = { showTextField = true },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp).padding(horizontal = 20.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
                ) {
                    Text("CONTINUE AS CHURCH MEMBER", fontFamily = FontFamily(Font(R.font.roboto)))
                }

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {},
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp).padding(horizontal = 20.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
                ) {
                    Text("CONTINUE AS GUEST", fontFamily = FontFamily(Font(R.font.roboto)))
                }

                val annotatedText = buildAnnotatedString {
                    append("Read our ")

                    // 🔗 Privacy Policy link
                    pushStringAnnotation(
                        tag = "PRIVACY",
                        annotation = "https://partime-j-generation.blogspot.com/2025/10/privacy-policy-for-knc-konganthanpari.html"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF1E88E5),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("privacy policy")
                    }
                    pop()

                    append(" and ")

                    // 🔗 Terms and Conditions link
                    pushStringAnnotation(
                        tag = "TERMS",
                        annotation = "https://partime-j-generation.blogspot.com/2025/10/terms-conditions-for-knc.html"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF1E88E5),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("terms and conditions")
                    }
                    pop()
                }

                val uriHandler = LocalUriHandler.current

                ClickableText(
                    modifier = Modifier.padding(top = 10.dp),
                    text = annotatedText,
                    style = MaterialTheme.typography.bodyMedium,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(start = offset, end = offset)
                            .firstOrNull()?.let { annotation ->
                                uriHandler.openUri(annotation.item)
                            }
                    }
                )
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun Loginpreview() {
    MyAppTheme {
        val navController = rememberNavController()
        Login1Screen1(navController)
    }
}
