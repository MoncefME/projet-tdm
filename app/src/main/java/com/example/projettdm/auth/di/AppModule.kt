package com.example.projettdm.auth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.projettdm.auth.data.local.AuthPreferences
import com.example.projettdm.auth.data.remote.AuthAPI
import com.example.projettdm.auth.repository.AuthRepository
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
object AuthModule {

    private const val AUTH_PREFERENCES = "auth_preferences"
    @Provides
    @Singleton
    fun providePreferenceDataStore(@ApplicationContext context: Context) : DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(AUTH_PREFERENCES)
            }
        )

    @Provides
    @Singleton
    fun provideAuthPreferences(dataStore: DataStore<Preferences>) : AuthPreferences =
        AuthPreferences(dataStore)

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
    fun provideAuthRepository(authAPI: AuthAPI, preferences: AuthPreferences): AuthRepository {
        return AuthRepository(authAPI, preferences)
    }


}