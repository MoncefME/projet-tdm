package com.example.projettdm.common.navigation

sealed class Screens(val route: String) {
    object SignInScreen : Screens(route = "SignIn_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")
    object ProfileScreen : Screens(route = "Profile_Screen")
    object OnBoardingScreen : Screens(route = "OnBoarding_Screen")
    object ParkingListScreen : Screens(route = "ParkingList_Screen")
    object ParkingMapScreen : Screens(route = "ParkingMap_Screen")
    object ParkingDetailsScreen : Screens(route = "ParkingDetails_Screen")
}