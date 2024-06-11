package com.example.projettdm.reservation.data.repository

import com.example.projettdm.auth.data.local.AuthPreferences
import com.example.projettdm.reservation.data.local.ReservationDao
import com.example.projettdm.reservation.data.local.ReservationDataBase
import com.example.projettdm.reservation.data.model.Reservation
import com.example.projettdm.reservation.data.remote.ReservationAPI
import javax.inject.Inject


class ReservationRepository @Inject constructor(
    private val reservationAPI: ReservationAPI,
    private val reservationDataBase: ReservationDataBase,
    private val preferences: AuthPreferences
) {

        //Remote API
        suspend fun addReservation(token: String, reservation: Reservation ) = reservationAPI.addReservation(token, reservation)


        //Local database
        suspend fun getAllReservations():List<Reservation>{
            val userId = preferences.getUserInfos().id
            return reservationDataBase.reservationDao.getAllReservations(userId)
        }
        fun addLocalReservation(res: Reservation){
            reservationDataBase.reservationDao.addLocalReservation(res)
        }
}