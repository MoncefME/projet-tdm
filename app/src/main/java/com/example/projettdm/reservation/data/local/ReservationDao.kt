package com.example.projettdm.reservation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.projettdm.reservation.data.model.Reservation
import java.util.Date

@Dao
interface ReservationDao {
    @Insert
    fun addLocalReservation(res: Reservation)
    @Query("SELECT * FROM Reservations WHERE userId = :userId")
    fun getAllReservations(
        userId: String
    ):List<Reservation>

}