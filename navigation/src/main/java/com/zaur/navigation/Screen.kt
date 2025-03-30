package com.zaur.navigation

sealed class Screen(val route: String) {
    object SurahChoose : Screen("surah_choose_screen")
    object SurahDetail : Screen("surah_detail_screen/{surahNumber}") {
        fun createRoute(surahNumber: Int) = "surah_detail_screen/$surahNumber"
    }
}
