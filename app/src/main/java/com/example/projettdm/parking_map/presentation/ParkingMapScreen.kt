package com.example.projettdm.parking_map.presentation
//
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.ModalBottomSheetLayout
//import androidx.compose.material.ModalBottomSheetState
//import androidx.compose.material.ModalBottomSheetValue
//import com.example.projettdm.parking_list.data.remote.response.Parking
//
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import coil.compose.AsyncImage
//import com.example.projettdm.R
//import com.example.projettdm.common.navigation.Screens
//import com.example.projettdm.parking_list.presentation.ParkingViewModel
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.maps.model.BitmapDescriptorFactory
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLng
//import com.google.maps.android.compose.CameraPositionState
//import com.google.maps.android.compose.GoogleMap
//import com.google.maps.android.compose.MapProperties
//import com.google.maps.android.compose.MapType
//import com.google.maps.android.compose.MapUiSettings
//import com.google.maps.android.compose.Marker
//import com.google.maps.android.compose.MarkerInfoWindow
//import com.google.maps.android.compose.MarkerState
//import com.google.maps.android.compose.rememberCameraPositionState
//
//@Composable
//
//fun ParkingMapScreen(
//    navController: NavController,
//    viewModel: ParkingViewModel,
//    userLocation: LatLng,
//    cameraPositionState: CameraPositionState
//
//) {
//    val parkings by viewModel.parkings
//
//
//    val uiSettings = remember {
//        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
//    }
//    val properties = remember {
//        mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
//    }
//
//    Text(text = "Hello World")
//    Text(text = "Hello World 2, i hope you get displayed")
//
//    GoogleMap(
//        modifier = Modifier.fillMaxSize(),
//        cameraPositionState = cameraPositionState,
//        properties = properties.value,
//        uiSettings = uiSettings.value,
//    ) {
//        Marker(
//            state = MarkerState(position = userLocation),
//            title = "Your Location",
//            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
//        )
//
//        parkings.forEach { parking ->
//            val position = LatLng(parking.latitude, parking.longitude)
//            MarkerInfoWindow(
//                state = MarkerState(position = position),
//                icon = BitmapDescriptorFactory.fromResource(R.drawable.logo_map)
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                    modifier = Modifier
//                        .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(10))
//                        .clip(RoundedCornerShape(10))
//                        .background(Color.Blue)
//                        .padding(20.dp)
//                ) {
//
//                    Text(parking.name, fontWeight = FontWeight.Bold, color = Color.White)
//                    Text(parking.price.toString(), fontWeight = FontWeight.Medium, color = Color.White)
//                    Button(onClick = {
//                        navController.navigate(Screens.ParkingDetailsScreen.route + "/${parking.id}")
//                     }) {
//                        Text(text = "Parking details")
//                    }
//                    Button(
//                        modifier = Modifier.background(color = Color( 0xfffff), shape = RoundedCornerShape(16.dp)),
//                        onClick = {
//                            navController.navigate(Screens.ParkingDetailsScreen.route + "/${parking.id}")
//                        },
//                    ) {
//                        Text(text = "Details du parking ")
//                    }
////
//                }
//            }
//        }
//    }
//}
//

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projettdm.R
import com.example.projettdm.common.navigation.Screens
import com.example.projettdm.parking_list.data.remote.response.Parking
import com.example.projettdm.parking_list.presentation.ParkingViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingMapScreen(
    navController: NavController,
    viewModel: ParkingViewModel,
    userLocation: LatLng,
    cameraPositionState: CameraPositionState
) {
    val parkings by viewModel.parkings
    val uiSettings = remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }
    val properties = remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
    }

    // State to track selected parking
    var selectedParking by remember { mutableStateOf<Parking?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties.value,
            uiSettings = uiSettings.value,
        ) {
            Marker(
                state = MarkerState(position = userLocation),
                title = "Your Location",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )

            parkings.forEach { parking ->
                val position = LatLng(parking.latitude, parking.longitude)
                Marker(
                    state = MarkerState(position = position),
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.logo_map),
                    onClick = {
                        selectedParking = parking
                        true
                    }
                )
            }
        }

        // Display parking details at the bottom if a parking is selected
        selectedParking?.let { parking ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(parking.name, fontWeight = FontWeight.Bold, color = Color.Black, style = MaterialTheme.typography.titleLarge)
                    Text("Price: ${parking.price} per hour", fontWeight = FontWeight.Medium, color = Color.Black, style = MaterialTheme.typography.bodyMedium)
                    Text("Empty places: ${parking.availablePlaces}/${parking.capacity}", color = Color.Black, style = MaterialTheme.typography.bodyMedium)
                    Button(
                        onClick = {
                            navController.navigate(Screens.ParkingDetailsScreen.route + "/${parking.id}")
                        }
                    ) {
                        Text(text = "Parking details")
                    }
//                    Button(
//                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
//                        onClick = {
//                            navController.navigate(Screens.ParkingDetailsScreen.route + "/${parking.id}")
//                        },
//                    ) {
//                        Text(text = "Details du parking")
//                    }
                }
            }
        }
    }
}
