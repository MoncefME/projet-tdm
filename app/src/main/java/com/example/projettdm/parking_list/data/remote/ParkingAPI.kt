package com.example.projettdm.parking_list.data.remote

import com.example.projettdm.parking_list.data.remote.response.Parking
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ParkingAPI {
   @GET("/parkings")
   //@GET("/jokes/ten")

   suspend fun getParkingList(): Response<List<Parking>>

    @GET("/parkings/{id}")
    suspend fun getParkingById(@Path("id") id:Int): Response<Parking>

    companion object {
        const val BASE_URL = "https://ff1f-41-220-149-190.ngrok-free.app"

     //   const val BASE_URL = "http://10.0.2.2:3000/"
    }
}
