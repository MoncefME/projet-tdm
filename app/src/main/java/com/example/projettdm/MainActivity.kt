package com.example.projettdm

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.projettdm.auth.presentation.login_screen.SignInViewModel
import com.example.projettdm.common.navigation.NavigationGraph
import com.example.projettdm.common.navigation.Screens
import com.example.projettdm.onboarding.presentation.SplashViewModel
import com.example.projettdm.parking_list.presentation.ParkingListScreen
import com.example.projettdm.parking_list.presentation.ParkingViewModel
import com.example.projettdm.ui.theme.ProjetTDMTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var splashViewModel: SplashViewModel
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }
        super.onCreate(savedInstanceState)
        setContent {
            ProjetTDMTheme {
                val screen by splashViewModel.startDestination
//                NavigationGraph(startDestination = Screens.ProfileScreen.route)
                NavigationGraph(startDestination = screen)
            }
        }
    }
}
