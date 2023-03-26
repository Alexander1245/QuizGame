package com.dart69.quizgame.common.data

import com.dart69.quizgame.R
import com.dart69.quizgame.common.domain.models.Quiz
import javax.inject.Inject

interface QuizzesDataSource {
    fun getNextQuiz(): Quiz

    fun getCurrentQuiz(): Quiz

    class FakeDataSource @Inject constructor() : QuizzesDataSource {
        private var index = 0

        override fun getNextQuiz(): Quiz {
            if (index + 1 >= QUIZZES.size) index = -1
            return QUIZZES[++index]
        }

        override fun getCurrentQuiz(): Quiz {
            if (index >= QUIZZES.size) index = 0
            return QUIZZES[index]
        }

        private companion object {
            val QUIZZES = listOf(
                Quiz(
                    question = "How long does the marathon last?",
                    answers = listOf("21.3 miles", "12.4 miles", "26.2 miles"),
                    correct = "26.2 miles",
                    imageRes = R.drawable.marathon,
                ),
                Quiz(
                    question = "How many players are on the baseball team?",
                    answers = listOf("5", "7", "9"),
                    correct = "9",
                    imageRes = R.drawable.baseball,
                ),
                Quiz(
                    question = "What kind of sport is considered the \"king of sports\"?",
                    answers = listOf("Football", "Tennis", "Ping-pong"),
                    correct = "Football",
                    imageRes = R.drawable.football,
                ),
                Quiz(
                    question = "In what year did Amir Khan win his Olympic boxing medal?",
                    answers = listOf("2003", "1998", "2004"),
                    correct = "2004",
                    imageRes = R.drawable.amir_khan,
                ),
                Quiz(
                    question = "In what sport would you have a touchdown?",
                    answers = listOf("American football", "Soccer", "Tennis"),
                    correct = "American football",
                    R.drawable.american_football,
                ),
            )
        }
    }
}