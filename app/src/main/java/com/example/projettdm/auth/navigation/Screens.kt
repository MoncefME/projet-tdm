package com.example.projettdm.auth.navigation

sealed class Screens(val route: String) {
    object SignInScreen : Screens(route = "SignIn_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")
    object ProfileScreen : Screens(route = "Profile_Screen")
}