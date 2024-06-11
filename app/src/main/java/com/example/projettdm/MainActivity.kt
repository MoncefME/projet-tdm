package com.example.projettdm

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.projettdm.auth.presentation.login_screen.SignInViewModel
import com.example.projettdm.common.navigation.NavigationGraph
import com.example.projettdm.common.navigation.Screens
import com.example.projettdm.onboarding.presentation.SplashViewModel
import com.example.projettdm.ui.theme.ProjetTDMTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var splashViewModel: SplashViewModel
    private lateinit var navController: NavHostController

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
                navController = rememberNavController()
                NavigationGraph(startDestination = screen, navController = navController, context = this)

                LaunchedEffect(Unit) {
                    handleIntent(intent)
                }
            }
        }

    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent?.let {
            handleIntent(it)
        }
    }

    private fun handleIntent(intent: Intent) {
        val navigateTo = intent.getStringExtra("navigateTo")
        if (navigateTo == "ReservationsScreen") {
            navController.navigate(Screens.ReservationsScreen.route)
        }
    }
}
