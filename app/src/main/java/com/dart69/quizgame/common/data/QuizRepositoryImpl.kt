package com.dart69.quizgame.common.data

import com.dart69.quizgame.common.domain.PointsRepository
import com.dart69.quizgame.common.domain.QuizRepository
import com.dart69.quizgame.common.domain.models.Quiz
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizzesDataSource: QuizzesDataSource,
    private val pointsRepository: PointsRepository,
) : QuizRepository {
    private val quizzes = MutableStateFlow(quizzesDataSource.getCurrentQuiz())

    override fun observeQuizzes(): Flow<Quiz> = quizzes.asStateFlow()
    override fun tryAnswer(answer: String) {
        if (answer == quizzes.value.correct) {
            pointsRepository.increment()
        }
        loadNextQuiz()
    }

    override fun loadNextQuiz() {
        quizzes.value = quizzesDataSource.getNextQuiz()
    }
}