package com.example.projettdm.parking_map.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ParkingMapScreen(
    navController: NavController
) {
    Row {
        Text(text = "Parking Map Screen")
    }
}