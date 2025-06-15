package com.zaur.presentation.ui.ui_state.aqc

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface SurahDetailScreenState {

    fun textState(): TextState.Base
    fun translatorState(): TranslatorState.Base
    fun reciterState(): ReciterState.Base
    fun uiPreferencesState(): UiPreferencesState.Base
    fun bottomSheetState(): BottomSheetState.Base

    data class Base(
        private val textState: TextState.Base = TextState.Base(),
        private val translatorState: TranslatorState.Base = TranslatorState.Base("", "", ""),
        private val reciterState: ReciterState.Base = ReciterState.Base("", ""),
        private val uiPreferencesState: UiPreferencesState.Base = UiPreferencesState.Base(),
        private val bottomSheetState: BottomSheetState.Base = BottomSheetState.Base(),
    ) : SurahDetailScreenState {
        override fun textState(): TextState.Base = textState
        override fun translatorState(): TranslatorState.Base = translatorState
        override fun reciterState(): ReciterState.Base = reciterState
        override fun uiPreferencesState(): UiPreferencesState.Base = uiPreferencesState
        override fun bottomSheetState(): BottomSheetState.Base = bottomSheetState
    }

    object Empty : SurahDetailScreenState {
        override fun textState(): TextState.Base = TextState.Empty
        override fun translatorState(): TranslatorState.Base = TranslatorState.Empty
        override fun reciterState(): ReciterState.Base = ReciterState.Empty
        override fun uiPreferencesState(): UiPreferencesState.Base = UiPreferencesState.Empty
        override fun bottomSheetState(): BottomSheetState.Base = BottomSheetState.Empty
    }
}

interface TextState {

    fun surahName(): String
    fun currentAyah(): Int
    fun currentSurahNumber(): Int
    fun currentPageNumber(): Int

    data class Base(
        private val surahName: String = "",
        private val currentAyah: Int = 0,
        private val currentSurahNumber: Int = 0,
        private val currentPageNumber: Int = 0
    ) : TextState {
        override fun surahName(): String = surahName
        override fun currentAyah(): Int = currentAyah
        override fun currentSurahNumber() = currentSurahNumber
        override fun currentPageNumber(): Int = currentPageNumber
    }

    companion object {
        val Empty = Base()
    }
}

interface UiPreferencesState {
    fun showTranscription(): Boolean
    fun showArabic(): Boolean
    fun showRussian(): Boolean
    fun fontSizeArabic(): Float
    fun fontSizeRussian(): Float
    fun showSurahMode(): Boolean
    fun showPageMode(): Boolean

    data class Base(
        private val showTranscription: Boolean = true,
        private val showArabic: Boolean = true,
        private val showRussian: Boolean = true,
        private val fontSizeArabic: Float = 24f,
        private val fontSizeRussian: Float = 18f,
        private val showSurahMode: Boolean = true,
        private val showPageMode: Boolean = false,
    ) : UiPreferencesState {
        override fun showTranscription() = showTranscription
        override fun showArabic() = showArabic
        override fun showRussian() = showRussian
        override fun fontSizeArabic() = fontSizeArabic
        override fun fontSizeRussian() = fontSizeRussian
        override fun showSurahMode(): Boolean = showSurahMode
        override fun showPageMode(): Boolean = showPageMode
    }

    companion object {
        val Empty = Base()
    }
}

interface ReciterState {
    fun currentReciter(): String
    fun currentReciterName(): String

    data class Base(
        private val currentReciter: String,
        private val currentReciterName: String,

        ) : ReciterState {
        override fun currentReciter(): String = currentReciter
        override fun currentReciterName() = currentReciterName
    }

    companion object {
        val Empty = Base(currentReciter = "", currentReciterName = "")
    }
}

interface TranslatorState {

    fun translator(): String
    fun translatorName(): String
    fun transcriptionName(): String

    data class Base(
        private val translator: String,
        private val translatorName: String,
        private val transcriptionName: String,
    ) : TranslatorState {
        override fun translator(): String = translator
        override fun translatorName(): String = translatorName
        override fun transcriptionName(): String = transcriptionName
    }

    companion object {
        val Empty = Base("", "", "")
    }

}

interface BottomSheetState {
    fun showRepeatDialog(): Boolean
    fun showTranslatorDialog(): Boolean
    fun showTranscriptionSheet(): Boolean
    fun showTextBottomSheet(): Boolean
    fun showPlayerBottomSheet(): Boolean
    fun showSettingsBottomSheet(): Boolean
    fun showReciterDialog(): Boolean

    data class Base(
        private val showTranslatorDialog: Boolean = false,
        private val showTranscriptionSheet: Boolean = false,
        private val showTextBottomSheet: Boolean = false,
        private val showRepeatDialog: Boolean = false,
        private val showSettingsBottomSheet: Boolean = false,
        private val showPlayerBottomSheet: Boolean = false,
        private val showReciterDialog: Boolean = false,
    ) : BottomSheetState {
        override fun showRepeatDialog() = showRepeatDialog
        override fun showTranslatorDialog() = showTranslatorDialog
        override fun showTranscriptionSheet() = showTranscriptionSheet
        override fun showTextBottomSheet() = showTextBottomSheet
        override fun showPlayerBottomSheet() = showPlayerBottomSheet
        override fun showSettingsBottomSheet() = showSettingsBottomSheet
        override fun showReciterDialog() = showReciterDialog
    }

    companion object {
        val Empty = Base()
    }
}