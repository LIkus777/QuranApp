package com.zaur.features.surah.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.zaur.domain.al_quran_cloud.models.chapter.RevelationType
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCaseAqc
import com.zaur.features.surah.fakes.FakeQAudioRAqc
import com.zaur.features.surah.fakes.FakeQTextRAqc
import com.zaur.features.surah.fakes.FakeQTranslationRAqc
import com.zaur.features.surah.fakes.FakeQuranStorage
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
import com.zaur.presentation.ui.ModernSurahText
import com.zaur.presentation.ui.QuranAppTheme

@Composable
fun SurahScreen(
    quranTextViewModel: QuranTextViewModel,
    quranAudioViewModel: QuranAudioViewModel,
    quranTranslationViewModel: QuranTranslationViewModel,
    modifier: Modifier
) {

    LaunchedEffect(Unit) {
        quranTextViewModel.getAllChapters()
    }

    val textState = quranTextViewModel.textUiState.collectAsState().value
    quranAudioViewModel.audioUiState.collectAsState().value
    quranTranslationViewModel.translationUiState.collectAsState().value

    val surahs = listOf(
        "سورة الفاتحة",
        "سورة البقرة",
        "سورة آل عمران",
        "سورة النساء",
        "سورة المائدة",
        "سورة الأنعام",
        "سورة الأعراف",
        "سورة الأنفال",
        "سورة التوبة",
        "سورة يونس",
        "سورة هود",
        "سورة يوسف",
        "سورة الرعد",
        "سورة إبراهيم",
        "سورة الحجر",
        "سورة النحل",
        "سورة الإسراء",
        "سورة الكهف",
        "سورة مريم",
        "سورة طه",
        "سورة الأنبياء",
        "سورة الحج",
        "سورة المؤمنون",
        "سورة النور",
        "سورة الفرقان",
        "سورة الشعراء",
        "سورة النمل",
        "سورة القصص",
        "سورة العنكبوت",
        "سورة الروم",
        "سورة لقمان",
        "سورة السجدة",
        "سورة الأحزاب",
        "سورة سبأ",
        "سورة فاطر",
        "سورة يس",
        "سورة الصافات",
        "سورة ص",
        "سورة الزمر",
        "سورة غافر",
        "سورة فصلت",
        "سورة الشورى",
        "سورة الزخرف",
        "سورة الدخان",
        "سورة الجاثية",
        "سورة الأحقاف",
        "سورة محمد",
        "سورة الفتح",
        "سورة الحجرات",
        "سورة ق",
        "سورة الذاريات",
        "سورة الطور",
        "سورة النجم",
        "سورة القمر",
        "سورة الرحمن",
        "سورة الواقعة",
        "سورة الحديد",
        "سورة المجادلة",
        "سورة الحشر",
        "سورة الممتحنة",
        "سورة الصف",
        "سورة الجمعة",
        "سورة المنافقون",
        "سورة التغابن",
        "سورة الطلاق",
        "سورة التحريم",
        "سورة الملك",
        "سورة القلم",
        "سورة الحاقة",
        "سورة المعارج",
        "سورة نوح",
        "سورة الجن",
        "سورة المزمل",
        "سورة المدثر",
        "سورة القيامة",
        "سورة الإنسان",
        "سورة المرسلات",
        "سورة النبأ",
        "سورة النازعات",
        "سورة عبس",
        "سورة التكوير",
        "سورة الإنفطار",
        "سورة المطففين",
        "سورة الإنشقاق",
        "سورة البروج",
        "سورة الطارق",
        "سورة الأعلى",
        "سورة الغاشية",
        "سورة الفجر",
        "سورة البلد",
        "سورة الشمس",
        "سورة الليل",
        "سورة الضحى",
        "سورة الشرح",
        "سورة التين",
        "سورة العلق",
        "سورة القدر",
        "سورة البينة",
        "سورة الزلزلة",
        "سورة العاديات",
        "سورة القارعة",
        "سورة التكاثر",
        "سورة العصر",
        "سورة الهمزة",
        "سورة الفيل",
        "سورة قريش",
        "سورة الماعون",
        "سورة الكوثر",
        "سورة الكافرون",
        "سورة النصر",
        "سورة المسد",
        "سورة الإخلاص",
        "سورة الفلق",
        "سورة الناس"
    )

    val surahNumbers: List<Int> = (1..114).toList()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val chapers = textState.chapters?.chapters ?: emptyList() // Избегаем повторного ?. вызова
        itemsIndexed(chapers) { index, chapter ->
            ModernSurahText(
                number = surahNumbers[index],
                englishName = chapter.englishName,
                numberOfAyats = chapter.number.toInt(),
                revelationType = chapter.revelationType,
                modifier = Modifier.clickable {
                    // navigation
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SurahScreenPreview() {
    QuranAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SurahScreen(
                quranTextViewModel = QuranTextViewModel(
                    SavedStateHandle(), QuranTextUseCaseAqc(FakeQTextRAqc(), FakeQuranStorage())
                ), quranAudioViewModel = QuranAudioViewModel(
                    SavedStateHandle(), QuranAudioUseCaseAqc(FakeQAudioRAqc())
                ), quranTranslationViewModel = QuranTranslationViewModel(
                    SavedStateHandle(), QuranTranslationUseCaseAqc(FakeQTranslationRAqc())
                ), modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
