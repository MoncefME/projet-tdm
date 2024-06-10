package com.example.projettdm.auth.presentation.login_screen

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projettdm.R
import com.example.projettdm.common.navigation.Screens
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
                    viewModel.loginUser(email, password)
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
//            IconButton(onClick = {
//                // TODO Later: Implement Google Sign In
//                viewModel.loginWithGoogle(context);
//                //navController.navigate(Screens.ProfileScreen.route)
//            }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_google),
//                    contentDescription = "Google Icon",
//                    modifier = Modifier.size(50.dp),
//                    tint = Color.Unspecified
//                )
//            }
            TextButton(
                onClick = {

                    Log.e("Hi", "HEEEEEE")

                    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false) // Query all google accounts on the device
                        .setServerClientId("110532752994-vn1mpiftqgs2tbupn4em6dk6gc3oc7vr.apps.googleusercontent.com")
                        .build()

                    val request =
                        GetCredentialRequest.Builder().addCredentialOption(googleIdOption)
                            .build()
//
                    val credentialManager = CredentialManager.create(context)

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val result =
                                credentialManager.getCredential(context, request)
                            viewModel.handleSignIn(result)
//                            Log.e("MainActivity", "idToken: $idToken")

                        } catch (e: GetCredentialException) {
                            Log.e("MainActivity", "GetCredentialException", e)
                        }
                    }


                },

                ) {
                Text("Sign in")
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