package com.example.projettdm.reservation.data.repository

import com.example.projettdm.parking_list.data.remote.ParkingAPI
import com.example.projettdm.reservation.data.model.Reservation
import com.example.projettdm.reservation.data.remote.ReservationAPI
import dagger.Provides
import javax.inject.Inject

class ReservationRepository @Inject constructor
    (private val reservationAPI: ReservationAPI) {


        suspend fun addReservation(token: String, reservation: Reservation ) = reservationAPI.addReservation(token, reservation)
        suspend fun getReservationById(id: String) = reservationAPI.getReservationById(id)
        suspend fun getReservationsByUserId(userId: String) = reservationAPI.getReservationsByUserId(userId)

}