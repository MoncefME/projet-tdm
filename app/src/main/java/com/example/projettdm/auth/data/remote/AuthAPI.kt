package com.example.projettdm.auth.data.remote

import com.example.projettdm.auth.data.remote.request.LoginBody
import com.example.projettdm.auth.data.remote.request.SignupBody
import com.example.projettdm.auth.data.remote.response.LoginResponse
import com.example.projettdm.auth.data.remote.response.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
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


    companion object {
        const val BASE_URL = "https://backend-bdm.onrender.com/"
    }
}