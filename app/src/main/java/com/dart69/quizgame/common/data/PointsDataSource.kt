package com.dart69.quizgame.common.data

interface PointsDataSource {
    suspend fun getPoints(): Int

    suspend fun savePoints(points: Int)
}