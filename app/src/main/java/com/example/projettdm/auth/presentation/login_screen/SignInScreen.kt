package com.example.projettdm.auth.presentation.login_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projettdm.R
import com.example.projettdm.common.navigation.Screens
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel
) {

//    val googleSignInState = viewModel.googleState.value
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signInState.collectAsState(initial = null)
    var fcmToken by remember { mutableStateOf("") }


    // Fetch the FCM token
    LaunchedEffect(Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("fetching", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            // Get new FCM registration token
            fcmToken = task.result
            Log.d("FCMToken", "FCM Token: $fcmToken")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = "Sign In",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
        )
        Text(
            text = "Enter your credential's to register",
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp), singleLine = true, placeholder = {
                Text(text = "Email")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp), singleLine = true, placeholder = {
                Text(text = "Password")
            }
        )

        Button(
            onClick = {
                scope.launch {
                    viewModel.loginUser(email, password, fcmToken)
                    if (state.value?.isSuccess == true) {
                        navController.navigate(Screens.ProfileScreen.route)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(text = "Sign In", color = Color.White, modifier = Modifier.padding(7.dp))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (state.value?.isLoading == true) {
                CircularProgressIndicator()
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "New User? Sign Up ",

            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.clickable {
                navController.navigate(Screens.SignUpScreen.route)
            },
        )
        Text(text = "or connect with",
            modifier = Modifier
                .padding(
                    top = 40.dp,
                ),
            fontWeight = FontWeight.Medium,
            color = Color.Gray)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {
                // TODO Later: Implement Google Sign In
//                scope.launch {
//                    viewModel.googleSignIn("google")
//                }
                navController.navigate(Screens.ProfileScreen.route)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(50.dp),
                    tint = Color.Unspecified
                )
            }

            // LaunchedEffect block to observe changes in the error state
            LaunchedEffect(key1 = state.value?.isError) {
                scope.launch {
                    if (state.value?.isError?.isNotEmpty() == true) {
                        val error = state.value?.isError
                        Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                    }
                }
            }

            // LaunchedEffect block to observe changes in the Google sign-in success state
//            LaunchedEffect(key1 = googleSignInState.success) {
//                scope.launch {
//                    if (googleSignInState.success != null) {
//                        Toast.makeText(context, "Sign In Success", Toast.LENGTH_LONG).show()
//                        navController.navigate(Screens.ProfileScreen.route)
//                    }
//                }
//            }

            // LaunchedEffect block to observe changes in the sign-in success state
            LaunchedEffect(key1 = state.value?.isSuccess) {
                scope.launch {
                    if(state.value?.isSuccess == true){
                        val success = state.value?.isSuccess
                        Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
                        navController.navigate(Screens.ProfileScreen.route)
                    }
                }
            }

        }
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//            if (googleSignInState.loading){
//                CircularProgressIndicator()
//            }
//        }
    }
}