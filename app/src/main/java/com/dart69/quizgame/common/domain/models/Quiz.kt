package com.dart69.quizgame.common.domain.models

data class Quiz(
    val question: String,
    val answers: List<String>,
    val correct: String,
    val imageRes: Int,
) {
    companion object {
        val INITIAL = Quiz("", emptyList(), "", 0)
    }
}
