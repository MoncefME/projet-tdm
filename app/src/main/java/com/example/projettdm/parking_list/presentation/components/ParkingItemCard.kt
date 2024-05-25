package com.example.projettdm.parking_list.presentation.components

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.projettdm.parking_list.data.remote.response.Parking

@Composable
fun ParkingItemCard(parking: Parking, navController: NavController){
    Row (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .clickable { navController.navigate("parking_details/${parking.id}") }
    ){
        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0xFF977897),
                    shape = RoundedCornerShape(16.dp)
                )
        ){
            AsyncImage(model = null, contentDescription ="Parking IMage" )
            Text("Hello")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParkingItemCardPreview(){
    val sampleParking = Parking(
        id = "1",
        name = "Downtown Parking Lot",
        price = 15.0f,
        capacity = 200,
        available = 150,
        reserved = 50,
        image = "https://example.com/parking_lot_image.jpg",
        city = "New York",
        latitude = 40.712776,
        longitude = -74.005974,
        description = "A convenient parking lot located in the heart of downtown New York, close to major attractions and business centers.",
        availablePlaces = 150
    )
    ParkingItemCard(parking = sampleParking, navController = rememberNavController())
}