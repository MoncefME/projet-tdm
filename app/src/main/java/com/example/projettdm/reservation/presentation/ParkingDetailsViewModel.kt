package com.example.projettdm.reservation.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    private val repository: ParkingRepository,

    ) : ViewModel()  {
    var allReservations = mutableStateOf(listOf<Reservation>())

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
                val token = "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImthX21vdXNzYW91aUBlc2kuZHoiLCJ1c2VySWQiOiI2NjNkZTI1NTEwODgzMDVhMDM0NGQ5MTMiLCJpYXQiOjE3MTY2NzkwMzMsImV4cCI6MTcxNjY4MjYzM30.m8c9eNkpctwfggKocNvaMIXIiBsTlCrGlAuUMgLmc1o";
                val response = reservationRepository.addReservation(token, reservation)
                print("Response: $response")
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        reservation.id = data.id
                        reservation.place = data.place
                        reservation.userId = data.userId
                    }

                    addLocalReservation(reservation)
                    println("success")
                } else {
                    println("error")
                }


            }

        }

    }
    //Get all reservations from local database
    fun getAllReservations(){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                allReservations.value =  reservationRepository.getAllReservations()

            }

        }

    }

    //add a reservation to local database once the resevation is sucesssfully inserted in the remote database
    fun addLocalReservation(res: Reservation){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                reservationRepository.addLocalReservation(res)
            }
        }
    }

//    class Factory(private val reservationRepository: ReservationRepository, private val parkingRepository:ParkingRepository) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return ParkingDetailsViewModel(reservationRepository, parkingRepository ) as T
//
//        }
//    }


}
