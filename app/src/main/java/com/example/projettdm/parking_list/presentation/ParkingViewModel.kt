package com.example.projettdm.parking_list.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projettdm.parking_list.data.remote.response.Parking
import com.example.projettdm.parking_list.data.repository.ParkingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ParkingViewModel @Inject constructor(
    private val repository: ParkingRepository,
) : ViewModel() {

    val parkings = mutableStateOf((listOf<Parking>()))
    val error = MutableStateFlow(false)

    init{
       getParkingList()
    }
    private fun getParkingList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repository.getParkingList()
                if (response.isSuccessful) {
                    parkings.value = response.body()!!
                } else {
                    println("error")
                    error.value = true
                }
            }
        }
    }
}


