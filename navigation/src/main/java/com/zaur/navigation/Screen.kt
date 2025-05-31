package com.zaur.navigation

import android.net.Uri

/**
 * @author Zaur
 * @since 2025-05-12
 */

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object SurahChoose : Screen("surah_choose_screen")
    object Bookmarks : Screen("bookmarks_screen")
    object Settings : Screen("settings_screen")
    object SurahDetail : Screen("surah_detail/{surahNumber}/{surahName}/{highlightAyah}") {
        fun createRoute(surahNumber: Int, surahName: String, highlightAyah: Int = 0): String {
            return "surah_detail/$surahNumber/${Uri.encode(surahName)}/$highlightAyah"
        }
    }

    object PageDetail :
        Screen("page_detail/{pageNumber}/{surahNumber}/{surahName}/{highlightAyah}") {
        fun createRoute(
            pageNumber: Int,
            surahNumber: Int,
            surahName: String,
            highlightAyah: Int = 0,
        ): String {
            return "page_detail/$pageNumber/$surahNumber/${Uri.encode(surahName)}/$highlightAyah"
        }
    }

    object JuzDetail : Screen("juz_detail/{juzNumber}/{surahNumber}/{surahName}/{highlightAyah}") {
        fun createRoute(
            juzNumber: Int,
            surahNumber: Int,
            surahName: String,
            highlightAyah: Int = 0,
        ): String {
            return "juz_detail/$juzNumber/$surahNumber/${Uri.encode(surahName)}/$highlightAyah"
        }
    }
}