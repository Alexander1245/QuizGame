package com.dart69.quizgame.quiz.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dart69.quizgame.common.domain.PointsRepository
import com.dart69.quizgame.common.domain.QuizRepository
import com.dart69.quizgame.common.domain.models.Quiz
import com.dart69.quizgame.common.presentation.BaseViewModel
import com.dart69.quizgame.common.timer.SingleTimerLauncher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class QuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    pointsRepository: PointsRepository,
    private val timerLauncher: SingleTimerLauncher,
    private val quizRepository: QuizRepository,
) : BaseViewModel<QuizViewModel.State, Nothing>(State.INITIAL), SingleTimerLauncher.Callbacks {
    private val total = QuizFragmentArgs
        .fromSavedStateHandle(savedStateHandle)
        .difficulty
        .timeOut
        .seconds

    init {
        quizRepository.observeQuizzes().combine(pointsRepository.observePoints()) { quiz, points ->
            states.update { it.copy(quiz = quiz, points = points) }
            timerLauncher.startAndCancelPrevious(total, TICK_RATE, this)
        }.launchIn(viewModelScope)
    }

    fun answerTheQuestion(message: String) {
        quizRepository.tryAnswer(message)
    }

    override fun onTick(duration: Duration) {
        states.update { it.copy(timeLeft = duration.inWholeSeconds.toInt()) }
    }

    override fun onFinish() {
        quizRepository.loadNextQuiz()
    }

    override fun onCleared() {
        super.onCleared()
        timerLauncher.cancel()
    }

    data class State(
        val points: Int,
        val quiz: Quiz,
        val timeLeft: Int,
    ) {
        companion object {
            val INITIAL = State(0, Quiz.INITIAL, 0)
        }
    }

    private companion object {
        val TICK_RATE = 1.seconds
    }
}