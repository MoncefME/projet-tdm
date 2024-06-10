package com.example.projettdm.auth.presentation.signup_screen

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.credentials.Credential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.projettdm.auth.presentation.login_screen.SignInState
import com.example.projettdm.auth.presentation.login_screen.UserInfo
import com.example.projettdm.auth.repository.AuthRepository
import com.example.projettdm.common.utils.Resource
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


import kotlinx.coroutines.launch


import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow



@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _signUpState  = Channel<SignUpState>()
    val signUpState  = _signUpState.receiveAsFlow()


    fun registerUser(
        email: String,
        password:String,
        firstName:String,
        lastName:String,
        phone:String
    ) = viewModelScope.launch {
        repository.signup(email, password, firstName, lastName, phone).let { response ->
            _signUpState.send(SignUpState(isSuccess = true))
            Log.d("registerUser", response.toString())
        }
    }

    @RequiresApi(34)
    suspend fun handleSignIn(result: androidx.credentials.GetCredentialResponse) {
        when (val credential = result.credential) {
            is Credential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val userInfo = decodeJwtToken(googleIdTokenCredential)
                        // Use userInfo as needed
                        Log.d("MainActivity", "User Info: $userInfo")

                        repository.googleAuth(userInfo).let { response ->
                            when(response){
                                is Resource.Success -> {
                                    _signUpState.send(SignUpState(isSuccess = true, isGoogleSignUp = true))
                                }
                                is Resource.Error -> {
                                    _signUpState.send(SignUpState(isError = response.message ?: "An error occurred"))
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
}





