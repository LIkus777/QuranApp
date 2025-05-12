package com.zaur.navigation

import android.net.Uri

/**
* @author Zaur
* @since 2025-05-12
*/

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object SurahChoose : Screen("surah_choose_screen")
    object SurahDetail : Screen("surah_detail_screen/{surahNumber}/{surahName}") {
        fun createRoute(surahNumber: Int, surahName: String) =
            "surah_detail_screen/$surahNumber/${Uri.encode(surahName)}"
    }
}
