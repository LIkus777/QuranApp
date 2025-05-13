package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.AnimatedMenuUiState
import kotlinx.coroutines.flow.StateFlow


/**
 * @author Zaur
 * @since 13.05.2025
 */

interface AnimatedMenuObservable : Observable.Mutable<AnimatedMenuUiState> {

    interface Update : Observable.Update<AnimatedMenuUiState>

    interface Read : Observable.Read<AnimatedMenuUiState> {
        fun animatedMenu(): StateFlow<AnimatedMenuUiState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: AnimatedMenuUiState = AnimatedMenuUiState.Animate
    ) : Observable.Abstract<AnimatedMenuUiState>(initial), Mutable {
        override fun animatedMenu(): StateFlow<AnimatedMenuUiState> = state()
    }
    
}