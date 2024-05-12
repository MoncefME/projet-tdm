package com.example.projettdm.reservation.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projettdm.R
import com.example.projettdm.common.navigation.Screens
import com.example.projettdm.parking_list.presentation.ParkingViewModel
import com.google.type.DateTime

@Composable
fun ParkingDetailsScreen(
    navController: NavController, viewModel: ParkingDetailsViewModel,parkingId :String
){
    val entryDate = remember { mutableStateOf("") }
    val exitDate = remember { mutableStateOf("") }
    val entryHour = remember { mutableStateOf("") }
    val exitHour = remember { mutableStateOf("") }
    val dateTime = remember { mutableStateOf("") }

    val price = "100$"



//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        AsyncImage(model = viewModel.parking.value?.image, contentDescription = null)
//       // Text(text = parkingId)
//        Text(text = viewModel.parking.value?.name ?: "")
//        Text(text = viewModel.parking.value?.city ?: "")
//
//
//        Text(text = "Entry Date")
//
//        OutlinedTextField(
//            value = entryDate.value,
//            onValueChange = { entryDate.value = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 8.dp)
//        )
//
//        Text(text = "Entry Hour")
//        OutlinedTextField(
//            value = entryHour.value,
//            onValueChange = { entryHour.value = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 8.dp)
//        )
//
//        Text(text = "Exit Date")
//        OutlinedTextField(
//            value = exitDate.value,
//            onValueChange = { exitDate.value = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 8.dp)
//        )
//
//        Text(text = "Exit Hour")
//        OutlinedTextField(
//            value = exitHour.value,
//            onValueChange = { exitHour.value = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 8.dp)
//        )
//
//        Text(text = "Price: $price", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
//
//        Button(
//            onClick = {
//
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 8.dp)
//        ) {
//            Text(text = "Book")
//        }
//
//
//
//
//    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Display parking image (assuming `viewModel.parking.value?.image` is a valid URL)
     //   AsyncImage(model = viewModel.parking.value?.image, contentDescription = null)

        // Display parking name (assuming `viewModel.parking.value?.name` is not null)
        Text(text = viewModel.parking.value?.name ?: "", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)

        // Display parking city (assuming `viewModel.parking.value?.city` is not null)
        Text(text = viewModel.parking.value?.city ?: "", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp)) // Add spacing after basic info

         //Combined entry date and time input
        Text(text = "Entry Date & Time")
        OutlinedTextField(
            value = dateTime.value, // Use the combined state variable `dateTime`
            onValueChange = { dateTime.value = it },
            modifier = Modifier.fillMaxWidth(),
        )

        Text(
            text = "Price: ${viewModel.parking.value?.price.toString()}", // Use `parking.price` directly
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            textAlign = TextAlign.Center // Center price text
        )
        Text(
            text = "Empty places: ${viewModel.parking.value?.availablePlaces}/${viewModel.parking.value?.capacity}",
            fontSize = 16.sp
        )


        Button(
            onClick = { /* Handle booking logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(color= Color(0xFF977897),shape = MaterialTheme.shapes.medium),
        ) {
            Text(text = "Book")
        }
    }

    LaunchedEffect (Unit){
        viewModel.getParkingById(parkingId)
    }
}

