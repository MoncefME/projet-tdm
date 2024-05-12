package com.example.projettdm.reservation.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projettdm.common.navigation.Screens
import com.example.projettdm.parking_list.presentation.ParkingViewModel

@Composable
fun ParkingDetailsScreen(
    navController: NavController, viewModel: ParkingDetailsViewModel,parkingId :String
){
    val entryDate = remember { mutableStateOf("") }
    val exitDate = remember { mutableStateOf("") }
    val entryHour = remember { mutableStateOf("") }
    val exitHour = remember { mutableStateOf("") }
    val price = "100$"



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        //AsyncImage(model = viewModel.parking.value?.image, contentDescription = null)
        Text(text = parkingId)
        Text(text = viewModel.parking.value?.name ?: "")
       // Text(text = viewModel.parking.value.toString())
        Text(text = "Entry Date")

        OutlinedTextField(
            value = entryDate.value,
            onValueChange = { entryDate.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Text(text = "Entry Hour")
        OutlinedTextField(
            value = entryHour.value,
            onValueChange = { entryHour.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Text(text = "Exit Date")
        OutlinedTextField(
            value = exitDate.value,
            onValueChange = { exitDate.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Text(text = "Exit Hour")
        OutlinedTextField(
            value = exitHour.value,
            onValueChange = { exitHour.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Text(text = "Price: $price", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))

        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(text = "Book")
        }




    }
    LaunchedEffect (Unit){
        viewModel.getParkingById(parkingId)
    }
}

