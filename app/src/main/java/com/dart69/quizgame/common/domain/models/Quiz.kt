package com.dart69.quizgame.common.domain.models

data class Quiz(
    val question: String,
    val availableAnswers: List<String>,
    private val correctAnswer: String,
) {
    init {
        require(question.isNotBlank()) { "Can't create a Quiz object with the blank question." }
        require(availableAnswers.isNotEmpty()) { "Available answers can't be empty" }
    }

    fun tryAnswer(answer: String): Boolean =
        answer.trim().lowercase() == correctAnswer.trim().lowercase()
}
