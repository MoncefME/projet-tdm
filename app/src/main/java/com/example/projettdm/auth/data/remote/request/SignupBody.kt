package com.example.projettdm.auth.data.remote.request

data class SignupBody (
    val email: String,
    val password: String,
    val firstName : String,
    val lastName : String,
    val phone : String,
)