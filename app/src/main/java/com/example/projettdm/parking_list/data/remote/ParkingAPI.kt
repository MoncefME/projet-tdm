package com.example.projettdm.parking_list.data.remote

import com.example.projettdm.parking_list.data.remote.response.Parking
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ParkingAPI {
    @GET("/parkings")
    suspend fun getParkingList(): Response<List<Parking>>

    @GET("/parkings/{id}")
    suspend fun getParkingById(@Path("id") id:Int): Response<Parking>

    companion object {
        const val BASE_URL = "http://192.168.43.51:3000/"
    }
}

