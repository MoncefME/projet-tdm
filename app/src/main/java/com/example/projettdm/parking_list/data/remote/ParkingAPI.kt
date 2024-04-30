package com.example.projettdm.parking_list.data.remote

import com.example.projettdm.parking_list.data.remote.response.Parking
import retrofit2.Response
import retrofit2.http.GET

interface ParkingAPI {
    @GET("/jokes/ten")
    suspend fun getParkingList(): Response<List<Parking>>

    companion object {
        const val BASE_URL = "https://official-joke-api.appspot.com"
    }
}

