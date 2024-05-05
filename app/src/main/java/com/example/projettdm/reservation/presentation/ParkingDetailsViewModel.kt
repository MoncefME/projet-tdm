package com.example.projettdm.reservation.presentation

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projettdm.parking_list.data.remote.response.Parking
import com.example.projettdm.parking_list.data.repository.ParkingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ParkingDetailsViewModel @Inject constructor(
    private val repository: ParkingRepository
) : ViewModel()  {
        //var parkingId = mutableIntStateOf(0)
        val parking = mutableStateOf<Parking?>(null)
        val error = mutableStateOf(false)



        fun getParkingById(id: Int) {
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
            } }
