package com.example.projettdm.reservation.data.model

import com.google.type.DateTime
import java.util.Date

data class Reservation(
    val id: String,
    val parkingId: String,
    val userId: String,
    val entryTime: Date,
    val exiteTime: Date,


)

