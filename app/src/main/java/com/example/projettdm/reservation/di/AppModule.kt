package com.example.projettdm.reservation.di



import android.app.Application
import android.content.Context
import androidx.room.Room.databaseBuilder
import com.example.projettdm.reservation.data.local.ReservationDao
import com.example.projettdm.reservation.data.local.ReservationDataBase
import com.example.projettdm.reservation.data.remote.ReservationAPI
import com.example.projettdm.reservation.data.repository.ReservationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  ReservationModule{
    @Provides
    @Singleton
    fun provideReservationAPI(): ReservationAPI {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ReservationAPI.BASE_URL)
            .build()
            .create(ReservationAPI::class.java)
    }

//    @Provides
//    fun provideReservationDao(db: ReservationDataBase): ReservationDao {
//        return db.reservationDao
//    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ReservationDataBase {
        return databaseBuilder(
            appContext,
            ReservationDataBase::class.java,
            "reservation_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideReservationRepository(
        reservationAPI: ReservationAPI,
        reservationDataBase: ReservationDataBase
    ): ReservationRepository {
        return ReservationRepository(reservationAPI, reservationDataBase)
    }

}