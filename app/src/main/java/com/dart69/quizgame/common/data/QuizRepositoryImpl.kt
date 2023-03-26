package com.dart69.quizgame.common.data

import com.dart69.quizgame.common.domain.QuizRepository
import com.dart69.quizgame.common.domain.models.Quiz
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizzesDataSource: QuizzesDataSource,
) : QuizRepository {
    private val quizzes = MutableStateFlow(quizzesDataSource.getCurrentQuiz())

    override fun observeQuizzes(): Flow<Quiz> = quizzes.asStateFlow()
    override fun tryAnswer(answer: String, callbacks: QuizRepository.Callbacks) {
        if (answer == quizzes.value.correct) {
            callbacks.onCorrectAnswer(answer)
        }
        loadNextQuiz()
    }

    override fun loadNextQuiz() {
        quizzes.value = quizzesDataSource.getNextQuiz()
    }
}