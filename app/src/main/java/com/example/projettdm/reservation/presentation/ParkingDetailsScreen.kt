package com.example.projettdm.reservation.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.projettdm.common.navigation.Screens

@Composable
fun ParkingDetailsScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
         Text(text = "Parking Details Screen")
         Button(onClick = {
            navController.navigate(Screens.ParkingMapScreen.route)
         }) {
                Text(text = "Reserve")
         }
    }
}