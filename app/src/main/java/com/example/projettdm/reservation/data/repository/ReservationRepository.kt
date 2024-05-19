package com.example.projettdm.reservation.data.repository

import com.example.projettdm.reservation.data.local.ReservationDao
import com.example.projettdm.reservation.data.local.ReservationDataBase
import com.example.projettdm.reservation.data.model.Reservation
import com.example.projettdm.reservation.data.remote.ReservationAPI
import javax.inject.Inject


class ReservationRepository @Inject constructor
    (private val reservationAPI: ReservationAPI,
     private val reservationDao: ReservationDao
            )

    {

        //Remote API
        suspend fun addReservation(token: String, reservation: Reservation ) = reservationAPI.addReservation(token, reservation)


        //Local database
        fun getAllReservations():List<Reservation>{
            return reservationDao.getAllReservations()
        }
        fun addLocalReservation(res: Reservation){
            reservationDao.addLocalReservation(res)
        }
}