package com.dart69.quizgame.common.data

import com.dart69.quizgame.common.domain.QuizGame
import com.dart69.quizgame.common.domain.models.QuizStats
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class QuizGameImpl @Inject constructor(
    private val pointsDataSource: PointsDataSource,
    private val quizzesDataSource: QuizzesDataSource,
): QuizGame {
    private val points = MutableStateFlow(pointsDataSource.getPoints())
    private val quizzes = MutableStateFlow(quizzesDataSource.getCurrentQuiz())

    override fun observeStats(): Flow<QuizStats> =
        quizzes.combine(points) { quiz, points ->
            QuizStats(quiz, points)
        }

    override fun tryAnswer(answer: String): Boolean {
        val result = if (answer.trim().lowercase() == quizzes.value.correct.trim().lowercase()) {
            points.update { it + 1 }
            pointsDataSource.savePoints(points.value)
            true
        } else {
            false
        }
        loadNextQuiz()
        return result
    }

    override fun loadNextQuiz() {
        quizzes.value = quizzesDataSource.getNextQuiz()
    }
}