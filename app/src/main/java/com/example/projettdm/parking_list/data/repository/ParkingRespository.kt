package com.example.projettdm.parking_list.data.repository

import com.example.projettdm.parking_list.data.remote.ParkingAPI
import javax.inject.Inject

class ParkingRepository @Inject constructor (private val parkingAPI: ParkingAPI) {
    suspend fun getParkingList() = parkingAPI.getParkingList()
    suspend fun getParkingById(id: Int) = parkingAPI.getParkingById(id)
}