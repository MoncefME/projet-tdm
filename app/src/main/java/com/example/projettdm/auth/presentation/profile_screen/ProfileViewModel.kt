package com.example.projettdm.auth.presentation.profile_screen

import androidx.lifecycle.ViewModel
import com.example.projettdm.auth.data.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> = _user

    init {
        fetchUser()
    }

    private fun fetchUser() {
        _user.value = authRepository.getCurrentUser()
    }

    fun logout() {
        authRepository.logout()
        _user.value = null
    }
}