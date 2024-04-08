package com.example.projettdm.common.navigation

sealed class Screens(val route: String) {
    object SignInScreen : Screens(route = "SignIn_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")
    object ProfileScreen : Screens(route = "Profile_Screen")
    object OnBoardingScreen : Screens(route = "OnBoarding_Screen")
}