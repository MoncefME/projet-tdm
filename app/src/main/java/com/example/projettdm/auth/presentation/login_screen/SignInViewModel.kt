package com.example.projettdm.auth.presentation.login_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projettdm.auth.repository.AuthRepository
import com.example.projettdm.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _signInState = Channel<SignInState>()
    val signInState: Flow<SignInState> = _signInState.receiveAsFlow()

    private val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState

//    fun googleSignIn(idToken: String) = viewModelScope.launch {
//        println("googleSignIn: $idToken")
//    }


    fun loginUser(email: String, password: String) = viewModelScope.launch {
            repository.login(email, password).let { response ->
                _signInState.send(SignInState(isSuccess = true))
                Log.d("loginUser", response.toString())
            }

    }
}