package com.example.projettdm.reservation.data.remote

import com.example.projettdm.parking_list.data.remote.response.Parking
import com.example.projettdm.reservation.data.model.Reservation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservationAPI {

    @POST("/reservations")
    suspend fun addReservation(): Response<Reservation>
    @GET("/reservations/{id}")
    suspend fun getReservationById(@Path("id") id:String): Response<Reservation>

    @GET("/reservations/{userId}")

    suspend fun getReservationsByUserId(@Path("userId") userId:String): Response<List<Reservation>>



    companion object {
        const val BASE_URL = "https://backend-bdm.onrender.com"
    }
}
