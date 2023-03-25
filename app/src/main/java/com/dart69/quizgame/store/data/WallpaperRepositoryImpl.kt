package com.dart69.quizgame.store.data

import com.dart69.quizgame.common.coroutines.AvailableDispatchers
import com.dart69.quizgame.common.data.PointsDataSource
import com.dart69.quizgame.store.domain.WallpaperRepository
import com.dart69.quizgame.store.presentation.models.WallpaperItem
import com.dart69.quizgame.store.presentation.models.toItem
import com.dart69.quizgame.store.presentation.models.toWallpaper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class WallpaperRepositoryImpl @Inject constructor(
    private val pointsSource: PointsDataSource,
    private val wallpaperSource: WallpaperDataSource,
    private val dispatchers: AvailableDispatchers,
) : WallpaperRepository {
    private val points = MutableStateFlow(pointsSource.getPoints())

    override fun observeWallpapers(): Flow<List<WallpaperItem>> =
        wallpaperSource.observeAll().combine(points) { wallpapers, points ->
            wallpapers.map { it.toItem(points) }
        }.flowOn(dispatchers.io)

    override fun observePoints(): Flow<Int> = points.asStateFlow()

    override suspend fun initialize() {
        wallpaperSource.initialize()
    }

    override suspend fun buyWallpaper(item: WallpaperItem) {
        val currentPoints = points.value
        val newPoints = currentPoints - item.price
        if (newPoints < 0) {
            throw NotEnoughPointsError()
        }
        wallpaperSource.buy(item.toWallpaper())
        pointsSource.savePoints(newPoints)
        points.value = newPoints
    }

    class NotEnoughPointsError : Exception()
}