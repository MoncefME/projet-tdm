package com.example.projettdm.reservation.di



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
    fun provideReservationRepository(reservationAPI: ReservationAPI): ReservationRepository{
        return ReservationRepository(reservationAPI)
    }
}