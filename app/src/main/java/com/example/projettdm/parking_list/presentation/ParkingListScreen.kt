package com.example.projettdm.parking_list.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projettdm.common.navigation.Screens
import com.example.projettdm.parking_list.data.remote.ParkingAPI
import com.example.projettdm.parking_list.data.remote.response.Parking

@Composable
fun ParkingListScreen(
    navController: NavController, viewModel: ParkingViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        displayError(viewModel = viewModel)
        Text(text = "Parking List Screen new")
        LazyColumn {
            items(viewModel.parkings.value) { parking ->
                ParkingCard(parking = parking, navController = navController)
            }
        }

    }
//    viewModel.getParkingList()
}

@Composable
fun ParkingCard(parking: Parking, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = parking.name)
        Text(text = parking.description)
        AsyncImage(model = ParkingAPI.BASE_URL + parking.img , contentDescription ="image of the parking")
        Button(onClick = {
            navController.navigate(Screens.ParkingDetailsScreen.route + "/${parking.id}")
        }) {
            Text(text = "Details")
        }
    }
}

@Composable
fun displayError(viewModel: ParkingViewModel){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        if(viewModel.error.value){
            Toast.makeText(LocalContext.current, "parking list is empty", Toast.LENGTH_SHORT).show()

        }
    }
}