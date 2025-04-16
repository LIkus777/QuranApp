package com.zaur.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun QuranNavGraph(
    navController: NavHostController,
    mainScreen: @Composable () -> Unit,
    surahChooseScreen: @Composable (NavHostController) -> Unit,
    surahDetailScreen: @Composable (Int, String, NavHostController) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SurahChoose.route // Используем sealed class
    ) {
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
