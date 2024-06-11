package com.example.projettdm.auth.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.projettdm.auth.data.remote.response.UserInfoResponse
import kotlinx.coroutines.flow.first

class AuthPreferences(private val dataStore: DataStore<Preferences>) {

    companion object {
        val AUTH_KEY = stringSetPreferencesKey("auth_key")
        val USER_ID = stringSetPreferencesKey("user_id")
        val EMAIL = stringSetPreferencesKey("email")
        val FIRST_NAME = stringSetPreferencesKey("first_name")
        val LAST_NAME = stringSetPreferencesKey("last_name")
        val PHONE = stringSetPreferencesKey("phone")
    }
    suspend fun saveAuthToken(loginToken:String){
        dataStore.edit { pref ->
            pref[AUTH_KEY] = setOf(loginToken)
        }
    }

    suspend fun saveUserInfos(user:UserInfoResponse){
        dataStore.edit { pref ->
            pref[USER_ID] = setOf(user.id)
            pref[EMAIL] = setOf(user.email)
            pref[FIRST_NAME] = setOf(user.firstName)
            pref[LAST_NAME] = setOf(user.lastName)
            pref[PHONE] = setOf(user.phone)
        }
    }

    suspend fun getUserInfos(): UserInfoResponse {
        val preferences = dataStore.data.first()
        return UserInfoResponse(
            preferences[USER_ID]?.firstOrNull() ?: "",
            preferences[EMAIL]?.firstOrNull() ?: "",
            preferences[FIRST_NAME]?.firstOrNull() ?: "",
            preferences[LAST_NAME]?.firstOrNull() ?: "",
            preferences[PHONE]?.firstOrNull() ?: "",
            ""
        )
    }

    // getAuthToken
    suspend fun getAuthToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[AUTH_KEY]?.firstOrNull()
    }

    suspend fun clearAuthToken() {
        dataStore.edit { pref ->
            pref.remove(AUTH_KEY)
        }
    }

}