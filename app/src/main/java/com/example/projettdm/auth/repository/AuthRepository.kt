package com.example.projettdm.auth.repository

import android.util.Log
import com.example.projettdm.auth.data.local.AuthPreferences
import com.example.projettdm.auth.data.remote.AuthAPI
import com.example.projettdm.auth.data.remote.request.LoginBody
import com.example.projettdm.auth.data.remote.request.SignupBody
import com.example.projettdm.common.utils.Resource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authAPI: AuthAPI,
    private val preferences: AuthPreferences
) {
    suspend fun login(email: String, password: String) : Resource<Unit> {
        return try {
            val response = authAPI.login(LoginBody(email, password))
            preferences.saveAuthToken(response.token)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }

    }
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

    suspend fun getUserAuthToken() : String {
        val token = preferences.getAuthToken() ?: ""
        Log.d("getUserAuthToken", token)
        return token
    }
}