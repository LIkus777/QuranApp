package com.zaur.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun QuranNavGraph(
    navController: NavHostController,
    mainScreen: @Composable (NavHostController) -> Unit,
    surahChooseScreen: @Composable (NavHostController) -> Unit,
    bookmarksScreen: @Composable () -> Unit,
    settingsScreen: @Composable () -> Unit,
    surahDetailScreen: @Composable (Int, String, Int) -> Unit,
    pageDetailScreen: @Composable (Int, Int, String, Int) -> Unit,
    juzDetailScreen: @Composable (Int, Int, String, Int) -> Unit,
) {
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            mainScreen(navController)
        }
        composable(Screen.SurahChoose.route) {
            surahChooseScreen(navController)
        }
        composable(Screen.Bookmarks.route) {
            bookmarksScreen()
        }
        composable(Screen.Settings.route) {
            settingsScreen()
        }
        composable(
            route = Screen.SurahDetail.route,
            arguments = listOf(
                navArgument("surahNumber") { type = NavType.IntType },
                navArgument("surahName") { type = NavType.StringType },
                navArgument("highlightAyah") {
                    type = NavType.IntType; defaultValue = 0
                })) { backStackEntry ->
            val surahNumber = backStackEntry.arguments?.getInt("surahNumber") ?: 1
            val surahName = backStackEntry.arguments?.getString("surahName") ?: ""
            val highlightAyah = backStackEntry.arguments?.getInt("highlightAyah") ?: 0
            surahDetailScreen(surahNumber, surahName, highlightAyah)
        }
        composable(
            route = Screen.PageDetail.route,
            arguments = listOf(
                navArgument("pageNumber") { type = NavType.IntType },
                navArgument("surahNumber") { type = NavType.IntType },
                navArgument("surahName") { type = NavType.StringType },
                navArgument("highlightAyah") {
                    type = NavType.IntType; defaultValue = 0
                })) { backStackEntry ->
            val pageNumber = backStackEntry.arguments?.getInt("pageNumber") ?: 1
            val surahNumber = backStackEntry.arguments?.getInt("surahNumber") ?: 1
            val surahName = backStackEntry.arguments?.getString("surahName") ?: ""
            val highlightAyah = backStackEntry.arguments?.getInt("highlightAyah") ?: 0
            pageDetailScreen(pageNumber, surahNumber, surahName, highlightAyah)
        }
        composable(
            route = Screen.JuzDetail.route,
            arguments = listOf(
                navArgument("juzNumber") { type = NavType.IntType },
                navArgument("surahNumber") { type = NavType.IntType },
                navArgument("surahName") { type = NavType.StringType },
                navArgument("highlightAyah") {
                    type = NavType.IntType; defaultValue = 0
                })) { backStackEntry ->
            val juzNumber = backStackEntry.arguments?.getInt("juzNumber") ?: 1
            val surahNumber = backStackEntry.arguments?.getInt("surahNumber") ?: 1
            val surahName = backStackEntry.arguments?.getString("surahName") ?: ""
            val highlightAyah = backStackEntry.arguments?.getInt("highlightAyah") ?: 0
            juzDetailScreen(juzNumber, surahNumber, surahName, highlightAyah)
        }
    }
}