package com.example.projettdm.auth.data.remote.request
import com.google.gson.annotations.SerializedName

data class GoogleLoginBody (
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName : String,

    )