package com.example.projettdm.common.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController


data class BottomItem(
    val title: String, val icon: ImageVector
)


@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController,
    screenIndex: Int
) {
    val items = listOf(
        BottomItem(
            title = "Home",
            icon = Icons.Rounded.Home
        ),
        BottomItem(
            title = "Search",
            icon = Icons.Rounded.Search
        ),
        BottomItem(
            title = "Parking",
            icon = Icons.Rounded.ShoppingCart
        ),
        BottomItem(title = "Profile",
        icon = Icons.Rounded.AccountCircle
        ),
    )

    val selected = rememberSaveable { mutableIntStateOf(0) }

    NavigationBar {
        Row {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(text = item.title)
                    },
                    selected = screenIndex == index,
                    onClick = {
                        selected.intValue = screenIndex
                        when (index) {
                            0 -> {
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screens.ParkingListScreen.route)
                            }
                            1 -> {
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screens.ParkingMapScreen.route)
                            }
                            2 -> {
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screens.ParkingDetailsScreen.route)
                            }
                            3 -> {
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screens.ProfileScreen.route)
                            }
                        }
                    }
                )
            }
        }
    }
}