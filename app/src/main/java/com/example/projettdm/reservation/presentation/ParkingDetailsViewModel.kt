package com.example.projettdm.reservation.presentation

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projettdm.parking_list.data.remote.response.Parking
import com.example.projettdm.parking_list.data.repository.ParkingRepository
import com.example.projettdm.reservation.data.model.Reservation
import com.example.projettdm.reservation.data.repository.ReservationRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ParkingDetailsViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository,

    private val repository: ParkingRepository
) : ViewModel()  {
        //var parkingId = mutableIntStateOf(0)
        val parking = mutableStateOf<Parking?>(null)
        val error = mutableStateOf(false)



        fun getParkingById(id: String) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val response = repository.getParkingById(id)
                    if (response.isSuccessful) {
                        parking.value = response.body()!!
                    } else {
                        println("error")
                        error.value = true
                    }

                    }
                }
            }
    fun addReservation(reservation: Reservation) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val token = "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InVzZXJFbnZAZXhhbXBsZS5jb20iLCJ1c2VySWQiOiI2NjM4ZjhhYWY5OWNlOTE5ZDg4MTQxMGEiLCJpYXQiOjE3MTU1MDc5ODUsImV4cCI6MTcxNTUxMTU4NX0.gnTLImuDF5BXcBzpVLQQ7vSniPXvEaVABoJ_xXY2rvg";
                val response = reservationRepository.addReservation(token, reservation)
                print("Response: $response")
                if (response.isSuccessful) {
                    println("success")
                } else {
                    println("error")
                }


            }

        }

    }


}
