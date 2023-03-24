package com.dart69.quizgame.common.data

import com.dart69.quizgame.common.domain.models.Quiz

interface QuizDataSource {
    fun getNextQuiz(): Quiz

    class FakeDataSource() : QuizDataSource {
        private var index = 0;

        override fun getNextQuiz(): Quiz {
            if(index == QUIZZES.size) index = 0
            return QUIZZES[index++]
        }

        private companion object {
            val QUIZZES = listOf(
                Quiz(
                    question = "How long does the marathon last?",
                    availableAnswers = listOf("21.3 miles", "12.4 miles", "26.2 miles"),
                    correctAnswer = "26.2 miles",
                ),
                Quiz(
                    question = "How many players are on the baseball team?",
                    availableAnswers = listOf("5", "7", "9"),
                    correctAnswer = "9",
                ),
                Quiz(
                    question = "What kind of sport is considered the \"king of sports\"?",
                    availableAnswers = listOf("Football", "Tennis", "Ping-pong"),
                    correctAnswer = "Football",
                ),
                Quiz(
                    question = "In what year did Amir Khan win his Olympic boxing medal?",
                    availableAnswers = listOf("2003", "1998", "2004"),
                    correctAnswer = "2004",
                ),
                Quiz(
                    question = "In what sport would you have a touchdown?",
                    availableAnswers = listOf("American football", "Soccer", "Tennis"),
                    correctAnswer = "American football",
                ),
            )
        }
    }
}