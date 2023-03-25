package com.dart69.quizgame.common.presentation

import androidx.navigation.NavController
import androidx.navigation.NavDirections

data class NavigateEvent(
    val directions: NavDirections,
) {

    fun apply(navController: NavController) {
        navController.navigate(directions)
    }
}