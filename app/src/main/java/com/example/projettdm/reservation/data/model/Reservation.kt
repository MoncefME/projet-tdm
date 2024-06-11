package com.example.projettdm.reservation.data.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.type.DateTime
import java.util.Date

@Entity(tableName = "Reservations")

data class Reservation(
    @PrimaryKey
    var id: String="",
    val parkingId: String,
    var userId: String="",
    val entryTime: Date,
    val exiteTime: Date,
    var place: Int?=null,

    var parkingName : String?=null,
    var parkingPrice : String?=null,


    )










