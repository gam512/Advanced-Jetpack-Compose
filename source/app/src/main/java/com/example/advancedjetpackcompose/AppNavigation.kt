
package com.example.advancedjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.advancedjetpackcompose.screens.*

sealed class Screen(val route: String) {

    object Home : Screen("home")

    object CompositionLocal :
        Screen("composition_local")

    object SideEffects :
        Screen("side_effects")

    object Animation :
        Screen("animation")
}

@Composable
fun AppNavigation(
    selectedColor: Color,
    onColorChange: (Color) -> Unit
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {

            HomeScreen(
                onNavigate = { route ->

                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.CompositionLocal.route) {

            CompositionLocalScreen(
                selectedColor = selectedColor,
                onColorChange = onColorChange,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.SideEffects.route) {

            SideEffectsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Animation.route) {

            AnimationScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
