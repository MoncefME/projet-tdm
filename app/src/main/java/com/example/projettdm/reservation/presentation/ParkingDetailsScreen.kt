package com.example.projettdm.reservation.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.projettdm.R
import com.example.projettdm.common.navigation.Screens
import com.example.projettdm.parking_list.presentation.ParkingViewModel
import com.example.projettdm.reservation.data.model.Reservation
import com.google.type.DateTime
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.time.LocalDate
import java.util.Date


@Composable
fun ParkingDetailsScreen(
    navController: NavController,
    viewModel: ParkingDetailsViewModel,
    parkingId: String
) {
    val entryDateTime = remember { mutableStateOf<Date?>(null) }
    val exitDateTime = remember { mutableStateOf<Date?>(null) }
    val dateTime = remember { mutableStateOf(TextFieldValue("")) }
    val (reservationId, setReservationId) = remember { mutableStateOf("") }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Add image of parking
        Image(
            painter = rememberImagePainter(data = viewModel.parking.value?.image ?: ""),
            contentDescription = "Parking Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display parking name and city
        Text(
            text = viewModel.parking.value?.name ?: "",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = viewModel.parking.value?.city ?: "",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        DateTimePicker { selectedDate ->
            entryDateTime.value = selectedDate
            dateTime.value = TextFieldValue(selectedDate.toString()) // Format as needed
        }
        // TextFields for choosing entry and exit date & time
//        OutlinedTextField(
//            value = dateTime.value,
//            onValueChange = { /* Change DateTime value */ },
//            label = { Text("Entry Date & Time") },
//            modifier = Modifier.fillMaxWidth(),
//            trailingIcon = {
//                Icon(
//                    imageVector = Icons.Default.DateRange,
//                    contentDescription = "Date",
//                )
//            }
//        )
        DateTimePicker { selectedDate ->
            exitDateTime.value = selectedDate
            dateTime.value = TextFieldValue(selectedDate.toString()) // Format as needed
        }
//        OutlinedTextField(
//            value = dateTime.value,
//            onValueChange = { /* Change DateTime value */ },
//            label = { Text("Exit Date & Time") },
//            modifier = Modifier.fillMaxWidth(),
//            trailingIcon = {
//                Icon(
//                    imageVector = Icons.Default.DateRange,
//                    contentDescription = "Date",
//                )
//            }
//        )

        Spacer(modifier = Modifier.height(16.dp))

        // Price and empty places
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Money",
                tint = Color.Black, // Adjust the color as needed
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp)) // Add spacing between icon and text
            Text(
                text = "Price: ${viewModel.parking.value?.price.toString()} per hour",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Money",
                tint = Color.Black, // Adjust the color as needed
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp)) // Add spacing between icon and text
            Text(
                text = "Empty places: ${viewModel.parking.value?.availablePlaces}/${viewModel.parking.value?.capacity}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),

            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Book button
        Button(
            onClick = {
                val reservation = Reservation(
                    parkingId = parkingId,
                    entryTime = entryDateTime.value ?: Date(),
                    exiteTime = exitDateTime.value ?: Date(),
                    parkingName = viewModel.parking.value?.name ?: "",
                    parkingPrice = viewModel.parking.value?.price.toString(),
                )
                // viewModel.addReservation(reservation)
                viewModel.addReservation(reservation) { id ->
                    setReservationId(id)
                    setShowDialog(true)
                    Log.d("ParkingDetailsScreen", "Reservation ID: $id, Show Dialog: $showDialog")
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(text = "Book")

            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Book",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        if (showDialog) {
            QrCodePopup(content = reservationId, onDismiss = { setShowDialog(false) })
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getParkingById(parkingId)
    }
}

//
//@SuppressLint("SuspiciousIndentation")
//@Composable
//fun ParkingDetailsScreen(
//    navController: NavController, viewModel: ParkingDetailsViewModel,parkingId :String
//){
//
//    val entryDateTime = remember { mutableStateOf<Date?>(null) }
//    val exitDateTime = remember { mutableStateOf<Date?>(null) }
//
//    val dateTime = remember { mutableStateOf(TextFieldValue("")) }
//    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
//    val (reservationId, setReservationId) = remember { mutableStateOf("") }
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//
//        Text(text = viewModel.parking.value?.name ?: "", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
//
//        Text(text = viewModel.parking.value?.city ?: "", style = MaterialTheme.typography.bodyMedium)
//
//        Spacer(modifier = Modifier.height(16.dp)) // Add spacing after basic info
//
//        Text(text = "Entry Date & Time")
//        DateTimePicker { selectedDate ->
//            entryDateTime.value = selectedDate
//            dateTime.value = TextFieldValue(selectedDate.toString()) // Format as needed
//        }
//        Text(text = "Exit Date & Time")
//        DateTimePicker { selectedDate ->
//            exitDateTime.value = selectedDate
//            dateTime.value = TextFieldValue(selectedDate.toString()) // Format as needed
//        }
//
//        Text(
//            text = "Price: ${viewModel.parking.value?.price.toString()}", // Use `parking.price` directly
//            fontWeight = FontWeight.Bold,
//            fontSize = 18.sp,
//            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
//            textAlign = TextAlign.Center // Center price text
//        )
//        Text(
//            text = "Empty places: ${viewModel.parking.value?.availablePlaces}/${viewModel.parking.value?.capacity}",
//            fontSize = 16.sp
//        )
//
//
//        Button(
//            onClick = {
//                val reservation = Reservation(
//                    parkingId = parkingId,
//                    entryTime = entryDateTime.value ?: Date(),
//                    exiteTime = exitDateTime.value ?: Date()
//                )
//                // viewModel.addReservation(reservation)
//                viewModel.addReservation(reservation) { id ->
//                    setReservationId(id)
//                    setShowDialog(true)
//                    Log.d("ParkingDetailsScreen", "Reservation ID: $id, Show Dialog: $showDialog")
//                }
//
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 8.dp)
//                .background(color = Color(0xFF977897), shape = MaterialTheme.shapes.medium),
//        ) {
//            Text(text = "Book")
//        }
//        //Text(text = "Reservation ID: $reservationId Show Dialog: $showDialog")
//        if (showDialog) {
//            QrCodePopup(content = reservationId, onDismiss = { setShowDialog(false) })
//        }
//    }
//
//    LaunchedEffect (Unit){
//        viewModel.getParkingById(parkingId)
//    }
//}
@Composable
fun QrCodePopup(content: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Reservation QR Code",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                QrCodeDisplay(content = content)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Scan this QR code at the entrance",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text("OK", style = MaterialTheme.typography.bodyMedium)
            }
        },

        shape = RoundedCornerShape(16.dp),

    )
}
