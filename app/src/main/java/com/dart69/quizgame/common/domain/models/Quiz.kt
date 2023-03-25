package com.dart69.quizgame.common.domain.models

data class Quiz(
    val question: String,
    val answers: List<String>,
    val correct: String,
)
