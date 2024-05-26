package com.example.projettdm.parking_map.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projettdm.R
import com.example.projettdm.parking_list.presentation.ParkingViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("MissingPermission")

@Composable
fun ParkingMapScreen(
    navController: NavController,
    viewModel: ParkingViewModel
) {
    val parkings by viewModel.parkings

    val atasehir = LatLng(40.9971, 29.1007)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(atasehir, 15f)
    }


    val uiSettings = remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }
    val properties = remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties.value,
        uiSettings = uiSettings.value,


        ) {
        parkings.forEach { parking ->
            val position = LatLng(parking.latitude, parking.longitude)
            MarkerInfoWindow(
                state = MarkerState(position = position),
                icon = BitmapDescriptorFactory.fromResource(R.drawable.logo_2)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, Color.Black),
                            RoundedCornerShape(10)
                        )
                        .clip(RoundedCornerShape(10))
                        .background(Color.Blue)
                        .padding(20.dp)
                ) {
                    Text(parking.name, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(parking.description, fontWeight = FontWeight.Medium, color = Color.White)
                }
            }
        }
    }
}