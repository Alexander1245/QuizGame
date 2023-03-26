package com.dart69.quizgame.common.domain

import kotlinx.coroutines.flow.Flow

interface PointsRepository {
    fun observePoints(): Flow<Int>

    fun spend(amount: Int)

    fun increment()
}