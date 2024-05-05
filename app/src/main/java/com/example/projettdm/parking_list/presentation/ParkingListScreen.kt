package com.example.projettdm.parking_list.presentation

import android.annotation.SuppressLint
import android.media.Image
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projettdm.R
import com.example.projettdm.common.navigation.Screens
import com.example.projettdm.parking_list.data.remote.ParkingAPI
import com.example.projettdm.parking_list.data.remote.response.Parking
import com.example.projettdm.reservation.presentation.ParkingDetailsViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun ParkingListScreen(
    navController: NavController, viewModel: ParkingViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DisplayError(viewModel = viewModel)
        Text(text = "Parking List Screen new")
        LazyColumn {

            items(viewModel.parkings.value) { parking ->
                ParkingCard(parking = parking, navController = navController)
            }
            items(viewModel.parkings.value) { parking ->
                ParkingCard(parking = parking, navController = navController)
            }
        }

    }
}

@Composable
fun ParkingCard(parking: Parking, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color(0xFF613EEA), shape = RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.parking),
                contentDescription = "Image of the parking",
                modifier = Modifier
                    .size(150.dp)
                    .padding(end = 16.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = parking.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = parking.city,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = parking.price.toString(),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    modifier = Modifier.background(color = Color( 0xfffff), shape = RoundedCornerShape(16.dp)),
                    onClick = {
                        navController.navigate(Screens.ParkingDetailsScreen.route + "/${parking.id}")
                    },
//                    modifier = Modifier.align(LineHeightStyle.Alignment.End)
                ) {
                    Text(text = "Details")
                }
            }
        }
    }
}


@Composable
fun DisplayError(viewModel: ParkingViewModel){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        if(viewModel.error.value){
            Toast.makeText(LocalContext.current, "parking list is empty", Toast.LENGTH_SHORT).show()

        }
    }
}