package com.example.projettdm.auth.presentation.login_screen



import androidx.credentials.Credential

import kotlinx.coroutines.launch
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.projettdm.auth.repository.AuthRepository
import com.example.projettdm.common.utils.Resource
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
data class UserInfo(val email: String?, val firstName: String?,val lastName :String?)
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _signInState = Channel<SignInState>()
    val signInState: Flow<SignInState> = _signInState.receiveAsFlow()

//    private val _googleState = mutableStateOf(GoogleSignInState())
//    val googleState: State<GoogleSignInState> = _googleState

//    fun googleSignIn(idToken: String) = viewModelScope.launch {
//        println("googleSignIn: $idToken")
//    }


    fun loginUser(email: String, password: String) = viewModelScope.launch {
            repository.login(email, password).let { response ->
                when(response){
                    is Resource.Success -> {
                        _signInState.send(SignInState(isSuccess = true))
                    }
                    is Resource.Error -> {
                        _signInState.send(SignInState(isError = response.message ?: "An error occurred"))
                    }

                    is Resource.Loading -> TODO()
                }

            }

    }

    @RequiresApi(34)
    suspend fun handleSignIn(result: androidx.credentials.GetCredentialResponse) {
        // Handle the successfully returned credential.
        when (val credential = result.credential) {
            is Credential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val userInfo = decodeJwtToken(googleIdTokenCredential)
                        // Use userInfo as needed
                        Log.d("MainActivity", "User Info: $userInfo")

                        repository.googleAuth(userInfo).let { response ->
                            when(response){
                                is Resource.Success -> {
                                    _signInState.send(SignInState(isSuccess = true))
                                }
                                is Resource.Error -> {
                                    _signInState.send(SignInState(isError = response.message ?: "An error occurred"))
                                }

                                is Resource.Loading -> TODO()
                            }

                        }

                        // TODO: Send [googleIdTokenCredential.idToken] to your
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("MainActivity", "handleSignIn:", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e("MainActivity", "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("MainActivity", "Unexpected type of credential")
            }
        }
    }
    fun decodeJwtToken(googleIdTokenCredential: GoogleIdTokenCredential): UserInfo {
        val idToken = googleIdTokenCredential.idToken
        return if (idToken != null) {
            try {
                val jwt = JWT(idToken)
                val email = jwt.getClaim("email").asString()
                val firstName = jwt.getClaim("given_name").asString()
                val lastName = jwt.getClaim("family_name").asString()
                UserInfo(email, firstName,lastName)
            } catch (e: Exception) {
                Log.e("JWTDecode", "JWT Decode error", e)
                UserInfo(null, null,null)
            }
        } else {
            Log.e("JWTDecode", "ID token is null")
            UserInfo(null, null,null)
        }
    }
//
//    private fun handleSignIn(result: GetCredentialResponse) {
//        // Handle the successfully returned credential.
//        when (val credential = result.credential) {
//            is CustomCredential -> {
//                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
//                    try {
//                        // Use googleIdTokenCredential and extract id to validate and
//                        // authenticate on your server.
//                        val googleIdTokenCredential =
//                            GoogleIdTokenCredential.createFrom(credential.data)
//
//                        // TODO: Send [googleIdTokenCredential.idToken] to your backend
//                    } catch (e: GoogleIdTokenParsingException) {
//                        Log.e("MainActivity", "handleSignIn:", e)
//                    }
//                } else {
//                    // Catch any unrecognized custom credential type here.
//                    Log.e("MainActivity", "Unexpected type of credential")
//                }
//            }
//
//            else -> {
//                // Catch any unrecognized credential type here.
//                Log.e("MainActivity", "Unexpected type of credential")
//            }
//        }
//    }
//    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
//    fun loginWithGoogle(context:Context) {
//        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
//            .setFilterByAuthorizedAccounts(false) // Query all google accounts on the device
//            .setServerClientId("110532752994-vn1mpiftqgs2tbupn4em6dk6gc3oc7vr.apps.googleusercontent.com")
//            .build()
//
//        val request = GetCredentialRequest.Builder()
//            .addCredentialOption(googleIdOption)
//            .build()
//
//        val credentialManager = CredentialManager.create(context)
//
//        viewModelScope.launch {
//            try {
//                val result = credentialManager.getCredential(context, request)
//                handleSignIn(result)
//            } catch (e: GetCredentialException) {
//                Log.e("MainActivity", "GetCredentialException", e)
//            }
//        }
//    }

}