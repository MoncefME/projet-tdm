package com.example.projettdm.reservation.data.remote

import com.example.projettdm.parking_list.data.remote.response.Parking
import com.example.projettdm.reservation.data.model.Reservation
import dagger.Provides
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface ReservationAPI {

    @POST("/reservations")

    suspend fun addReservation(
        @Header("Authorization") token:String,
        @Body reservation:Reservation): Response<Reservation>
    @GET("/reservations/{id}")
    suspend fun getReservationById(@Path("id") id:String): Response<Reservation>

    @GET("/reservations/{userId}")
    suspend fun getReservationsByUserId(@Path("userId") userId:String): Response<List<Reservation>>




    companion object {

        const val BASE_URL = "https://backend-bdm.onrender.com"
          //const val BASE_URL = "https://ac88-129-45-114-200.ngrok-free.app "

    }
}
