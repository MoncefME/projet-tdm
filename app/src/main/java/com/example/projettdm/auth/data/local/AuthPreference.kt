package com.example.projettdm.auth.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.core.edit

class AuthPreferences(private val dataStore: DataStore<Preferences>) {

    companion object {
        val AUTH_KEY = stringSetPreferencesKey("auth_key")
    }
    suspend fun saveAuthToken(loginToken:String){
        dataStore.edit { pref ->
            pref[AUTH_KEY] = setOf(loginToken)
        }
    }

}