package com.example.projettdm.parking_list.presentation


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projettdm.parking_list.data.remote.response.Parking
import com.example.projettdm.parking_list.presentation.components.ParkingItemCard

@SuppressLint("UnrememberedMutableState")
@Composable
fun ParkingListScreen(
    navController: NavController, viewModel: ParkingViewModel
) {
    var searchText by remember { mutableStateOf("") }


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


        DisplayError(viewModel = viewModel)

        LazyColumn {
            items(viewModel.parkings.value.filter {
                it.name.contains(searchText, ignoreCase = true) || it.city.contains(searchText, ignoreCase = true)
            }) { parking ->
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
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        FilterChip(
//            selected = filterByPrice,
//            onClick = { onFilterByPriceChange(!filterByPrice) },
//            label = { Text("Sort by Price") }
//        )
//        FilterChip(
//            selected = filterByAvailability,
//            onClick = { onFilterByAvailabilityChange(!filterByAvailability) },
//            label = { Text("Sort by Availability") }
//        )
//    }
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
