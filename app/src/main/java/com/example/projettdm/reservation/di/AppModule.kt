package com.example.projettdm.reservation.di



import android.app.Application
import androidx.room.Room.databaseBuilder
import androidx.room.TypeConverters
import com.example.projettdm.reservation.data.local.ReservationDao
import com.example.projettdm.reservation.data.local.ReservationDataBase
import com.example.projettdm.reservation.data.remote.ReservationAPI
import com.example.projettdm.reservation.data.repository.ReservationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    @Provides
    @Singleton
    fun provideReservationRepository(reservationAPI: ReservationAPI, reservationDao: ReservationDao): ReservationRepository{
        return ReservationRepository(reservationAPI, reservationDao)
    }


    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application): ReservationDataBase {
        return databaseBuilder(
            app,
            ReservationDataBase::class.java,
            "ReservationDB"
        ).build()
    }
}