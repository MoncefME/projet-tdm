package com.example.projettdm.parking_list.presentation
//
//import android.annotation.SuppressLint
//import android.media.Image
//import android.widget.Toast
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.LineHeightStyle
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import coil.compose.AsyncImage
//import com.example.projettdm.R
//import com.example.projettdm.common.navigation.Screens
//import com.example.projettdm.parking_list.data.remote.ParkingAPI
//import com.example.projettdm.parking_list.data.remote.response.Parking
//import com.example.projettdm.parking_list.presentation.components.ParkingItemCard
//import com.example.projettdm.reservation.presentation.ParkingDetailsViewModel
//
//@SuppressLint("UnrememberedMutableState")
//@Composable
//fun ParkingListScreen(
//    navController: NavController, viewModel: ParkingViewModel
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 8.dp)
//    ) {
//        Text(
//            text = "Parking List",
//            style = MaterialTheme.typography.headlineMedium,
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colorScheme.onSurface,
//            modifier = Modifier.padding(vertical = 8.dp)
//        )
//
//        DisplayError(viewModel = viewModel)
//
//        LazyColumn {
//            items(viewModel.parkings.value) { parking ->
//                ParkingItemCard(parking = parking, navController = navController)
//            }
//        }
//    }
//}
//
//@Composable
//fun DisplayError(viewModel: ParkingViewModel){
//    Column(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//
//        val err by  viewModel.error.collectAsState()
//        if(err){
//            Toast.makeText(LocalContext.current, "parking list is empty", Toast.LENGTH_SHORT).show()
//
//        }
//    }
//}

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projettdm.parking_list.presentation.components.ParkingItemCard

@SuppressLint("UnrememberedMutableState")
@Composable
fun ParkingListScreen(
    navController: NavController, viewModel: ParkingViewModel
) {
    var searchText by remember { mutableStateOf("") }
    var filterByPrice by remember { mutableStateOf(false) }
    var filterByAvailability by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = "Car Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Parking List",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        SearchBar(
            searchText = searchText,
            onSearchTextChange = { searchText = it }
        )

        Filters(
            filterByPrice = filterByPrice,
            filterByAvailability = filterByAvailability,
            onFilterByPriceChange = { filterByPrice = it },
            onFilterByAvailabilityChange = { filterByAvailability = it }
        )

        DisplayError(viewModel = viewModel)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(viewModel.parkings.value) { parking ->
                ParkingItemCard(parking = parking, navController = navController)
            }
        }
    }
}


@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        placeholder = { Text(text = "Search by name or city") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { /* Handle search action */ })
    )
}

@Composable
fun Filters(
    filterByPrice: Boolean,
    filterByAvailability: Boolean,
    onFilterByPriceChange: (Boolean) -> Unit,
    onFilterByAvailabilityChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FilterChip(
            selected = filterByPrice,
            onClick = { onFilterByPriceChange(!filterByPrice) },
            label = { Text("Sort by Price") }
        )
        FilterChip(
            selected = filterByAvailability,
            onClick = { onFilterByAvailabilityChange(!filterByAvailability) },
            label = { Text("Sort by Availability") }
        )
    }
}

@Composable
fun DisplayError(viewModel: ParkingViewModel){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val err by viewModel.error.collectAsState()
        if(err){
            Toast.makeText(LocalContext.current, "parking list is empty", Toast.LENGTH_SHORT).show()
        }

    }
}
