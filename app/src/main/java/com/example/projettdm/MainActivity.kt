package com.example.projettdm

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var splashViewModel: SplashViewModel
    lateinit var signinViewModel: SignInViewModel
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }
        super.onCreate(savedInstanceState)
        setContent {
            ProjetTDMTheme {
             //   val parkingViewModel = hiltViewModel<ParkingViewModel>()
              //  ParkingListScreen(navController = rememberNavController(), parkingViewModel)
                 val screen by splashViewModel.startDestination
//                //val screen by signinViewModel.
               NavigationGraph(startDestination = screen)
            }
        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("serine", token)

            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }
}
