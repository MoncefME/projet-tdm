package com.example.projettdm.common.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projettdm.auth.presentation.login_screen.SignInScreen
import com.example.projettdm.auth.presentation.profile_screen.ProfileScreen
import com.example.projettdm.auth.presentation.signup_screen.SignUpScreen
import com.example.projettdm.onboarding.presentation.WelcomeScreen
import com.example.projettdm.parking_list.presentation.ParkingListScreen
import com.example.projettdm.parking_map.presentation.ParkingMapScreen
import com.example.projettdm.reservation.presentation.ParkingDetailsScreen
import com.google.accompanist.pager.ExperimentalPagerApi


@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                     Text(text = "Projet TDM")
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    Color(0xFFBAADC9),
                ),
            )
        },
        bottomBar = {
            BottomNavigationBar(
                bottomNavController = navController
            )
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable(route = Screens.SignInScreen.route) {
                    SignInScreen(navController)

                }
                composable(route = Screens.SignUpScreen.route) {
                    SignUpScreen(navController)

                }
                composable(route = Screens.ProfileScreen.route) {
                    ProfileScreen(navController)
                }

                composable(route = Screens.OnBoardingScreen.route) {
                    WelcomeScreen(navController)
                }

                composable(route = Screens.ParkingListScreen.route) {
                    ParkingListScreen(navController)
                }

                composable(route = Screens.ParkingMapScreen.route) {
                    ParkingMapScreen(navController)
                }

                composable(route = Screens.ParkingDetailsScreen.route) {
                    ParkingDetailsScreen(navController)
                }

            }
        }
    }
}
data class BottomItem(
    val title: String, val icon: ImageVector
)


@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController
) {
    val items = listOf(
        BottomItem(
            title = "Home",
            icon = Icons.Rounded.Home
        ),
        BottomItem(
            title = "Profile",
            icon = Icons.Rounded.AccountCircle
        ),
        BottomItem(
            title = "Search",
            icon = Icons.Rounded.Search
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(text = item.title)
                            },
                    selected = index == selected.intValue,
                    onClick = {
                        selected.value = index
                        when (index) {
                            0 -> {
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screens.ParkingListScreen.route)
                            }
                            1 -> {
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screens.ParkingMapScreen.route)
                            }
                            2 -> {
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screens.ParkingDetailsScreen.route)
                            }
                        }
                    }
                )
            }
        }
    }
}


