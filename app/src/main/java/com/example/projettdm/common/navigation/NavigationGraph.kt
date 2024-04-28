package com.example.projettdm.common.navigation

import androidx.activity.viewModels
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
import com.example.projettdm.auth.presentation.profile_screen.ProfileScreen
import com.example.projettdm.auth.presentation.signup_screen.SignUpScreen
import com.example.projettdm.onboarding.presentation.WelcomeScreen
import com.example.projettdm.parking_list.presentation.ParkingListScreen
import com.example.projettdm.parking_list.presentation.ParkingViewModel
import com.example.projettdm.parking_map.presentation.ParkingMapScreen
import com.example.projettdm.reservation.presentation.ParkingDetailsScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {

  val parkingViewModel = hiltViewModel<ParkingViewModel>()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screens.OnBoardingScreen.route) {
            WelcomeScreen(navController)
        }

        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController)
        }

        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController)
        }

        composable(route = Screens.ProfileScreen.route) {
            Scaffold (
                bottomBar = {
                    BottomNavigationBar(
                        bottomNavController = navController,
                        screenIndex =  3
                    )
                }
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ){
                    ProfileScreen(navController)
                }
            }
        }

        composable(route = Screens.ParkingListScreen.route) {
            Scaffold (
                bottomBar = {
                    BottomNavigationBar(
                        bottomNavController = navController,
                        screenIndex = 0
                    )
                }
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    ParkingListScreen(navController, parkingViewModel)
                }
            }
        }

        composable(route = Screens.ParkingMapScreen.route) {
            Scaffold (
                bottomBar = {
                    BottomNavigationBar(
                        bottomNavController = navController,
                        screenIndex = 1
                    )
                }
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    ParkingMapScreen(navController)
                }
            }
        }

        composable(route = Screens.ParkingDetailsScreen.route + "/{parkingId}"){
            Scaffold (
                bottomBar = {
                    BottomNavigationBar(
                        bottomNavController = navController,
                        screenIndex = 2
                    )
                }
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    ParkingDetailsScreen(navController)
                }
            }
        }
    }
}



