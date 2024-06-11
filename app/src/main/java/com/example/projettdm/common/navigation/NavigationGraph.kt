package com.example.projettdm.common.navigation

import android.os.Build
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projettdm.auth.presentation.login_screen.SignInScreen
import com.example.projettdm.auth.presentation.login_screen.SignInViewModel
import com.example.projettdm.auth.presentation.profile_screen.ProfileScreen
import com.example.projettdm.auth.presentation.signup_screen.SignUpScreen
import com.example.projettdm.auth.presentation.signup_screen.SignUpViewModel
import com.example.projettdm.common.utils.OfflineScreen
import com.example.projettdm.common.utils.isInternetAvailable
import com.example.projettdm.onboarding.presentation.WelcomeScreen
import com.example.projettdm.parking_list.presentation.ParkingListScreen
import com.example.projettdm.parking_list.presentation.ParkingViewModel
import com.example.projettdm.parking_map.presentation.ParkingMapScreen
import com.example.projettdm.parking_map.presentation.LocationAwareParkingMapScreen

import com.example.projettdm.reservation.presentation.MyReservationsScreen
import com.example.projettdm.reservation.presentation.ParkingDetailsScreen
import com.example.projettdm.reservation.presentation.ParkingDetailsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String,
    context: android.content.Context) {
    val parkingViewModel = hiltViewModel<ParkingViewModel>()
    val parkingDetailsViewModel = hiltViewModel<ParkingDetailsViewModel>()
    val signInViewModel = hiltViewModel<SignInViewModel>()
    val loginViewModel = hiltViewModel<SignUpViewModel>()


    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screens.OnBoardingScreen.route) {
            WelcomeScreen(navController)
        }

        composable(route = Screens.SignInScreen.route) {
            if (isInternetAvailable(context)) {
                SignInScreen(navController, signInViewModel)
            } else {
                OfflineScreen()
            }        }

        composable(route = Screens.SignUpScreen.route) {
            if (isInternetAvailable(context)) {
                SignUpScreen(navController, loginViewModel)
            } else {
                OfflineScreen()
            }        }

        composable(route = Screens.ProfileScreen.route) {

                Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        bottomNavController = navController,
                        screenIndex = 3
                    )
                }

            )
                {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    if (isInternetAvailable(context)) {

                        ProfileScreen(navController)
                    } else {
                        OfflineScreen()
                    }
                }
            }

        }

        composable(route = Screens.ParkingListScreen.route) {

                Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        bottomNavController = navController,
                        screenIndex = 0
                    )
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    if (isInternetAvailable(context)) {

                        ParkingListScreen(navController, parkingViewModel)
                    } else {
                        OfflineScreen()
                    }
                }
            }

        }

        composable(route = Screens.ParkingMapScreen.route) {

                Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        bottomNavController = navController,
                        screenIndex = 1
                    )
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                   // ParkingMapScreen(navController, parkingViewModel)
                    if (isInternetAvailable(context)) {

                        LocationAwareParkingMapScreen(navController, parkingViewModel)}
                        else{
                            OfflineScreen()
                        }
                }
            }

        }

        composable(route = Screens.ParkingDetailsScreen.route + "/{id}") {backStackEntry->
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        bottomNavController = navController,
                        screenIndex = 2
                    )
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    val parkingId = backStackEntry.arguments?.getString("id")
                    if (parkingId != null) {
                        if (isInternetAvailable(context)) {

                            ParkingDetailsScreen(navController, parkingDetailsViewModel, parkingId)
                        } else {
                            OfflineScreen()
                        }
                    }
                }
            }

        }

        composable(route = Screens.ReservationsScreen.route) {backStackEntry->
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        bottomNavController = navController,
                        screenIndex = 2
                    )
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    MyReservationsScreen(parkingDetailsViewModel = parkingDetailsViewModel, navController = navController)
                }
            }
        }
    }
}

