package com.example.projettdm.auth.presentation.profile_screen


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Profile Screen ")

        if (user != null) {
            UserInfoRow(Icons.Default.Person, "First Name", user!!.firstName)
            UserInfoRow(Icons.Default.Person, "Last Name", user!!.lastName)
            UserInfoRow(Icons.Default.Email, "Email", user!!.email)
            UserInfoRow(Icons.Default.Phone, "Phone Number", user!!.phone)

            Button(
                onClick = {
                    viewModel.logout()
                    navController.navigate(Screens.SignInScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Logout")
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
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$label: $value")
    }
}