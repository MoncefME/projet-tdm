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

    private val _user = MutableStateFlow<AuthRepository.UserInfo?>(null)
    val user: StateFlow<AuthRepository.UserInfo?> = _user
//
    init {
        viewModelScope.launch {
            fetchUser()
        }
    }
//
    private suspend fun fetchUser() {
        val user = authRepository.getUserInfo()
        _user.value = user.data
    }

     fun logout() {
         viewModelScope.launch {
             authRepository.logout()
         }
     }
}


