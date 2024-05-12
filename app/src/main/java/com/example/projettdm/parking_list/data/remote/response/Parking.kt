package com.example.projettdm.parking_list.data.remote.response

data class Parking(
    val id: Int,
    val name: String,
    val price: Float,
    val capacity: Int,
    val available: Int,
    val reserved: Int,
    val image: String,
    val city: String,
    val latitude: Double,
    val longitude: Double,
    val description: String
//    val setup: String,
//    val punchline: String,
)