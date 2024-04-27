package com.example.projettdm.auth.presentation.login_screen

data class GoogleSignInState(
    val success: Boolean? = null,
    val loading: Boolean = false,
    val error: String = ""
)