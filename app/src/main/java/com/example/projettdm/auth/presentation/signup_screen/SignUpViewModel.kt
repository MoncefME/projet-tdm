package com.example.projettdm.auth.presentation.signup_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projettdm.auth.presentation.login_screen.GoogleSignInState
import com.example.projettdm.auth.repository.AuthRepository
import com.example.projettdm.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _signUpState  = Channel<SignUpState>()
    val signUpState  = _signUpState.receiveAsFlow()


    fun registerUser(email: String, password:String,firstName:String,lastName:String,phone:String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.signup(email, password, firstName, lastName, phone).let { response ->
                if (response.isSuccessful) {
                    _signUpState.send(SignUpState(isSuccess = true))
                    print("registerUser: ${response.body()}")
                } else {
                    _signUpState.send(SignUpState(isError = response.message()))
                    print("registerUser: ${response.message()}")
                }
            }
        }
    }


    private val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState

    fun googleSignIn(credential: String) = viewModelScope.launch {
        print("googleSignIn: $credential")
    }



}