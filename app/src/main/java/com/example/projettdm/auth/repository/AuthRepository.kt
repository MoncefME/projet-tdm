package com.example.projettdm.auth.repository

import com.example.projettdm.auth.data.remote.AuthAPI
import com.example.projettdm.auth.data.remote.request.LoginBody
import com.example.projettdm.auth.data.remote.request.SignupBody
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authAPI: AuthAPI
) {
    suspend fun login(email: String, password: String) = authAPI.login(LoginBody(email, password))
    suspend fun signup(
        email: String,
        password: String,
        firstName:String,
        lastName:String,
        phone : String,
    ) = authAPI.signup(SignupBody(email, password, firstName, lastName, phone))
    fun logout() {
        // logout
    }
}