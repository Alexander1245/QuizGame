package com.dart69.quizgame.common.data

import com.dart69.quizgame.common.domain.models.QuizStats
import kotlinx.coroutines.flow.*

class QuizRepositoryImpl(
    private val quizDataSource: QuizDataSource,
    private val pointsDataSource: PointsDataSource,
) {
    private val stats = MutableStateFlow<QuizStats?>(null)

    suspend fun getPoints() = stats.value?.points ?: pointsDataSource.getPoints()

    fun observe(): Flow<QuizStats> =
        flow {
            stats
                .filterNotNull()
                .onStart { loadNextQuiz() }
                .onCompletion {
                    stats.value?.points?.let { pointsDataSource.savePoints(it) }
                    stats.value = null
                }
        }

    suspend fun loadNextQuiz() {
        stats.value = QuizStats(
            quiz = quizDataSource.getNextQuiz(),
            points = getPoints(),
        )
    }

    fun tryAnswer(answer: String) {
        if (stats.value?.quiz?.tryAnswer(answer) == true) {
            stats.update {
                it?.copy(
                    quiz = quizDataSource.getNextQuiz(),
                    points = it.points + 1,
                )
            }
        }
    }
}