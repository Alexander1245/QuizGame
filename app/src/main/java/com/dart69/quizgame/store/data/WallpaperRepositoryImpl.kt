package com.dart69.quizgame.store.data

import com.dart69.quizgame.common.coroutines.AvailableDispatchers
import com.dart69.quizgame.common.domain.PointsRepository
import com.dart69.quizgame.store.domain.WallpaperRepository
import com.dart69.quizgame.store.presentation.models.WallpaperItem
import com.dart69.quizgame.store.presentation.models.toItem
import com.dart69.quizgame.store.presentation.models.toWallpaper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class WallpaperRepositoryImpl @Inject constructor(
    private val wallpaperSource: WallpaperDataSource,
    private val dispatchers: AvailableDispatchers,
    private val pointsRepository: PointsRepository,
) : WallpaperRepository {
    private val isInitialized = AtomicBoolean(false)

    override fun observeWallpapers(): Flow<List<WallpaperItem>> =
        wallpaperSource.observeAll()
            .combine(pointsRepository.observePoints()) { wallpapers, points ->
                wallpapers.map { it.toItem(points) }
            }.flowOn(dispatchers.io)


    override suspend fun initialize() {
        if(isInitialized.compareAndSet(false, true)) {
            wallpaperSource.initialize()
        }
    }

    override suspend fun buyWallpaper(item: WallpaperItem) {
        pointsRepository.spend(item.price)
        wallpaperSource.buy(item.toWallpaper())
    }
}