package com.dart69.quizgame.quiz.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dart69.quizgame.common.domain.QuizGame
import com.dart69.quizgame.common.domain.models.QuizStats
import com.dart69.quizgame.common.presentation.BaseViewModel
import com.dart69.quizgame.common.timer.SingleTimerLauncher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val game: QuizGame,
    private val timerLauncher: SingleTimerLauncher,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<QuizViewModel.State, Nothing>(State.INITIAL), SingleTimerLauncher.Callbacks {
    private val total = QuizFragmentArgs
        .fromSavedStateHandle(savedStateHandle)
        .difficulty
        .timeOut
        .seconds

    init {
        game.observeStats()
            .onEach { stats ->
                states.update { it.copy(quizStats = stats) }
                timerLauncher.startAndCancelPrevious(total, TICK_RATE, this)
            }
            .launchIn(viewModelScope)
    }

    fun answerTheQuestion(message: String) {
        if (game.tryAnswer(message)) {
            timerLauncher.startAndCancelPrevious(total, TICK_RATE, this)
        }
    }

    override fun onTick(duration: Duration) {
        states.update { it.copy(timeLeft = duration.inWholeSeconds.toInt()) }
    }

    override fun onFinish() {
        game.loadNextQuiz()
    }

    override fun onCleared() {
        super.onCleared()
        timerLauncher.cancel()
    }

    data class State(
        val quizStats: QuizStats,
        val timeLeft: Int,
    ) {
        companion object {
            val INITIAL = State(QuizStats.INITIAL, 0)
        }
    }

    private companion object {
        val TICK_RATE = 1.seconds
    }
}