package com.example.projettdm.auth.repository

import android.util.Log
import com.auth0.android.jwt.JWT

import com.example.projettdm.auth.data.local.AuthPreferences
import com.example.projettdm.auth.data.remote.AuthAPI
import com.example.projettdm.auth.data.remote.request.LoginBody
import com.example.projettdm.auth.data.remote.request.SignupBody
import com.example.projettdm.auth.data.remote.response.UserInfoResponse
import com.example.projettdm.common.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authAPI: AuthAPI,
    private val preferences: AuthPreferences
) {
    suspend fun login(email: String, password: String) : Resource<Unit> {
        return try {
            val response = authAPI.login(LoginBody(email, password))
            if(response.message === "Auth failed"){
                return Resource.Error("Auth failed")
            }else {
                response.token?.let { preferences.saveAuthToken(it) }
                Resource.Success(Unit)
            }
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
    suspend fun logout() {
        preferences.clearAuthToken()
    }

    suspend fun getUserInfo(): UserInfoResponse {
        return withContext(Dispatchers.IO) {
            val token = preferences.getAuthToken() ?: ""
            val user = authAPI.getUserInfo("Bearer $token")
            preferences.saveUserInfos(user)
            user
        }
    }


}