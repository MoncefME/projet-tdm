package com.example.projettdm.common.navigation

sealed class Screens(val route: String) {
    data object SignInScreen : Screens(route = "SignIn_Screen")
    data object SignUpScreen : Screens(route = "SignUp_Screen")
    data object ProfileScreen : Screens(route = "Profile_Screen")
    data object OnBoardingScreen : Screens(route = "OnBoarding_Screen")
    data object ParkingListScreen : Screens(route = "ParkingList_Screen")
    data object ParkingMapScreen : Screens(route = "ParkingMap_Screen")
    data object ParkingDetailsScreen : Screens(route = "ParkingDetails_Screen")

    data object ReservationsScreen : Screens(route = "Reservations_Screen")

}