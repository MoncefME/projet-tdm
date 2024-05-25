package com.example.projettdm.reservation.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Color
import com.example.projettdm.reservation.data.model.Reservation


@Composable
fun MyReservationsScreen(parkingDetailsViewModel: ParkingDetailsViewModel) {

    DisplayReservations(reservations = parkingDetailsViewModel.allReservations.value)


}


@Composable
fun DisplayReservations(reservations: List<Reservation>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(reservations) { reservation ->
            ReservationCard(reservation = reservation)
            Spacer(modifier = Modifier.height(16.dp)) // Add some space between cards
        }
    }
}


@Composable
fun ReservationCard(reservation: Reservation) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {


            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Place: ${reservation.place}",
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Price: ${reservation.userId}",
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Price: ${reservation.parkingId}",
                color = Color.White
            )

        }
    }
}

