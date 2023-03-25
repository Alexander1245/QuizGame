package com.dart69.quizgame.common.domain.models

data class QuizStats(
    val quiz: Quiz,
    val points: Int,
) {
    init {
        require(points >= 0) { "A count of the points can't be a negative number." }
    }

    companion object {
        val INITIAL = QuizStats(
            quiz = Quiz("", emptyList(), ""),
            points = 0,
        )
    }
}
