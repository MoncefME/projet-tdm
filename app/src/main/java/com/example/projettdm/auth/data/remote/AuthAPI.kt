package com.example.projettdm.auth.data.remote

import com.example.projettdm.auth.data.remote.request.LoginBody
import com.example.projettdm.auth.data.remote.request.SignupBody
import com.example.projettdm.auth.data.remote.response.LoginResponse
import com.example.projettdm.auth.data.remote.response.SignupResponse
import com.example.projettdm.auth.data.remote.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthAPI {
    @POST("/users/login/")
    suspend fun login(
        @Body loginBody: LoginBody
    ) : LoginResponse

    @POST("/users/signup/")
    suspend fun signup(
        @Body signupBody: SignupBody
    ) : SignupResponse

    @POST("users/me")
    suspend fun getUserInfo(
        @Header("Authorization") token:String,
    ): UserInfoResponse


    companion object {
        const val BASE_URL = "https://backend-bdm.onrender.com/"
    }
}