package com.example.projettdm.reservation.presentation


import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.projettdm.R
import com.example.projettdm.common.navigation.Screens
import com.example.projettdm.reservation.data.model.Reservation
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


@Composable
fun MyReservationsScreen(parkingDetailsViewModel: ParkingDetailsViewModel, navController: NavController) {
    LaunchedEffect(Unit) {
        parkingDetailsViewModel.getAllReservations()
    }

    DisplayReservations(reservations = parkingDetailsViewModel.allReservations.value, navController = navController)
}


@Composable
fun DisplayReservations(reservations: List<Reservation>, navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "My Reservations",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
        }

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
}


//@Composable
//fun DisplayReservations(reservations: List<Reservation>, navController: NavController) {
//    Text(text = "My Reservations" , style = MaterialTheme.typography.bodyMedium)
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(16.dp)
//    ) {
//        items(reservations) { reservation ->
//            ReservationCard(reservation = reservation, navController = navController)
//            Spacer(modifier = Modifier.height(16.dp)) // Add some space between cards
//        }
//    }
//}


@Composable
fun ReservationCard(reservation: Reservation, navController: NavController) {
    val entryTimeIcon = painterResource(R.drawable.time_start)
    val exitTimeIcon = painterResource(R.drawable.time_end)
    val cityIcon = painterResource(R.drawable.city)
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    val qrCodeContent = reservation.id

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5),
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header: Parking Name
            Text(
                text = "Parking : ${reservation.parkingName ?: "Unknown"}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Content: Reservation Details
            Column {

                ReservationInfoRow(iconPainter = cityIcon,"Place:", reservation.parkingCity ?: "Unknown")
                ReservationInfoRow(iconPainter = entryTimeIcon,"Entry Time:", reservation.entryTime)
                ReservationInfoRow(iconPainter = exitTimeIcon,"Exit Time:", reservation.exiteTime)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Footer: Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screens.ParkingDetailsScreen.route + "/${reservation.parkingId}")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = "Parking Details")
                }
                Button(
                    onClick = {
                        setShowDialog(true)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = "Show QR Code")
                }
            }
        }
    }


    if (showDialog) {
        QrCodePopup(content = qrCodeContent, onDismiss = { setShowDialog(false) })
    }
}


@Composable
fun ReservationInfoRow(iconPainter:Painter,label: String, value: Any) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,){
            Icon(
                painter = iconPainter,
                contentDescription = "Icon",
                Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        when (value) {
            is Date -> FormattedDateTimeText(value)
            is String -> Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun FormattedDateTimeText(dateTime: Date) {
    val formattedDateTime = remember(dateTime) {
        val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        formatter.format(dateTime)
    }

    Text(
        text = formattedDateTime,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

