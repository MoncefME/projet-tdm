package com.example.projettdm.parking_list.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ParkingListScreen(
    navController: NavController
) {
    Row {
        Text(text = "Parking List Screen")
    }
}