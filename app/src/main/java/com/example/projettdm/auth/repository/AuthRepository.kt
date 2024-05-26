package com.example.projettdm.auth.repository

import android.util.Log
import com.auth0.android.jwt.JWT
import com.example.projettdm.auth.data.local.AuthPreferences
import com.example.projettdm.auth.data.remote.AuthAPI
import com.example.projettdm.auth.data.remote.request.LoginBody
import com.example.projettdm.auth.data.remote.request.SignupBody
import com.example.projettdm.common.utils.Resource
import kotlinx.coroutines.Dispatchers
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

    suspend fun getUserAuthToken() : String {
        val token = preferences.getAuthToken() ?: ""
        Log.d("getUserAuthToken", token)
        return token
    }

    data class UserInfo(val userId: Int, val email: String)


    suspend fun getUserInfo(): Resource<UserInfo> {
        return withContext(Dispatchers.IO) {
            try {
                val currentToken = preferences.getAuthToken()
                if (currentToken.isNullOrBlank()) {
                    return@withContext Resource.Error<UserInfo>("Token is null or blank")
                }

                val jwt = JWT(currentToken)
                val userId = jwt.getClaim("userId").asInt()
                val userEmail = jwt.getClaim("email").asString()

                if (userId == null) {
                    return@withContext Resource.Error<UserInfo>("Invalid or missing userId claim")
                }

                if (userEmail.isNullOrBlank()) {
                    return@withContext Resource.Error<UserInfo>("Invalid or missing email claim")
                }

                Resource.Success(UserInfo(userId, userEmail))
            } catch (e: Exception) {
                Resource.Error<UserInfo>(e.message ?: "An error occurred")
            }
        }
    }
}