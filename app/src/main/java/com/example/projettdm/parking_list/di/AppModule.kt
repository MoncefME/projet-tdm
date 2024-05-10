package com.example.projettdm.parking_list.di

import com.example.projettdm.parking_list.data.remote.ParkingAPI
import com.example.projettdm.parking_list.data.repository.ParkingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ParkingModule {
    @Provides
    @Singleton
    fun provideParkingAPI(): ParkingAPI {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ParkingAPI.BASE_URL)
            .build()
            .create(ParkingAPI::class.java)
    }
    @Provides
    @Singleton
    fun provideParkingRepository(parkingAPI: ParkingAPI): ParkingRepository {
        return ParkingRepository(parkingAPI)
    }
}