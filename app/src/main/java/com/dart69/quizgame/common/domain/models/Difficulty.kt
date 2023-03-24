package com.dart69.quizgame.common.domain.models

enum class Difficulty(val timeOut: Long) {
    EASY(10L), MEDIUM(6L), HARD(3L)
}