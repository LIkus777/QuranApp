package com.zaur.presentation.ui.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTextUIState {
    fun isLoading(): Boolean
    fun chapters(): List<ChapterAqc>
    fun currentChapter(): ChapterAqc
    fun currentArabicText(): ArabicChapter
    fun isRefreshing(): Boolean

    data class Base(
        private val isLoading: Boolean = false,
        private val chapters: List<ChapterAqc> = emptyList(),
        private val currentChapter: ChapterAqc = ChapterAqc.Empty,
        private val currentArabicText: ArabicChapter = ArabicChapter.Empty,
        private val isRefreshing: Boolean = false
    ) : QuranTextUIState {
        override fun isLoading() = isLoading
        override fun chapters() = chapters
        override fun currentChapter() = currentChapter
        override fun currentArabicText() = currentArabicText
        override fun isRefreshing() = isRefreshing
    }
}
