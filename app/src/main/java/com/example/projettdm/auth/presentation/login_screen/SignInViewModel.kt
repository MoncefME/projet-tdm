
package com.example.projettdm.auth.presentation.login_screen



import androidx.credentials.Credential

import kotlinx.coroutines.launch
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.projettdm.auth.repository.AuthRepository
import com.example.projettdm.common.utils.Resource
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
data class UserInfo(val email: String?, val firstName: String?,val lastName :String?)
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _signInState = Channel<SignInState>()
    val signInState: Flow<SignInState> = _signInState.receiveAsFlow()


    fun loginUser(email: String, password: String, fcmToken: String) = viewModelScope.launch {
            repository.login(email, password, fcmToken).let { response ->
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
                                    _signInState.send(SignInState(isSuccess = true))
                                }
                                is Resource.Error -> {
                                    _signInState.send(SignInState(isError = response.message ?: "An error occurred"))
                                }

                                is Resource.Loading -> TODO()
                            }

                        }

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