package com.example.projettdm.reservation.presentation
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projettdm.reservation.data.model.Reservation
import com.example.projettdm.reservation.data.repository.ReservationRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
class ReservationViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository
) : ViewModel()  {
        val reservation = mutableStateOf<Reservation?>(null)
        val error = mutableStateOf(false)

        fun getReservationById(id: String) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val response = reservationRepository.getReservationById(id)
                    if (response.isSuccessful) {
                        reservation.value = response.body()!!
                    } else {
                        println("error")
                        error.value = true
                    }

                    }
                }
            }
        fun getReservationsByUserId(userId: String) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val response = reservationRepository.getReservationsByUserId(userId)
                    if (response.isSuccessful) {

                        //send ti
                    } else {
                        println("error")
                        error.value = true
                    }

                    }
                }
            }
        fun addReservation() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val response = reservationRepository.addReservation()
                    if (response.isSuccessful) {
                        reservation.value = response.body()!!
                    } else {
                        println("error")
                        error.value = true
                    }

                    }
                }

        }

}