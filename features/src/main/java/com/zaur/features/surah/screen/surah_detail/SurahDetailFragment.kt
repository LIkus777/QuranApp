package com.zaur.features.surah.screen.surah_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.Navigation.findNavController
import com.zaur.features.surah.screen.SurahDetailStateManager
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.features.surah.viewmodel.factory.QuranAudioViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTextViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTranslationViewModelFactory
import kotlin.getValue

class SurahDetailFragment : Fragment() {

    private val di by lazy { (requireActivity().application as App) }

    private val themeViewModel by lazy {
        ThemeViewModel.Base(
            SavedStateHandle(), di.provideThemeUseCase()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val chapterNumber = arguments?.getInt("chapterNumber") ?: 1
        val navController = findNavController()

        val stateManager = SurahDetailStateManager.Base()
        val surahDetailViewModel = SurahDetailViewModel.Base(stateManager)
        val quranTextViewModelFactory =
            QuranTextViewModelFactory.Base(di.provideQuranTextUseCaseAqc())
        val quranTranslationViewModelFactory =
            QuranTranslationViewModelFactory.Base(di.provideQuranTranslationUseCaseAqc())
        val quranAudioViewModelFactory = QuranAudioViewModelFactory.Base(
            requireContext(), stateManager, di.provideQuranAudioUseCaseAqc()
        )

        return ComposeView(requireContext()).apply {
            setContent {
                SurahDetailScreen(
                    chapterNumber = chapterNumber,
                    surahDetailViewModel = surahDetailViewModel,
                    themeViewModel = themeViewModel,
                    quranTextViewModelFactory = quranTextViewModelFactory,
                    quranTranslationViewModelFactory = quranTranslationViewModelFactory,
                    quranAudioViewModelFactory = quranAudioViewModelFactory,
                    controller = navController
                )
            }
        }
    }
}
