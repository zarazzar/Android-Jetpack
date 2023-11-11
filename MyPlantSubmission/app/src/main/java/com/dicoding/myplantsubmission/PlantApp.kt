package com.dicoding.myplantsubmission

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.myplantsubmission.ui.navigation.NavigationItem
import com.dicoding.myplantsubmission.ui.navigation.Screen
import com.dicoding.myplantsubmission.ui.screen.detail.DetailScreen
import com.dicoding.myplantsubmission.ui.screen.favorite.FavoriteScreen
import com.dicoding.myplantsubmission.ui.screen.home.HomeScreen
import com.dicoding.myplantsubmission.ui.screen.user.UserScreen

@Composable
fun PlantApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailPlant.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { plantId ->
                        navController.navigate(Screen.DetailPlant.createRoute(plantId))
                    }
                )
            }
            composable(Screen.Favorite.route) {

                FavoriteScreen(
                    navigateToDetail = { plantId ->
                        navController.navigate(Screen.DetailPlant.createRoute(plantId))
                    }
                )
            }
            composable(Screen.User.route) {
                UserScreen()
            }

            composable(
                route = Screen.DetailPlant.route,
                arguments = listOf(navArgument("plantId") {
                    type = NavType.LongType
                }),
            ) {
                val id = it.arguments?.getLong("plantId") ?: -1L
                DetailScreen(
                    plantId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToFavorite = {
                        navController.popBackStack()
                        navController.navigate(Screen.Favorite.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }

    }


}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier
        .graphicsLayer {
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            )
            clip = true
        },
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer

    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.menu_user),
                icon = Icons.Default.Person,
                screen = Screen.User
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                label = { Text(item.title) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
            )
        }
    }

}