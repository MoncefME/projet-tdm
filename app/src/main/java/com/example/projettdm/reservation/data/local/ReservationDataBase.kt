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

//@Database(entities = [Reservation::class], version = 6)
//@TypeConverters(Converters::class)
//abstract class ReservationDataBase: RoomDatabase() {
//    abstract val  reservationDao: ReservationDao
//
//
//}


@Database(entities = [Reservation::class], version = 4)
@TypeConverters(Converters::class)
abstract class ReservationDataBase: RoomDatabase() {
    abstract fun getReservationDao(): ReservationDao

    companion object {
        var INSTANCE: ReservationDataBase? = null
        fun getInstance(context: Context): ReservationDataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        ReservationDataBase::class.java,
                        "ReservationDB"
                    )
                        .fallbackToDestructiveMigration() // Add this line for fallback to destructive migration
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}
