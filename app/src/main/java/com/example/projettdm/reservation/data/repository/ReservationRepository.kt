package com.example.projettdm.reservation.data.repository

import com.example.projettdm.parking_list.data.remote.ParkingAPI
import com.example.projettdm.reservation.data.remote.ReservationAPI
import javax.inject.Inject

class ReservationRepository @Inject constructor
    (private val reservationAPI: ReservationAPI) {

        suspend fun addReservation() = reservationAPI.addReservation()
        suspend fun getReservationById(id: String) = reservationAPI.getReservationById(id)
        suspend fun getReservationsByUserId(userId: String) = reservationAPI.getReservationsByUserId(userId)

}