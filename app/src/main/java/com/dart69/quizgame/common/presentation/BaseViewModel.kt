package com.dart69.quizgame.common.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

/**
 * @param S represents screen state
 * @param E represents single ui events(e.g. show toast, snackBar, navigation)
 * */
abstract class BaseViewModel<S, E>(
    initialState: S,
) : ViewModel() {
    protected val states = MutableStateFlow(initialState)
    protected val events = MutableSharedFlow<E>()

    open fun observeStates(): Flow<S> = states.asStateFlow()

    open fun observeEvents(): Flow<E> = events.asSharedFlow()
}