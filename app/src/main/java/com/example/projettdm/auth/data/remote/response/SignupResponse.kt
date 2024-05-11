package com.example.projettdm.auth.data.remote.response
import com.google.gson.annotations.SerializedName
data class SignupResponse (
    @SerializedName("message")
    val message: String
)