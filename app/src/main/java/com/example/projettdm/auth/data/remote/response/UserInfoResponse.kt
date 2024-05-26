package com.example.projettdm.auth.data.remote.response
import com.google.gson.annotations.SerializedName

data class UserInfoResponse (
    @SerializedName("_id")
    val id : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("firstName")
    val firstName : String,
    @SerializedName("lastName")
    val lastName : String,
    @SerializedName("phone")
    val phone : String,
    @SerializedName("image")
    val image : String

)