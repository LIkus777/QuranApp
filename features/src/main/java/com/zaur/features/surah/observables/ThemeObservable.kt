package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.theme.ThemeUIState
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface ThemeObservable : Observable.Mutable<ThemeUIState> {

    interface Update : Observable.Update<ThemeUIState>

    interface Read : Observable.Read<ThemeUIState> {
        fun themeState(): StateFlow<ThemeUIState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: ThemeUIState
    ) : Observable.Abstract<ThemeUIState>(initial), Mutable {
        override fun themeState(): StateFlow<ThemeUIState> = state()
    }

}