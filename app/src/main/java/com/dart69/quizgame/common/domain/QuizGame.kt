package com.dart69.quizgame.common.domain

import com.dart69.quizgame.common.domain.models.QuizStats
import kotlinx.coroutines.flow.Flow

interface QuizGame {
    fun observeStats(): Flow<QuizStats>

    fun tryAnswer(answer: String): Boolean

    fun loadNextQuiz()
}