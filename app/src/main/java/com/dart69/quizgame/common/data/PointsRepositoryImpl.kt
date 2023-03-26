package com.dart69.quizgame.common.data

import com.dart69.quizgame.common.domain.PointsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class PointsRepositoryImpl @Inject constructor(
    private val dataSource: PointsDataSource,
) : PointsRepository {
    private val points = MutableStateFlow(dataSource.getPoints())

    override fun observePoints(): Flow<Int> = points.asStateFlow()

    override fun spend(amount: Int) {
        val newPoints = points.value - amount
        if (newPoints < 0) {
            throw NotEnoughPointsError()
        }
        dataSource.savePoints(newPoints)
        points.value = newPoints
    }

    override fun increment() {
        val newPoints = points.value + 1
        dataSource.savePoints(newPoints)
        points.value = newPoints
    }

    class NotEnoughPointsError : Exception()
}