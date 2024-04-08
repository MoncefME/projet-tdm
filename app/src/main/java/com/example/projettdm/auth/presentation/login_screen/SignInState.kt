package com.example.projettdm.auth.presentation.login_screen

data class SignInState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: String? = ""
)