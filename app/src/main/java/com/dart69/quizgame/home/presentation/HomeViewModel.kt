package com.dart69.quizgame.home.presentation

import androidx.annotation.IdRes
import androidx.lifecycle.viewModelScope
import com.dart69.quizgame.R
import com.dart69.quizgame.common.coroutines.AvailableDispatchers
import com.dart69.quizgame.common.presentation.BaseViewModel
import com.dart69.quizgame.common.presentation.NavigateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatchers: AvailableDispatchers,
) : BaseViewModel<HomeViewModel.State, NavigateEvent>(State.INITIAL) {

    fun setDifficulty(@IdRes difficulty: Int) {
        states.update { it.copy(selectedDifficulty = difficulty) }
    }

    fun startQuiz() {
        viewModelScope.launch(dispatchers.main) {
            events.emit(NavigateEvent(HomeFragmentDirections.actionHomeFragmentToQuizFragment()))
        }
    }

    data class State(
        @IdRes val selectedDifficulty: Int,
        val currentScore: Int,
    ) {
        companion object {
            val INITIAL = State(
                selectedDifficulty = R.id.itemSimple,
                currentScore = 0,
            )
        }
    }
}