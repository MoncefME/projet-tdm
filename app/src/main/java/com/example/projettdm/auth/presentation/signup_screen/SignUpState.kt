package com.example.projettdm.auth.presentation.signup_screen

data class SignUpState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: String? = "",
    val isGoogleSignUp : Boolean = false
)