package com.zaur.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/**
* @author Zaur
* @since 2025-05-12
*/

@Composable
fun QuranNavGraph(
    navController: NavHostController,
    mainScreen: @Composable (NavHostController) -> Unit,
    surahChooseScreen: @Composable (NavHostController) -> Unit,
    surahDetailScreen: @Composable (Int, String, NavHostController) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route // Используем sealed class
    ) {
        composable(Screen.MainScreen.route) {
            mainScreen(navController)
        }
        composable(Screen.SurahChoose.route) {
            surahChooseScreen(navController)
        }
        composable(Screen.SurahDetail.route) { backStackEntry ->
            val surahNumber = backStackEntry.arguments?.getString("surahNumber")?.toIntOrNull() ?: 0
            val surahName = backStackEntry.arguments?.getString("surahName")?.toString() ?: ""
            surahDetailScreen(surahNumber, surahName, navController)
        }
    }
}
