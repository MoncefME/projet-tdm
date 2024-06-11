package com.example.projettdm.auth.presentation.profile_screen


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projettdm.common.navigation.Screens


@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val user by viewModel.user.collectAsState()

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
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "My Profile",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        if (user != null) {
            // Profile Info Card
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .align(Alignment.Center)
                        .border(
                            width = 4.dp,
                            color = Color.Gray,
                            shape = CircleShape
                        )
                )
                AsyncImage(
                    model = user!!.image,
                    contentDescription = "User Image",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    UserInfoRow(Icons.Default.Person, "First Name", user!!.firstName)
                    UserInfoRow(Icons.Default.Person, "Last Name", user!!.lastName)
                    UserInfoRow(Icons.Default.Email, "Email", user!!.email)
                    if (!user?.phone.isNullOrEmpty()) {
                        UserInfoRow(Icons.Default.Phone, "Phone Number", user!!.phone)
                    }
                }
            }

            // Buttons Column
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Logout Button
                Button(
                    onClick = {
                        viewModel.logout()
                        navController.navigate(Screens.SignInScreen.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffeb4654),
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout Icon",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Logout")
                }


                // Reservation Page Button
//                Button(
//                    onClick = {
//                        navController.navigate(Screens.ReservationsScreen.route)
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 16.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.ShoppingCart,
//                        contentDescription = "Reservations Icon",
//                        tint = Color.White,
//                        modifier = Modifier.size(24.dp)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(text = "Reservations")
//                }


                // Parking List Button
//                Button(
//                    onClick = {
//                        navController.navigate(Screens.ParkingListScreen.route)
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 16.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.List,
//                        contentDescription = "Parking List Icon",
//                        tint = Color.White,
//                        modifier = Modifier.size(24.dp)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(text = "Parking List")
//                }


                // Map Button
//                Button(
//                    onClick = {
//                        navController.navigate(Screens.ParkingMapScreen.route)
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 16.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Place,
//                        contentDescription = "Map Icon",
//                        tint = Color.White,
//                        modifier = Modifier.size(24.dp)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(text = "Map")
//                }
            }



        } else {
            Button(
                onClick = {
                    navController.navigate(Screens.SignInScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Go to Sign In")
            }
        }
    }
}

@Composable
fun UserInfoRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "$label: ", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

