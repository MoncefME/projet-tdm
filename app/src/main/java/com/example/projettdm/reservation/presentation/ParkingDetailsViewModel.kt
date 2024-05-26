package com.example.projettdm.reservation.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projettdm.auth.data.local.AuthPreferences
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
    private val prefrences : AuthPreferences,
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
    fun addReservation(reservation: Reservation,  onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val token = "Bearer " + prefrences.getAuthToken()
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
                    withContext(Dispatchers.Main) {
                        onSuccess(reservation.id)
                    }
                    println("success")
                    Log.d("String", "succes inserting locally")


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
