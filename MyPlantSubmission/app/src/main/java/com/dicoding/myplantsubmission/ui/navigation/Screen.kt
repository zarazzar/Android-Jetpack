package com.dicoding.myplantsubmission.ui.navigation

sealed class Screen(val route: String) {

    object Home: Screen("home")
    object Favorite: Screen("favorite")
    object User: Screen("user")

    object DetailPlant : Screen("home/{plantId}") {
        fun createRoute(plantId: Long) = "home/$plantId"
    }
}