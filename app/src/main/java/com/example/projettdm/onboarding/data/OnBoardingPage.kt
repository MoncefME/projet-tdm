package com.example.projettdm.onboarding.data

import androidx.annotation.DrawableRes
import com.example.projettdm.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.onboarding_1,
        title = "Best Parking Spots",
        description = "Discover the perfect parking spots hassle-free, ensuring convenience and ease for your journeys."
    )

    object Second : OnBoardingPage(
        image = R.drawable.onboarding_2,
        title = "Quick Navigation",
        description = "Effortlessly find your way around with our intuitive navigation system, saving you time and effort on every trip."
    )

    object Third : OnBoardingPage(
        image = R.drawable.onboarding_3,
        title = "Easy Payment",
        description = "Simplify your transactions with seamless payment options, making your parking experience stress-free and efficient."
    )

}