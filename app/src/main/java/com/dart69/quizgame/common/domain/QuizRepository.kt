package com.dart69.quizgame.common.domain

import com.dart69.quizgame.common.domain.models.Quiz
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun observeQuizzes(): Flow<Quiz>

    fun tryAnswer(answer: String, callbacks: Callbacks)

    fun loadNextQuiz()

    interface Callbacks {
        fun onCorrectAnswer(answer: String)
    }
}