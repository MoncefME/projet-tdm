package com.example.projettdm.auth.data

import com.example.projettdm.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<Resource<Boolean>>
    fun registerUser(email: String, password: String): Flow<Resource<Boolean>>
    fun googleSignIn(idToken: String): Flow<Resource<Boolean>>
    fun logout() : Flow<Resource<Unit>>
    fun getCurrentUser() : Any

}