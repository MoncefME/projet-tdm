package com.example.projettdm.auth.presentation.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projettdm.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _user = MutableStateFlow<Any?>(null)
    val user: StateFlow<Any?> = _user
//
    init {
        viewModelScope.launch {
            fetchUser()
        }
    }
//
    private suspend fun fetchUser() {
        // get the user from the prefrecens
        val user = authRepository.getUserAuthToken()
        _user.value = user
    }

     fun logout() {
        authRepository.logout()

        _user.value = null
    }
}


