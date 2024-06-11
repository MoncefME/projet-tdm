package com.example.projettdm.auth.data.remote.request
import com.google.gson.annotations.SerializedName

data class LoginBody (
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("fcmToken")
    val fcmToken: String
)