package com.dart69.quizgame.common.data

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

interface PointsDataSource {
    fun getPoints(): Int

    fun savePoints(points: Int)

    class Default @Inject constructor(
        private val preferences: SharedPreferences,
    ): PointsDataSource {
        override fun getPoints(): Int =
            preferences.getInt(POINTS_KEY, DEFAULT_VALUE)

        override fun savePoints(points: Int) =
            preferences.edit(commit = true) {
                putInt(POINTS_KEY, points)
            }

        private companion object {
            const val POINTS_KEY = "pts"
            const val DEFAULT_VALUE = 0
        }
    }
}