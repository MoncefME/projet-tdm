package com.example.projettdm.auth.data

import com.example.projettdm.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    override fun loginUser(email: String, password: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            val isAuthenticated = email == "admin" && password == "admin"
            emit(Resource.Success(isAuthenticated))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            val isRegistered = email.isNotEmpty() && password.isNotEmpty()
            emit(Resource.Success(isRegistered))
        }
    }

    override fun googleSignIn(idToken: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            val isSignedIn = idToken.isNotEmpty()
            emit(Resource.Success(isSignedIn))
        }
    }

    override fun logout(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            emit(Resource.Success(Unit))
        }
    }

    override fun getCurrentUser(): Any {

        return Any()
    }

}