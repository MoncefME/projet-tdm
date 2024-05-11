package com.example.projettdm.auth.data.remote.response
import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("token")
    val token: String,
    @SerializedName("message")
    val message : String
)