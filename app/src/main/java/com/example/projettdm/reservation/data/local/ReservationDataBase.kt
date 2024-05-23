package com.example.projettdm.reservation.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.projettdm.reservation.data.model.Reservation
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.let { it.time }
    }
}



@Database(entities = [Reservation::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ReservationDataBase: RoomDatabase() {
    abstract val reservationDao: ReservationDao
}
