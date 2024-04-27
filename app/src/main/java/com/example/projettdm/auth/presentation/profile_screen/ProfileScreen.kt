package com.example.projettdm.auth.presentation.profile_screen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
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
        if (user != null) {
            Text(text = "Email: ${user.toString()}")

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
            // Display a loading indicator or handle when user is null
            Text(text = "Loading...")
        }
    }
}