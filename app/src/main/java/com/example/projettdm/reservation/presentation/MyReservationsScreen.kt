package com.example.projettdm.reservation.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.projettdm.common.navigation.Screens
import com.example.projettdm.reservation.data.model.Reservation


@Composable
fun MyReservationsScreen(parkingDetailsViewModel: ParkingDetailsViewModel, navController: NavController) {
    LaunchedEffect(Unit) {
        parkingDetailsViewModel.getAllReservations()
    }
    Text(text = "Reservations size " )
    Text(text = parkingDetailsViewModel.allReservations.value.size.toString())
    DisplayReservations(reservations = parkingDetailsViewModel.allReservations.value, navController = navController)


}



@Composable
fun DisplayReservations(reservations: List<Reservation>, navController: NavController) {
    Text(text = "Reservations" )
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(reservations) { reservation ->
            ReservationCard(reservation = reservation, navController = navController)
            Spacer(modifier = Modifier.height(16.dp)) // Add some space between cards
        }
    }
}


//@Composable
//fun ReservationCard(reservation: Reservation) {
//    Column(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//
//
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Place: ${reservation.place}",
//                color = Color.White
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Price: ${reservation.userId}",
//                color = Color.White
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Price: ${reservation.id}",
//                color = Color.White
//            )
//
//        }
//    }
//}

@Composable
fun ReservationCard(reservation: Reservation, navController: NavController) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    // QR code content (we'll use the reservation ID for this example)
    val qrCodeContent = reservation.id

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // QR code thumbnail
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clickable { setShowDialog(true) }
            ) {
                QrCodeDisplay(content = qrCodeContent)
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Reservation details
            Column {
                Text(
                    text = "Parking Name: ${reservation.parkingId}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Place: ${reservation.place}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Entry Time: ${reservation.entryTime}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Exit Time: ${reservation.exiteTime}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Button(
                    modifier = Modifier.background(color = Color( 0xfffff), shape = RoundedCornerShape(16.dp)),
                    onClick = {
                        navController.navigate(Screens.ParkingDetailsScreen.route + "/${reservation.parkingId.toString()}")
                    },
                ) {
                    Text(text = "Details du parking ")
                }
            }
        }
    }

    if (showDialog) {
        QrCodePopup(content = qrCodeContent, onDismiss = { setShowDialog(false) })
    }
}

//@Composable
//fun QrCodeDisplay(content: String) {
//    val painter = rememberQrBitmapPainter(content = content)
//    Image(
//        painter = painter,
//        contentDescription = "QR Code",
//        modifier = Modifier.fillMaxSize()
//    )
//}

//@Composable
//fun QrCodePopup(content: String, onDismiss: () -> Unit) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = { Text(text = "Reservation QR Code") },
//        text = {
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                QrCodeDisplay(content = content)
//            }
//        },
//        confirmButton = {
//            Button(onClick = onDismiss) {
//                Text("OK")
//            }
//        }
//    )
//}
