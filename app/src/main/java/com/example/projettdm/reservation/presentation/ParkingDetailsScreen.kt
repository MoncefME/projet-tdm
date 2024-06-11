package com.example.projettdm.reservation.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
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





@SuppressLint("SuspiciousIndentation")
@Composable
fun ParkingDetailsScreen(
    navController: NavController, viewModel: ParkingDetailsViewModel,parkingId :String
){
    val entryDate = remember { mutableStateOf("") }
    val exitDate = remember { mutableStateOf("") }
    val entryHour = remember { mutableStateOf("") }
    val exitHour = remember { mutableStateOf("") }
    //val dateTime = remember { mutableStateOf("") }

    val price = "100$"
    val entryDateTime = remember { mutableStateOf<Date?>(null) }
    val exitDateTime = remember { mutableStateOf<Date?>(null) }

    val dateTime = remember { mutableStateOf(TextFieldValue("")) }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val (reservationId, setReservationId) = remember { mutableStateOf("") }

    val currentDate: Date = Date()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(text = viewModel.parking.value?.name ?: "", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)

        Text(text = viewModel.parking.value?.city ?: "", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp)) // Add spacing after basic info

        Text(text = "Entry Date & Time")
        DateTimePicker { selectedDate ->
            entryDateTime.value = selectedDate
            dateTime.value = TextFieldValue(selectedDate.toString()) // Format as needed
        }
        Text(text = "Exit Date & Time")
        DateTimePicker { selectedDate ->
            exitDateTime.value = selectedDate
            dateTime.value = TextFieldValue(selectedDate.toString()) // Format as needed
        }
//        OutlinedTextField(
//            value = dateTime.value, // Use the combined state variable `dateTime`
//            onValueChange = { dateTime.value = it },
//            modifier = Modifier.fillMaxWidth(),
//        )

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
            onClick = {
                val reservation = Reservation(
                    parkingId = parkingId,
                    entryTime = entryDateTime.value ?: Date(),
                    exiteTime = exitDateTime.value ?: Date()
                )
                // viewModel.addReservation(reservation)
                viewModel.addReservation(reservation) { id ->
                    setReservationId(id)
                    setShowDialog(true)
                    Log.d("ParkingDetailsScreen", "Reservation ID: $id, Show Dialog: $showDialog")

                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(color = Color(0xFF977897), shape = MaterialTheme.shapes.medium),
        ) {
            Text(text = "Book")
        }
        Text(text = "Reservation ID: $reservationId Show Dialog: $showDialog")
        /// AlertDialog(onDismissRequest = { /*TODO*/ }, title = {"test diaglog"},confirmButton = { /*TODO*/ })
        if (showDialog) {
            QrCodePopup(content = reservationId, onDismiss = { setShowDialog(false) })
        }
    }

    LaunchedEffect (Unit){
        viewModel.getParkingById(parkingId)
    }
}
@Composable
fun QrCodePopup(content: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Reservation QR Code") },
        text = {
            QrCodeDisplay(content = content)
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Composable
fun QrCodeScreen(content: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QrCodeDisplay(content = content)
    }
}