package com.example.projettdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.projettdm.common.navigation.NavigationGraph
import com.example.projettdm.common.navigation.Screens
import com.example.projettdm.onboarding.presentation.SplashViewModel
import com.example.projettdm.ui.theme.ProjetTDMTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            ProjetTDMTheme {
                val screen by splashViewModel.startDestination
                NavigationGraph(startDestination = screen)
            }
        }
    }
}
