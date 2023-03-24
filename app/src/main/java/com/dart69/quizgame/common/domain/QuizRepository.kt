package com.dart69.quizgame.common.domain

import com.dart69.quizgame.common.domain.models.Difficulty
import com.dart69.quizgame.common.domain.models.QuizStats
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun observeQuiz(difficulty: Difficulty): Flow<QuizStats>

    fun tryAnswer(answer: String)
}