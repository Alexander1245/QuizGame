package com.dart69.quizgame.home.presentation

import androidx.annotation.IdRes
import androidx.lifecycle.viewModelScope
import com.dart69.quizgame.R
import com.dart69.quizgame.common.coroutines.AvailableDispatchers
import com.dart69.quizgame.common.domain.models.Difficulty
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
        states.update {
            it.copy(
                difficulty = when(difficulty) {
                    R.id.radioSimple -> Difficulty.EASY
                    R.id.radioMedium -> Difficulty.MEDIUM
                    R.id.radioHard -> Difficulty.HARD
                    else -> throw IllegalArgumentException("Illegal id $difficulty")
                }
            )
        }
    }

    fun startQuiz() {
        viewModelScope.launch(dispatchers.main) {
            val directions = HomeFragmentDirections.actionHomeFragmentToQuizFragment(states.value.difficulty)
            events.emit(NavigateEvent(directions))
        }
    }

    fun goToStore() {
        viewModelScope.launch(dispatchers.main) {
            val directions = HomeFragmentDirections.actionHomeFragmentToStoreFragment()
            events.emit(NavigateEvent(directions))
        }
    }

    data class State(
        val difficulty: Difficulty,
        val currentScore: Int,
    ) {
        companion object {
            val INITIAL = State(
                difficulty = Difficulty.EASY,
                currentScore = 0,
            )
        }
    }
}