package com.example.projettdm.parking_list.data.repository

import com.example.projettdm.parking_list.data.remote.ParkingAPI
import javax.inject.Inject

class ParkingRepository @Inject constructor
    (val parkingAPI: ParkingAPI) {
    suspend fun getParkingList() = parkingAPI.getParkingList()
}