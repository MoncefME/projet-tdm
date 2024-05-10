package com.example.projettdm.auth.di

import com.example.projettdm.auth.data.remote.AuthAPI
import com.example.projettdm.auth.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthAPI(): AuthAPI {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AuthAPI.BASE_URL)
            .build()
            .create(AuthAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authAPI: AuthAPI): AuthRepository {
        return AuthRepository(authAPI)
    }
}