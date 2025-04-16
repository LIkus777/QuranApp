package com.zaur.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object SurahChoose : Screen("surah_choose_screen")
    object SurahDetail : Screen("surah_detail_screen/{surahNumber}/{surahName}") {
        fun createRoute(surahNumber: Int, surahName: String) =
            "surah_detail_screen/$surahNumber/${Uri.encode(surahName)}"
    }
}
