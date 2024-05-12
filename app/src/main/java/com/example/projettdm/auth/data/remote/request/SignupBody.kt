package com.example.projettdm.auth.data.remote.request
import com.google.gson.annotations.SerializedName


data class SignupBody (
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("firstName")
    val firstName : String,
    @SerializedName("lastName")
    val lastName : String,
    @SerializedName("phone")
    val phone : String,
)