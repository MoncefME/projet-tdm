package com.example.projettdm.auth.presentation.login_screen

data class SignInState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)